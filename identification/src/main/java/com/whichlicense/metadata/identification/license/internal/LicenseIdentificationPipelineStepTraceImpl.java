package com.whichlicense.metadata.identification.license.internal;

import com.whichlicense.metadata.identification.license.LicenseIdentificationPipelineStepTrace;

import java.util.Map;

public record LicenseIdentificationPipelineStepTraceImpl(long step, String operation, String algorithm, Map<String, Object> parameters, Map<String, Float> matches, boolean terminated) implements LicenseIdentificationPipelineStepTrace {
}
