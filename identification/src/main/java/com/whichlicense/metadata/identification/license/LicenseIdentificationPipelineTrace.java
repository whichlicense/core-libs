package com.whichlicense.metadata.identification.license;

import com.whichlicense.metadata.identification.license.internal.LicenseIdentificationPipelineStepTraceImpl;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.util.Collections.emptyList;
import static java.util.Map.entry;
import static java.util.stream.Collectors.toUnmodifiableMap;

public record LicenseIdentificationPipelineTrace(String name, String license, float confidence, String algorithm, Map<String, Object> parameters, List<LicenseIdentificationPipelineStepTrace> traces) {
    public static LicenseIdentificationPipelineTrace empty(String name, String algorithm, Map<String, Object> parameters) {
        return new LicenseIdentificationPipelineTrace(name, null, 0f, algorithm, parameters, emptyList());
    }

    public static LicenseIdentificationPipelineTrace ofMatchSet(String algorithm, Map<String, Object> parameters, Set<LicenseMatch> matches) {
        if (matches.isEmpty()) return empty("automatic-unary-pipeline", algorithm, parameters);
        var match = matches.stream().findFirst().orElseThrow();

        var matchMap = matches.stream().map(m -> entry(m.license(), m.confidence()))
                .collect(toUnmodifiableMap(Map.Entry::getKey, Map.Entry::getValue, (f, s) -> s));

        return new LicenseIdentificationPipelineTrace("automatic-unary-pipeline", match.license(),
                match.confidence(), algorithm, parameters, List.of(
                        new LicenseIdentificationPipelineStepTraceImpl(1L,algorithm + "-identification",
                                algorithm, parameters, matchMap, true)
        ));
    }
}
