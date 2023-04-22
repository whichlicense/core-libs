/*
 * Copyright (c) 2023 - for information on the respective copyright owner
 * see the NOTICE file and/or the repository https://github.com/whichlicense/core-libs.
 *
 * SPDX-License-Identifier: Apache-2.0
 */
package com.whichlicense.metadata.sourcing.repository.local;

import com.whichlicense.metadata.sourcing.MetadataSource;
import com.whichlicense.metadata.sourcing.MetadataSourceResolver;

import java.nio.file.Files;
import java.nio.file.Path;

public record LocalMetadataRepositorySourceResolver(MetadataSourceResolver next) implements MetadataSourceResolver {
    @Override
    public boolean handles(Path path) {
        return Files.isDirectory(path);
    }

    @Override
    public MetadataSource handle(Path path) throws UnsupportedOperationException {
        return new LocalMetadataRepositorySource(path);
    }
}
