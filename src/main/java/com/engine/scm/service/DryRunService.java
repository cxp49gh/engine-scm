package com.engine.scm.service;

import com.engine.scm.dto.DryRunRequest;
import com.engine.scm.dto.DryRunResult;

public interface DryRunService {

    DryRunResult dryRun(DryRunRequest request);
}
