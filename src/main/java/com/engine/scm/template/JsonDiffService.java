package com.engine.scm.template;

import com.fasterxml.jackson.databind.JsonNode;
import com.flipkart.zjsonpatch.JsonDiff;
import org.springframework.stereotype.Service;

@Service
public class JsonDiffService {

    public JsonNode diff(JsonNode oldJson, JsonNode newJson) {
        return JsonDiff.asJson(oldJson, newJson);
    }
}
