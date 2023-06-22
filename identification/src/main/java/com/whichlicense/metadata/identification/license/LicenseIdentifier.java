/*
 * Copyright (c) 2023 - for information on the respective copyright owner
 * see the NOTICE file and/or the repository https://github.com/whichlicense/core-libs.
 *
 * SPDX-License-Identifier: Apache-2.0
 */
package com.whichlicense.metadata.identification.license;

import com.whichlicense.metadata.identification.license.internal.LazyLicenseIdentifierHolder;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

public interface LicenseIdentifier {
    static Set<LicenseMatch> identifyLicenses(String algorithm, String license) {
        return identifyLicenses(algorithm, (LicenseNormalization) null, license);
    }

    static Set<LicenseMatch> identifyLicenses(String algorithm, Map<String, Object> parameters, String license) {
        return identifyLicenses(algorithm, null, parameters, license);
    }

    static Set<LicenseMatch> identifyLicenses(String algorithm, LicenseNormalization normalization, String license) {
        return lookup(algorithm).identifyLicenses(license, normalization);
    }

    static Set<LicenseMatch> identifyLicenses(String algorithm, LicenseNormalization normalization, Map<String, Object> parameters, String license) {
        return lookup(algorithm).identifyLicenses(license, normalization, parameters);
    }

    static Optional<LicenseMatch> identifyLicense(String algorithm, String license) {
        return identifyLicense(algorithm, (LicenseNormalization) null, license);
    }

    static Optional<LicenseMatch> identifyLicense(String algorithm, Map<String, Object> parameters, String license) {
        return identifyLicense(algorithm, null, parameters, license);
    }

    static Optional<LicenseMatch> identifyLicense(String algorithm, LicenseNormalization normalization, String license) {
        return lookup(algorithm).identifyLicense(license, normalization);
    }

    static Optional<LicenseMatch> identifyLicense(String algorithm, LicenseNormalization normalization, Map<String, Object> parameters, String license) {
        return lookup(algorithm).identifyLicense(license, normalization, parameters);
    }

    private static LicenseIdentifier lookup(String algorithm) {
        return LazyLicenseIdentifierHolder.IDENTIFIERS.stream()
                .filter(i -> Objects.equals(i.algorithm(), algorithm))
                .findFirst().orElseThrow();
    }

    default Set<LicenseMatch> identifyLicenses(String license) {
        return identifyLicenses(license, (LicenseNormalization) null);
    }

    Set<LicenseMatch> identifyLicenses(String license, LicenseNormalization normalization);

    default Set<LicenseMatch> identifyLicenses(String license, Map<String, Object> parameters) {
        return identifyLicenses(license, null, parameters);
    }

    Set<LicenseMatch> identifyLicenses(String license, LicenseNormalization normalization, Map<String, Object> parameters);

    default Optional<LicenseMatch> identifyLicense(String license) {
        return identifyLicense(license, (LicenseNormalization) null);
    }

    default Optional<LicenseMatch> identifyLicense(String license, LicenseNormalization normalization) {
        return identifyLicenses(license, normalization).stream().findFirst();
    }

    default Optional<LicenseMatch> identifyLicense(String license, Map<String, Object> parameters) {
        return identifyLicense(license, null, parameters);
    }

    default Optional<LicenseMatch> identifyLicense(String license, LicenseNormalization normalization, Map<String, Object> parameters) {
        return identifyLicenses(license, normalization, parameters).stream().findFirst();
    }

    String algorithm();
}
