/*
 * Copyright (c) 2023 - for information on the respective copyright owner
 * see the NOTICE file and/or the repository https://github.com/whichlicense/core-libs.
 *
 * SPDX-License-Identifier: Apache-2.0
 */
package com.whichlicense.metadata.sourcing.artifact.tarball;

import com.whichlicense.metadata.sourcing.MetadataOrigin.RawPath;
import com.whichlicense.metadata.sourcing.MetadataOrigin.RawURL;
import com.whichlicense.metadata.sourcing.MetadataSource;
import com.whichlicense.metadata.sourcing.MetadataSourceResolver;
import com.whichlicense.metadata.sourcing.internal.ArchiveHelper;
import com.whichlicense.metadata.sourcing.internal.ArchiveHelper.ArchiveStreamConstructor;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;

import java.io.BufferedInputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;

public record TarballMetadataSourceResolver(MetadataSourceResolver next) implements MetadataSourceResolver {
    private static ArchiveStreamConstructor<TarArchiveInputStream> archiveStream() {
        return stream -> new TarArchiveInputStream(new GzipCompressorInputStream(new BufferedInputStream(stream)));
    }

    @Override
    public boolean handles(Path path) {
        return Files.isRegularFile(path) && (path.toFile().getName().endsWith(".tgz") || path.toFile().getName().endsWith(".tar.gz"));
    }

    @Override
    public boolean handles(URL url) {
        return url.toString().endsWith(".tgz") || url.toString().endsWith(".tar.gz");
    }

    @Override
    public MetadataSource handle(Path path) {
        return new TarballMetadataSource(ArchiveHelper.resolveRoot(path, archiveStream()), new RawPath(path));
    }

    @Override
    public MetadataSource handle(URL url) {
        return new TarballMetadataSource(ArchiveHelper.resolveRoot(url, archiveStream()), new RawURL(url));
    }
}
