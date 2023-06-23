/*
 * Copyright (c) 2023 - for information on the respective copyright owner
 * see the NOTICE file and/or the repository https://github.com/whichlicense/core-libs.
 *
 * SPDX-License-Identifier: Apache-2.0
 */

import com.whichlicense.metadata.identification.license.LicenseHasher;
import com.whichlicense.metadata.identification.license.LicenseIdentificationPipeline;
import com.whichlicense.metadata.identification.license.LicenseIdentifier;

open module whichlicense.identification.license {
    exports com.whichlicense.metadata.identification.license;
    exports com.whichlicense.metadata.identification.license.internal to whichlicense.cli, whichlicense.identification.license.panama, whichlicense.identification.license.wasm;
    exports com.whichlicense.metadata.identification.license.jmh to whichlicense.identification.license.panama, whichlicense.identification.license.wasm, whichlicense.identification.license.jmh;
    exports com.whichlicense.metadata.identification.license.pipeline;
    uses LicenseHasher;
    uses LicenseIdentifier;
    uses LicenseIdentificationPipeline;
}
