/*
 * Copyright (c) 2023 - for information on the respective copyright owner
 * see the NOTICE file and/or the repository https://github.com/whichlicense/core-libs.
 *
 * SPDX-License-Identifier: Apache-2.0
 */
package com.whichlicense.metadata.sourcing;

import com.whichlicense.configuration.ReadableKeyedConfiguration;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

public interface MetadataSourceResolver {
    MetadataSourceResolver next();

    default boolean handles(Path path, ReadableKeyedConfiguration configuration) {
        return false;
    }

    default boolean handles(URL url, ReadableKeyedConfiguration configuration) {
        return false;
    }

    default MetadataSource handle(Path path, ReadableKeyedConfiguration configuration) {
        throw new UnsupportedOperationException("Unsupported " + path + " for " + getClass().getCanonicalName());
    }

    default MetadataSource handle(URL url, ReadableKeyedConfiguration configuration) {
        throw new UnsupportedOperationException("Unsupported " + url + " for " + getClass().getCanonicalName());
    }

    default Optional<MetadataSource> resolve(String location, ReadableKeyedConfiguration configuration) {
        try {
            return resolve(new URL(location), configuration);
        } catch (MalformedURLException ignored) {
            return resolve(Paths.get(location), configuration);
        }
    }

    default Optional<MetadataSource> resolve(Path path, ReadableKeyedConfiguration configuration) {
        if (path != null) {
            if (handles(path, configuration)) return Optional.ofNullable(handle(path, configuration));
            if (next() != null) return next().resolve(path, configuration);
        }
        return Optional.empty();
    }

    default Optional<MetadataSource> resolve(URL url, ReadableKeyedConfiguration configuration) {
        if (url != null) {
            if (handles(url, configuration)) return Optional.ofNullable(handle(url, configuration));
            if (next() != null) return next().resolve(url, configuration);
        }
        return Optional.empty();
    }
}
