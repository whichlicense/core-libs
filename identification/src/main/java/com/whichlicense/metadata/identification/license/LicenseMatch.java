/*
 * Copyright (c) 2023 - for information on the respective copyright owner
 * see the NOTICE file and/or the repository https://github.com/whichlicense/core-libs.
 *
 * SPDX-License-Identifier: Apache-2.0
 */
package com.whichlicense.metadata.identification.license;

import java.util.Map;

public interface LicenseMatch extends Comparable<LicenseMatch> {
    String license();

    float confidence();

    String algorithm();

    Map<String, Object> parameters();

    @Override
    default int compareTo(LicenseMatch other) {
        return Float.compare(other.confidence(), confidence());
    }
}
