package com.engine.scm.config;

import com.engine.scm.domain.TemplateSnapshot;
import com.engine.scm.dto.ParamMeta;
import com.engine.scm.dto.ParamType;
import com.engine.scm.dto.RiskLevel;
import com.engine.scm.repository.TemplateSnapshotRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Component
@RequiredArgsConstructor
public class TestDataInitializer implements ApplicationRunner {

    private final TemplateSnapshotRepository repository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        long count = repository.count();
        log.info("检查是否需要初始化测试数据，当前数量: {}", count);

        if (count > 0) {
            log.info("测试数据已存在，共 {} 条", count);
            for (TemplateSnapshot s : repository.findAll()) {
                log.info("  - id: {}, templateId: {}, version: {}", s.getId(), s.getTemplateId(), s.getVersion());
            }

            // 检查是否有指定模板的数据，如果没有则添加
            Set<String> targetIds = new HashSet<>();
            targetIds.add("69a7bcdf37494a79c2cdc7c5");
            targetIds.add("69a7bced37494a79c2cdc7c6");
            targetIds.add("69a7bcfc37494a79c2cdc7c7");
            boolean hasTargetData = false;
            for (TemplateSnapshot s : repository.findAll()) {
                if (targetIds.contains(s.getTemplateId())) {
                    hasTargetData = true;
                    break;
                }
            }

            if (!hasTargetData) {
                log.info("为目标模板添加测试数据...");
                addTestData();
            }
            return;
        }

        log.info("开始初始化模板快照测试数据...");
        addTestData();
    }

    private void addTestData() {
        // 订单支付模板快照
        repository.save(TemplateSnapshot.builder()
                .templateId("69a7bcdf37494a79c2cdc7c5")
                .version("v1.0")
                .templateContent("{\"template\":\"order_payment.ftl\",\"variables\":[\"orderId\",\"amount\"]}")
                .params(Arrays.asList(
                        ParamMeta.builder().name("currency").type(ParamType.STRING).required(false).defaultValue("CNY").build(),
                        ParamMeta.builder().name("timeout").type(ParamType.INT).required(false).defaultValue(30000).build()
                ))
                .riskLevel("LOW")
                .publishedAt(Instant.parse("2026-01-15T10:00:00Z"))
                .build());

        repository.save(TemplateSnapshot.builder()
                .templateId("69a7bcdf37494a79c2cdc7c5")
                .version("v1.1")
                .templateContent("{\"template\":\"order_payment.ftl\",\"variables\":[\"orderId\",\"amount\",\"discount\"]}")
                .params(Arrays.asList(
                        ParamMeta.builder().name("currency").type(ParamType.STRING).required(false).defaultValue("CNY").build(),
                        ParamMeta.builder().name("timeout").type(ParamType.INT).required(false).defaultValue(30000).build()
                ))
                .riskLevel("MEDIUM")
                .publishedAt(Instant.parse("2026-02-20T14:30:00Z"))
                .build());

        repository.save(TemplateSnapshot.builder()
                .templateId("69a7bcdf37494a79c2cdc7c5")
                .version("v2.0")
                .templateContent("{\"template\":\"order_payment_v2.ftl\",\"variables\":[\"orderId\",\"amount\",\"discount\",\"points\"]}")
                .params(Arrays.asList(
                        ParamMeta.builder().name("currency").type(ParamType.STRING).required(false).defaultValue("CNY").build(),
                        ParamMeta.builder().name("timeout").type(ParamType.INT).required(false).defaultValue(60000).build(),
                        ParamMeta.builder().name("enablePoints").type(ParamType.BOOLEAN).required(false).defaultValue(true).riskLevel(RiskLevel.MEDIUM).build()
                ))
                .riskLevel("HIGH")
                .publishedAt(Instant.parse("2026-03-01T09:15:00Z"))
                .build());

        // 发货通知模板快照
        repository.save(TemplateSnapshot.builder()
                .templateId("69a7bced37494a79c2cdc7c6")
                .version("v1.0")
                .templateContent("{\"template\":\"shipment_notify.ftl\",\"variables\":[\"orderNo\",\"expressCompany\"]}")
                .params(Arrays.asList(
                        ParamMeta.builder().name("channel").type(ParamType.STRING).required(false).defaultValue("SMS").build(),
                        ParamMeta.builder().name("templateId").type(ParamType.STRING).required(false).defaultValue("TM002").build()
                ))
                .riskLevel("LOW")
                .publishedAt(Instant.parse("2026-01-10T08:00:00Z"))
                .build());

        repository.save(TemplateSnapshot.builder()
                .templateId("69a7bced37494a79c2cdc7c6")
                .version("v1.2")
                .templateContent("{\"template\":\"shipment_notify.ftl\",\"variables\":[\"orderNo\",\"expressCompany\",\"trackingNo\"]}")
                .params(Arrays.asList(
                        ParamMeta.builder().name("channel").type(ParamType.STRING).required(false).defaultValue("SMS").build(),
                        ParamMeta.builder().name("templateId").type(ParamType.STRING).required(false).defaultValue("TM002").build()
                ))
                .riskLevel("MEDIUM")
                .publishedAt(Instant.parse("2026-03-05T16:00:00Z"))
                .build());

        // 用户登录模板快照
        repository.save(TemplateSnapshot.builder()
                .templateId("69a7bcfc37494a79c2cdc7c7")
                .version("v1.0")
                .templateContent("{\"template\":\"login_success.ftl\",\"variables\":[\"username\"]}")
                .params(Arrays.asList(
                        ParamMeta.builder().name("theme").type(ParamType.STRING).required(false).defaultValue("light").build()
                ))
                .riskLevel("LOW")
                .publishedAt(Instant.parse("2026-01-05T12:00:00Z"))
                .build());

        repository.save(TemplateSnapshot.builder()
                .templateId("69a7bcfc37494a79c2cdc7c7")
                .version("v2.0")
                .templateContent("{\"template\":\"login_success_v2.ftl\",\"variables\":[\"username\",\"lastLoginTime\",\"ipAddress\"]}")
                .params(Arrays.asList(
                        ParamMeta.builder().name("theme").type(ParamType.STRING).required(false).defaultValue("light").build(),
                        ParamMeta.builder().name("redirectUrl").type(ParamType.STRING).required(false).defaultValue("/dashboard").build()
                ))
                .riskLevel("HIGH")
                .publishedAt(Instant.parse("2026-02-28T11:30:00Z"))
                .build());

        log.info("测试数据初始化完成，共 {} 条记录", repository.count());
    }
}