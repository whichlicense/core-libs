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
     * @return The metadata seeker class.
     * @since 0.0.0
     */
    Class<? extends MetadataSeeker> seeker();

    /**
     * A record that represents a metadata match for a file.
     *
     * @param relativePath the relative path of the matched file
     * @param seeker       the metadata seeker class associated with this metadata match
     * @author David Greven
     * @version 0
     * @since 0.0.0
     */
    record FileMatch(Path relativePath, Class<? extends MetadataSeeker> seeker) implements MetadataMatch {
    }

    /**
     * A record that represents a metadata match for a directory.
     *
     * @param relativePath the relative path of the matched directory
     * @param seeker       the metadata seeker class associated with this metadata match
     * @author David Greven
     * @version 0
     * @since 0.0.0
     */
    record DirectoryMatch(Path relativePath, Class<? extends MetadataSeeker> seeker) implements MetadataMatch {
    }
}
