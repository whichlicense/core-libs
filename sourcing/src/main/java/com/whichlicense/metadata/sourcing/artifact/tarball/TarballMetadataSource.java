/*
 * Copyright (c) 2023 - for information on the respective copyright owner
 * see the NOTICE file and/or the repository https://github.com/whichlicense/core-libs.
 *
 * SPDX-License-Identifier: Apache-2.0
 */
package com.whichlicense.metadata.sourcing.artifact.tarball;

import com.whichlicense.metadata.sourcing.MetadataOrigin;
import com.whichlicense.metadata.sourcing.artifact.MetadataArtifactSource;

import java.nio.file.Path;
import java.util.Objects;

public record TarballMetadataSource(Path path, MetadataOrigin origin) implements MetadataArtifactSource {
    public TarballMetadataSource {
        Objects.requireNonNull(path);
        Objects.requireNonNull(origin);
    }
}
