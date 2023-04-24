/*
 * Copyright (c) 2023 - for information on the respective copyright owner
 * see the NOTICE file and/or the repository https://github.com/whichlicense/core-libs.
 *
 * SPDX-License-Identifier: Apache-2.0
 */
package com.whichlicense.metadata.sourcing;

import java.net.URL;
import java.nio.file.Path;

public sealed interface MetadataOrigin {
    record RawPath(Path path) implements MetadataOrigin {}
    record RawURL(URL url) implements MetadataOrigin {}
}
