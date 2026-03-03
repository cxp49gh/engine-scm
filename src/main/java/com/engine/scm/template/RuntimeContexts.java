package com.engine.scm.template;

import java.time.Instant;
import java.util.UUID;

public final class RuntimeContexts {

    private RuntimeContexts() {}

    public static RuntimeContext system() {
        return RuntimeContext.builder()
                .env("prod")
                .caller("system")
                .operator("system")
                .requestId(UUID.randomUUID().toString())
                .traceId(UUID.randomUUID().toString())
                .triggerTime(Instant.now())
                .dryRun(false)
                .build();
    }

    public static RuntimeContext dryRun(String operator) {
        return RuntimeContext.builder()
                .env("test")
                .caller("manual")
                .operator(operator)
                .requestId(UUID.randomUUID().toString())
                .traceId(UUID.randomUUID().toString())
                .triggerTime(Instant.now())
                .dryRun(true)
                .build();
    }
}
