package com.engine.scm.dto;

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
