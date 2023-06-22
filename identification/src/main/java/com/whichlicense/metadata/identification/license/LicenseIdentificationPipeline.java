/*
 * Copyright (c) 2023 - for information on the respective copyright owner
 * see the NOTICE file and/or the repository https://github.com/whichlicense/core-libs.
 *
 * SPDX-License-Identifier: Apache-2.0
 */
package com.whichlicense.metadata.identification.license;

import com.whichlicense.metadata.identification.license.internal.LazyLicenseIdentificationPipelineHolder;
import com.whichlicense.metadata.identification.license.pipeline.PipelineStep;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public interface LicenseIdentificationPipeline {
    private static LicenseIdentificationPipeline lookup(String algorithm) {
        return LazyLicenseIdentificationPipelineHolder.PIPELINES.stream()
                .filter(i -> Objects.equals(i.algorithm(), algorithm))
                .findFirst().orElseThrow();
    }

    static List<LicenseIdentificationPipelineStepTrace> identifyLicenses(String algorithm, List<PipelineStep> steps, float threshold, String license) {
        return lookup(algorithm).identifyLicenses(steps, threshold, license);
    }

    static List<LicenseIdentificationPipelineStepTrace> identifyLicenses(String algorithm, List<PipelineStep> steps, String license) {
        return lookup(algorithm).identifyLicenses(steps, license);
    }

    static Optional<LicenseMatch> identifyLicense(String algorithm, List<PipelineStep> steps, float threshold, String license) {
        return lookup(algorithm).identifyLicense(steps, threshold, license);
    }

    static Optional<LicenseMatch> identifyLicense(String algorithm, List<PipelineStep> steps, String license) {
        return lookup(algorithm).identifyLicense(steps, license);
    }

    List<LicenseIdentificationPipelineStepTrace> identifyLicenses(List<PipelineStep> steps, float threshold, String license);

    default List<LicenseIdentificationPipelineStepTrace> identifyLicenses(List<PipelineStep> steps, String license) {
        return this.identifyLicenses(steps, 100, license);
    }

    default Optional<LicenseMatch> identifyLicense(List<PipelineStep> steps, float threshold, String license) {
        return identifyLicenses(steps, threshold, license).stream()
                .reduce((first, second) -> second)
                .map(LicenseIdentificationPipelineStepTrace::matches)
                .stream().flatMap(Collection::stream)
                .findFirst();
    }

    default Optional<LicenseMatch> identifyLicense(List<PipelineStep> steps, String license) {
        return identifyLicense(steps, 100, license);
    }

    String algorithm();
}
