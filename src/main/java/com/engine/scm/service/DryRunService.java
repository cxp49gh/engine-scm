package com.engine.scm.service;

import com.engine.scm.dto.DryRunRequest;
import com.engine.scm.dto.DryRunResult;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface DryRunService {

    DryRunResult dryRun(DryRunRequest request) throws JsonProcessingException;
}
