/*
 * Copyright (c) 2023 - for information on the respective copyright owner
 * see the NOTICE file and/or the repository https://github.com/whichlicense/core-libs.
 *
 * SPDX-License-Identifier: Apache-2.0
 */
package com.whichlicense.metadata.identification.license;

import com.whichlicense.metadata.identification.license.internal.LicenseIdentificationPipelineStepTraceImpl;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.util.Collections.emptyList;

public record LicenseIdentificationPipelineTrace(String name, String license, float confidence, String algorithm, Map<String, Object> parameters, List<LicenseIdentificationPipelineStepTrace> traces) {
    public static LicenseIdentificationPipelineTrace empty(String name, String algorithm, Map<String, Object> parameters) {
        return new LicenseIdentificationPipelineTrace(name, null, 0f, algorithm, parameters, emptyList());
    }

    public static LicenseIdentificationPipelineTrace ofMatchSet(String algorithm, Map<String, Object> parameters, Set<LicenseMatch> matches) {
        if (matches.isEmpty()) return empty("automatic-unary-pipeline", algorithm, parameters);
        var match = matches.stream().findFirst().orElseThrow();

        return new LicenseIdentificationPipelineTrace("automatic-unary-pipeline", match.license(),
                match.confidence(), algorithm, parameters, List.of(
                        new LicenseIdentificationPipelineStepTraceImpl(1L,
                                algorithm + "-identification", algorithm, parameters, matches)
        ));
    }
}
