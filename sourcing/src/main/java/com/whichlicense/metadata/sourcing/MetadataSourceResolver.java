/*
 * Copyright (c) 2023 - for information on the respective copyright owner
 * see the NOTICE file and/or the repository https://github.com/whichlicense/core-libs.
 *
 * SPDX-License-Identifier: Apache-2.0
 */
package com.whichlicense.metadata.sourcing;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

public interface MetadataSourceResolver {
    MetadataSourceResolver next();

    default boolean handles(Path path) {
        return false;
    }

    default boolean handles(URL url) {
        return false;
    }

    default MetadataSource handle(Path path) {
        throw new UnsupportedOperationException("Unsupported " + path + " for " + getClass().getCanonicalName());
    }

    default MetadataSource handle(URL url) {
        throw new UnsupportedOperationException("Unsupported " + url + " for " + getClass().getCanonicalName());
    }

    default Optional<MetadataSource> resolve(String location) {
        try {
            return resolve(new URL(location));
        } catch (MalformedURLException ignored) {
            return resolve(Paths.get(location));
        }
    }

    default Optional<MetadataSource> resolve(Path path) {
        if (path != null) {
            if (handles(path)) return Optional.ofNullable(handle(path));
            if (next() != null) return next().resolve(path);
        }
        return Optional.empty();
    }

    default Optional<MetadataSource> resolve(URL url) {
        if (url != null) {
            if (handles(url)) return Optional.ofNullable(handle(url));
            if (next() != null) return next().resolve(url);
        }
        return Optional.empty();
    }
}
