/*
 * Copyright (c) 2023 - for information on the respective copyright owner
 * see the NOTICE file and/or the repository https://github.com/whichlicense/core-libs.
 *
 * SPDX-License-Identifier: Apache-2.0
 */
package com.whichlicense.metadata.identification.license;

import com.whichlicense.metadata.identification.license.internal.LazyLicenseIdentificationPipelineHolder;
import com.whichlicense.metadata.identification.license.pipeline.PipelineStep;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public interface LicenseIdentificationPipeline {
    private static LicenseIdentificationPipeline lookup(String algorithm) {
        return LazyLicenseIdentificationPipelineHolder.PIPELINES.stream()
                .filter(i -> Objects.equals(i.algorithm(), algorithm))
                .findFirst().orElseThrow();
    }

    static LicenseIdentificationPipelineTrace identifyLicenses(String name, String algorithm, List<PipelineStep> steps, float threshold, String license) {
        return lookup(algorithm).identifyLicenses(name, steps, threshold, license);
    }

    static LicenseIdentificationPipelineTrace identifyLicenses(String name, String algorithm, List<PipelineStep> steps, String license) {
        return lookup(algorithm).identifyLicenses(name, steps, license);
    }

    static Optional<LicenseMatch> identifyLicense(String name, String algorithm, List<PipelineStep> steps, float threshold, String license) {
        return lookup(algorithm).identifyLicense(name, steps, threshold, license);
    }

    static Optional<LicenseMatch> identifyLicense(String name, String algorithm, List<PipelineStep> steps, String license) {
        return lookup(algorithm).identifyLicense(name, steps, license);
    }

    LicenseIdentificationPipelineTrace identifyLicenses(String name, List<PipelineStep> steps, float threshold, String license);

    default LicenseIdentificationPipelineTrace identifyLicenses(String name, List<PipelineStep> steps, String license) {
        return this.identifyLicenses(name, steps, 100, license);
    }

    default Optional<LicenseMatch> identifyLicense(String name, List<PipelineStep> steps, float threshold, String license) {
        record LicenseIdentificationPipelineMatch(String license, float confidence, String algorithm,
                                                  Map<String, Object> parameters) implements LicenseMatch {
        }

        var trace = identifyLicenses(name, steps, threshold, license);
        return Optional.ofNullable(trace.license())
                .map(l -> new LicenseIdentificationPipelineMatch(l, trace.confidence(),
                        trace.algorithm(), trace.parameters()));
    }

    default Optional<LicenseMatch> identifyLicense(String name, List<PipelineStep> steps, String license) {
        return identifyLicense(name, steps, 100, license);
    }

    String algorithm();
}
