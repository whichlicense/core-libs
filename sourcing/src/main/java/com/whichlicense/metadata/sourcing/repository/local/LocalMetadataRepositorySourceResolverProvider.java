/*
 * Copyright (c) 2023 - for information on the respective copyright owner
 * see the NOTICE file and/or the repository https://github.com/whichlicense/core-libs.
 *
 * SPDX-License-Identifier: Apache-2.0
 */
package com.whichlicense.metadata.sourcing.repository.local;

import com.whichlicense.metadata.sourcing.MetadataSourceResolver;
import com.whichlicense.metadata.sourcing.MetadataSourceResolverProvider;

import java.util.function.UnaryOperator;

public final class LocalMetadataRepositorySourceResolverProvider implements MetadataSourceResolverProvider {
    @Override
    public UnaryOperator<MetadataSourceResolver> resolver() {
        return LocalMetadataRepositorySourceResolver::new;
    }

    @Override
    public int priority() {
        return Integer.MIN_VALUE;
    }
}
