package com.engine.scm.other;

import java.util.Map;

public class DryRunRequest {

    private String templateKey;
    private String bizId;
    private Map<String, Object> runtimeParams;

    private DryRunMode mode; // STRICT / DEBUG / REPLAY
}
