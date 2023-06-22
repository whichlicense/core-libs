/*
 * Copyright (c) 2023 - for information on the respective copyright owner
 * see the NOTICE file and/or the repository https://github.com/whichlicense/core-libs.
 *
 * SPDX-License-Identifier: Apache-2.0
 */
package com.whichlicense.metadata.identification.license;

import com.whichlicense.metadata.identification.license.internal.LazyLicenseHasherHolder;

import java.util.Map;
import java.util.Objects;

public interface LicenseHasher {
    private static LicenseHasher lookup(String algorithm) {
        return LazyLicenseHasherHolder.HASHERS.stream()
                .filter(i -> Objects.equals(i.algorithm(), algorithm))
                .findFirst().orElseThrow();
    }

    static String computeHash(String algorithm, String license) {
        return computeHash(algorithm, (LicenseNormalization) null, license);
    }

    static String computeHash(String algorithm, LicenseNormalization normalization, String license) {
        return lookup(algorithm).computeHash(license, normalization);
    }

    static String computeHash(String algorithm, Map<String, Object> parameters, String license) {
        return computeHash(algorithm, null, parameters, license);
    }

    static String computeHash(String algorithm, LicenseNormalization normalization, Map<String, Object> parameters, String license) {
        return lookup(algorithm).computeHash(license, normalization, parameters);
    }

    default String computeHash(String license) {
        return computeHash(license, (LicenseNormalization) null);
    }

    String computeHash(String license, LicenseNormalization normalization);

    default String computeHash(String license, Map<String, Object> parameters) {
        return computeHash(license, null, parameters);
    }

    String computeHash(String license, LicenseNormalization normalization, Map<String, Object> parameters);

    String algorithm();
}
