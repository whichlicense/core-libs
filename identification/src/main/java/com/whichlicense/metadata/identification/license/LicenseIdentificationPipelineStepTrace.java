/*
 * Copyright (c) 2023 - for information on the respective copyright owner
 * see the NOTICE file and/or the repository https://github.com/whichlicense/core-libs.
 *
 * SPDX-License-Identifier: Apache-2.0
 */
package com.whichlicense.metadata.identification.license;

import java.util.Set;

public interface LicenseIdentificationPipelineStepTrace {
    long step();

    Set<LicenseMatch> matches();

    boolean terminated();
}
