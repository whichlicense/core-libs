/*
 * Copyright (c) 2023 - for information on the respective copyright owner
 * see the NOTICE file and/or the repository https://github.com/whichlicense/core-libs.
 *
 * SPDX-License-Identifier: Apache-2.0
 */
package com.whichlicense.metadata.identification.license.internal;

import com.whichlicense.metadata.identification.license.LicenseIdentificationPipelineStepTrace;
import com.whichlicense.metadata.identification.license.LicenseMatch;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import static java.util.Map.entry;
import static java.util.stream.Collectors.toMap;

public record LicenseIdentificationPipelineStepTraceImpl(long step, String operation, String algorithm, Map<String, Object> parameters, Map<String, Float> matches, boolean terminated) implements LicenseIdentificationPipelineStepTrace {
    public LicenseIdentificationPipelineStepTraceImpl(long step, String operation, String algorithm, Map<String, Object> parameters, Set<LicenseMatch> matches) {
        this(step, operation, algorithm, parameters, matches.stream().map(m -> entry(m.license(), m.confidence()))
                .collect(toMap(Entry::getKey, Entry::getValue, (f, s) -> s, TreeMap::new)), true);
    }
}
