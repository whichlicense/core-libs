/*
 * Copyright (c) 2023 - for information on the respective copyright owner
 * see the NOTICE file and/or the repository https://github.com/whichlicense/core-libs.
 *
 * SPDX-License-Identifier: Apache-2.0
 */
package com.whichlicense.versioning;

/**
 * The VersionScheme SPI interface to facilitate verifying and parsing version information.
 *
 * @author David Greven
 * @version 0
 * @since 0.0.0
 */
public interface VersionScheme {
    int STANDARDIZED = 1;
    int PROPRIETARY = 0;

    /**
     * @param version
     * @return
     */
    Version parse(String version) throws VersionFormatException;

    /**
     * @param version
     * @return
     */
    boolean matches(String version);

    default int priority() {
        return PROPRIETARY;
    }
}
