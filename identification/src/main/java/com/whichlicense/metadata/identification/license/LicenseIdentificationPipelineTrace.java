package com.whichlicense.metadata.identification.license;

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

        record UnaryLicenseIdentificationPipelineStepTrace(String algorithm, Map<String, Object> parameters, Set<LicenseMatch> matchSet) implements LicenseIdentificationPipelineStepTrace {

            @Override
            public long step() {
                return 1;
            }

            @Override
            public String operation() {
                return algorithm + "-identification";
            }

            @Override
            public Map<String, Object> parameters() {
                return parameters;
            }

            @Override
            public Map<String, Float> matches() {
                return matchSet.stream().map(m -> entry(m.license(), m.confidence()))
                        .collect(toUnmodifiableMap(Map.Entry::getKey, Map.Entry::getValue, (f, s) -> s));
            }

            @Override
            public boolean terminated() {
                return true;
            }
        }
        return new LicenseIdentificationPipelineTrace("automatic-unary-pipeline", match.license(),
                match.confidence(), algorithm, parameters, List.of(
                new UnaryLicenseIdentificationPipelineStepTrace(algorithm, parameters, matches)
        ));
    }
}
