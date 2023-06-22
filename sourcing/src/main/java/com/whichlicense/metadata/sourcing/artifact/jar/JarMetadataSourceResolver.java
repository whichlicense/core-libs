/*
 * Copyright (c) 2023 - for information on the respective copyright owner
 * see the NOTICE file and/or the repository https://github.com/whichlicense/core-libs.
 *
 * SPDX-License-Identifier: Apache-2.0
 */
package com.whichlicense.metadata.sourcing.artifact.jar;

import com.whichlicense.configuration.ReadableKeyedConfiguration;
import com.whichlicense.metadata.sourcing.MetadataOrigin.RawPath;
import com.whichlicense.metadata.sourcing.MetadataOrigin.RawURL;
import com.whichlicense.metadata.sourcing.MetadataSource;
import com.whichlicense.metadata.sourcing.MetadataSourceResolver;
import com.whichlicense.metadata.sourcing.internal.ArchiveHelper;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;

public record JarMetadataSourceResolver(MetadataSourceResolver next) implements MetadataSourceResolver {
    @Override
    public boolean handles(Path path, ReadableKeyedConfiguration configuration) {
        return Files.isRegularFile(path) && path.toFile().getName().endsWith(".jar");
    }

    @Override
    public boolean handles(URL url, ReadableKeyedConfiguration configuration) {
        return url.toString().endsWith(".jar");
    }

    @Override
    public MetadataSource handle(Path path, ReadableKeyedConfiguration configuration) {
        return new JarMetadataSource(ArchiveHelper.resolveRoot(path), new RawPath(path));
    }

    @Override
    public MetadataSource handle(URL url, ReadableKeyedConfiguration configuration) {
        return new JarMetadataSource(ArchiveHelper.resolveRoot(url), new RawURL(url));
    }
}
