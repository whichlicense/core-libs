/*
 * Copyright (c) 2023 - for information on the respective copyright owner
 * see the NOTICE file and/or the repository https://github.com/whichlicense/core-libs.
 *
 * SPDX-License-Identifier: Apache-2.0
 */
package com.whichlicense.metadata.sourcing.repository.local;

import com.whichlicense.metadata.sourcing.MetadataOrigin;
import com.whichlicense.metadata.sourcing.repository.MetadataRepositorySource;

import java.nio.file.Path;
import java.util.Objects;

public record LocalMetadataRepositorySource(Path path, MetadataOrigin origin) implements MetadataRepositorySource {
    public LocalMetadataRepositorySource(Path path) {
        this(path, new MetadataOrigin.RawPath(path));
    }

    public LocalMetadataRepositorySource {
        Objects.requireNonNull(path);
        Objects.requireNonNull(origin);
    }
}
