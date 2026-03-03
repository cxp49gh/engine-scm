package com.engine.scm.other;

import java.util.List;
import java.util.Map;

public class DryRunResult {

    private boolean success;

    private String templateVersion;
    private String schemaVersion;

    private Map<String, Object> mergedParams;
    private Object renderedJson;

    private String checksum;

    private List<String> warnings;
    private String errorStage;
    private String errorMessage;
}
