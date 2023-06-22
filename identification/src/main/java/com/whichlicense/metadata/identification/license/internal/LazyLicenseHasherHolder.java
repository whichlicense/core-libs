/*
 * Copyright (c) 2023 - for information on the respective copyright owner
 * see the NOTICE file and/or the repository https://github.com/whichlicense/core-libs.
 *
 * SPDX-License-Identifier: Apache-2.0
 */
package com.whichlicense.metadata.identification.license.internal;

import com.whichlicense.metadata.identification.license.LicenseHasher;

import java.util.ServiceLoader;
import java.util.ServiceLoader.Provider;
import java.util.Set;

import static java.util.stream.Collectors.toUnmodifiableSet;

public final class LazyLicenseHasherHolder {
    public static final Set<LicenseHasher> HASHERS = ServiceLoader.load(LicenseHasher.class)
            .stream().map(Provider::get).collect(toUnmodifiableSet());
}
