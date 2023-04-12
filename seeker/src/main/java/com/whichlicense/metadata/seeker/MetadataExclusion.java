/*
 * Copyright (c) 2023 - for information on the respective copyright owner
 * see the NOTICE file and/or the repository https://github.com/whichlicense/core-libs.
 *
 * SPDX-License-Identifier: Apache-2.0
 */
package com.whichlicense.metadata.seeker;

import java.util.Set;

/**
 * An SPI interface that represents a metadata exclusion.
 *
 * @author David Greven
 * @version 0
 * @since 0.0.0
 */
public interface MetadataExclusion {
    /**
     * Returns a set of glob patterns that specify the metadata to be excluded.
     *
     * @return A set of glob patterns as strings.
     * @since 0.0.0
     */
    Set<String> globs();

    /**
     * Returns the type of metadata source that the exclusion is designed to work with.
     *
     * @return The type of metadata source as a MetadataSourceType enum value.
     * @since 0.0.0
     */
    MetadataSourceType type();
}
