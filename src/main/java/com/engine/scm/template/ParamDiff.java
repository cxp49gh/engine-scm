package com.engine.scm.template;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ParamDiff {

    private String paramName;
    private Object oldValue;
    private Object newValue;
    private RiskLevel riskLevel;
}
