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

public record LicenseIdentificationPipelineTrace(String name, String license, float confidence, String algorithm, Map<String, Object> parameters, List<LicenseIdentificationPipelineStepTrace> traces, String input) {
    public static LicenseIdentificationPipelineTrace empty(String name, String algorithm, Map<String, Object> parameters, String input) {
        return new LicenseIdentificationPipelineTrace(name, null, 0f, algorithm, parameters, emptyList(), input);
    }

    public static LicenseIdentificationPipelineTrace ofMatchSet(String algorithm, Map<String, Object> parameters, Set<LicenseMatch> matches, String input) {
        if (matches.isEmpty()) return empty("automatic-unary-pipeline", algorithm, parameters, input);
        var match = matches.stream().findFirst().orElseThrow();

        return new LicenseIdentificationPipelineTrace("automatic-unary-pipeline", match.license(),
                match.confidence(), algorithm, parameters, List.of(
                        new LicenseIdentificationPipelineStepTraceImpl(0L,
                                algorithm + "-identification", algorithm, parameters, matches)
        ), input);
    }
}
