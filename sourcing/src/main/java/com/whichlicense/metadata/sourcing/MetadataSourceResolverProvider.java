/*
 * Copyright (c) 2023 - for information on the respective copyright owner
 * see the NOTICE file and/or the repository https://github.com/whichlicense/core-libs.
 *
 * SPDX-License-Identifier: Apache-2.0
 */
package com.whichlicense.metadata.sourcing;

import java.util.Comparator;
import java.util.ServiceLoader;
import java.util.function.UnaryOperator;
import java.util.stream.StreamSupport;

public interface MetadataSourceResolverProvider {
    static MetadataSourceResolver loadChain() {
        var loader = ServiceLoader.load(MetadataSourceResolverProvider.class);
        return StreamSupport.stream(loader.spliterator(), false)
                .sorted(Comparator.comparingInt(MetadataSourceResolverProvider::priority).reversed())
                .map(MetadataSourceResolverProvider::resolver)
                .reduce((prev, curr) -> next -> prev.apply(curr.apply(next)))
                .map(p -> p.apply(null)).orElseThrow();
    }

    UnaryOperator<MetadataSourceResolver> resolver();

    default int priority() {
        return 1;
    }
}
