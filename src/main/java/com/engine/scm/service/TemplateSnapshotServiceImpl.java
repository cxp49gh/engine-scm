package com.engine.scm.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.engine.scm.domain.TemplateDraft;
import com.engine.scm.domain.TemplateSnapshot;
import com.engine.scm.exception.BizException;
import com.engine.scm.repository.TemplateSnapshotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class TemplateSnapshotServiceImpl
        implements TemplateSnapshotService {

    private final TemplateSnapshotRepository repository;

    @Override
    public TemplateSnapshot publish(
            TemplateDraft draft,
            String version,
            JsonNode diffFromPrev,
            String riskLevel
    ) {
        final String finalVersion;

        // 如果未指定版本号，自动生成
        if (version == null || version.isEmpty()) {
            finalVersion = generateNextVersion(draft.getId());
        } else {
            // 检查版本号是否已存在
            final String checkVersion = version;
            repository.findByTemplateIdAndVersion(draft.getId(), checkVersion)
                    .ifPresent(s -> {
                        throw BizException.conflict(
                                "Snapshot version already exists: " + checkVersion);
                    });
            finalVersion = version;
        }

        TemplateSnapshot snapshot = new TemplateSnapshot();
        snapshot.setTemplateId(draft.getId());
        snapshot.setVersion(finalVersion);
        snapshot.setTemplateContent(draft.getTemplateContent());
        snapshot.setParams(draft.getParams());
        snapshot.setDiffFromPrev(diffFromPrev);
        snapshot.setRiskLevel(riskLevel);
        snapshot.setPublishedAt(Instant.now());

        return repository.save(snapshot);
    }

    /**
     * 生成下一个版本号
     * 规则：v1 -> v2 -> v3 ... 或 v1.0 -> v1.1 -> v2.0
     */
    private String generateNextVersion(String templateId) {
        // 获取最新版本
        Optional<TemplateSnapshot> latestOpt = repository.findTopByTemplateIdOrderByPublishedAtDesc(templateId);

        if (!latestOpt.isPresent()) {
            // 首次发布，版本为 v1
            return "v1";
        }

        String latestVersion = latestOpt.get().getVersion();
        return incrementVersion(latestVersion);
    }

    /**
     * 递增版本号
     */
    private String incrementVersion(String version) {
        // 去除可能的前缀 "v" 或 "V"
        String numPart = version;
        boolean hasPrefix = false;
        if (version.toLowerCase().startsWith("v")) {
            numPart = version.substring(1);
            hasPrefix = true;
        }

        // 尝试解析版本号 (支持 x.y.z 格式)
        Pattern pattern = Pattern.compile("^(\\d+)(?:\\.(\\d+))?(?:\\.(\\d+))?$");
        Matcher matcher = pattern.matcher(numPart);

        if (matcher.matches()) {
            int major = Integer.parseInt(matcher.group(1));
            Integer minor = matcher.group(2) != null ? Integer.parseInt(matcher.group(2)) : null;
            Integer patch = matcher.group(3) != null ? Integer.parseInt(matcher.group(3)) : null;

            if (patch != null) {
                // x.y.z 格式，递增 patch
                patch++;
                return formatVersion(hasPrefix, major, minor, patch);
            } else if (minor != null) {
                // x.y 格式，递增 minor
                minor++;
                return formatVersion(hasPrefix, major, minor, null);
            } else {
                // x 格式，直接递增
                major++;
                return formatVersion(hasPrefix, major, null, null);
            }
        }

        // 无法解析，默认递增
        return (hasPrefix ? "v" : "") + (numPart) + ".1";
    }

    private String formatVersion(boolean hasPrefix, Integer major, Integer minor, Integer patch) {
        StringBuilder sb = new StringBuilder();
        if (hasPrefix) sb.append("v");
        sb.append(major);
        if (minor != null) {
            sb.append(".").append(minor);
            if (patch != null) {
                sb.append(".").append(patch);
            }
        }
        return sb.toString();
    }

    @Override
    public TemplateSnapshot getById(String id) {
        return repository.findById(id)
                .orElseThrow(() ->
                        BizException.notFound("Snapshot not found"));
    }

    @Override
    public TemplateSnapshot getByTemplateAndVersion(
            String templateId,
            String version
    ) {
        return repository.findByTemplateIdAndVersion(templateId, version)
                .orElseThrow(() ->
                        BizException.notFound("Snapshot not found"));
    }

    @Override
    public TemplateSnapshot getLatest(String templateId) {
        return repository.findTopByTemplateIdOrderByPublishedAtDesc(templateId)
                .orElseThrow(() ->
                        BizException.notFound("No snapshot published"));
    }

    @Override
    public List<TemplateSnapshot> listByTemplate(String templateId) {
        return repository.findByTemplateIdOrderByPublishedAtDesc(templateId);
    }

    @Override
    public List<TemplateSnapshot> listAll() {
        return repository.findAll();
    }
}
