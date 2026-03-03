package com.engine.scm.template;

import lombok.Data;

import java.util.List;

@Data
public class RiskSummary {

    private int high;
    private int medium;
    private int low;

    public static RiskSummary fromDiffs(List<ParamDiff> diffs) {
        RiskSummary s = new RiskSummary();
        for (ParamDiff d : diffs) {
            if (d.getRiskLevel() == null) continue;
            switch (d.getRiskLevel()) {
                case HIGH:
                    s.high++;
                    break;
                case MEDIUM:
                    s.medium++;
                    break;
                case LOW:
                    s.low++;
                    break;
                default:
                    break;
            }
        }
        return s;
    }

    public boolean hasHighRisk() {
        return high > 0;
    }
}
