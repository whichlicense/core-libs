/*
 * Copyright (c) 2023 - for information on the respective copyright owner
 * see the NOTICE file and/or the repository https://github.com/whichlicense/core-libs.
 *
 * SPDX-License-Identifier: Apache-2.0
 */
package com.whichlicense.metadata.sourcing.artifact.zip;

import com.whichlicense.metadata.sourcing.MetadataOrigin.RawPath;
import com.whichlicense.metadata.sourcing.MetadataOrigin.RawURL;
import com.whichlicense.metadata.sourcing.MetadataSource;
import com.whichlicense.metadata.sourcing.MetadataSourceResolver;
import com.whichlicense.metadata.sourcing.internal.ArchiveHelper;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;

public record ZipMetadataSourceResolver(MetadataSourceResolver next) implements MetadataSourceResolver {
    @Override
    public boolean handles(Path path) {
        return Files.isRegularFile(path) && path.toFile().getName().endsWith(".zip");
    }

    @Override
    public boolean handles(URL url) {
        return url.toString().endsWith(".zip") && !url.getHost().endsWith(".zip");
    }

    @Override
    public MetadataSource handle(Path path) {
        return new ZipMetadataSource(ArchiveHelper.resolveRoot(path), new RawPath(path));
    }

    @Override
    public MetadataSource handle(URL url) {
        return new ZipMetadataSource(ArchiveHelper.resolveRoot(url), new RawURL(url));
    }
}
