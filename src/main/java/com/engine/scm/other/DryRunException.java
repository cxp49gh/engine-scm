package com.engine.scm.other;

public class DryRunException extends RuntimeException {

    private String stage;

    public DryRunException(String stage, String msg) {
        super(msg);
        this.stage = stage;
    }

    public String getStage() {
        return stage;
    }
}
