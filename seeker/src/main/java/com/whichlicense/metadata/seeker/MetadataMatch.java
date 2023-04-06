/*
 * Copyright (c) 2023 - for information on the respective copyright owner
 * see the NOTICE file and/or the repository https://github.com/whichlicense/core-libs.
 *
 * SPDX-License-Identifier: Apache-2.0
 */
package com.whichlicense.metadata.seeker;

import java.nio.file.Path;

/**
 * A sealed interface that represents a match of metadata information.
 *
 * @author David Greven
 * @version 0
 * @since 0.0.0
 */
public sealed interface MetadataMatch {
    /**
     * Returns the metadata seeker associated with this metadata match.
     *
     * @return The metadata seeker as a MetadataSeeker instance.
     * @since 0.0.0
     */
    MetadataSeeker seeker();

    /**
     * A record that represents a metadata match for a file.
     *
     * @param relativePath the relative path of the matched file
     * @param seeker       the metadata seeker associated with this metadata match
     * @author David Greven
     * @version 0
     * @since 0.0.0
     */
    record FileMatch(Path relativePath, MetadataSeeker seeker) implements MetadataMatch {
    }
}
