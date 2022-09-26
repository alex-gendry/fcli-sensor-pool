package com.fortify.cli.ssc.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fortify.cli.common.output.cli.mixin.OutputConfig;

public class SSCOutputConfigHelper {
    /**
     * Provide default table output configuration for results optionally embedded in a data object
     * @return {@link OutputConfig}
     */
    public static final OutputConfig table() {
        return OutputConfig.table().inputTransformer(SSCOutputConfigHelper::getDataOrSelf);
    }
    
    /**
     * Provide default details output configuration for results optionally embedded in a data object
     * @return {@link OutputConfig}
     */
    public static final OutputConfig details() {
        // TODO For now we use yaml output, until #104 has been fixed
        return OutputConfig.yaml().inputTransformer(SSCOutputConfigHelper::getDataOrSelf);
    }
    
    private static final JsonNode getDataOrSelf(JsonNode json) {
        // We check for both data and responseCode (which should both always be present
        // in raw SSC responses), to correctly handle the (very unlikely) case where
        // an already unwrapped data object has a nested data object.
        return json.has("data") && json.has("responseCode") ? json.get("data") : json;
    }
}