/*
 * Copyright (c) 2023 - for information on the respective copyright owner
 * see the NOTICE file and/or the repository https://github.com/whichlicense/core-libs.
 *
 * SPDX-License-Identifier: Apache-2.0
 */
package com.whichlicense.metadata.model.extractor;

import com.whichlicense.metadata.model.Metadata;
import com.whichlicense.metadata.model.Sink;
import com.whichlicense.metadata.model.algebra.MetadataSource;

public interface MetadataExtractor<S, A extends Metadata> extends MetadataSource<S, A> {
    Sink<? super A> extract(S source);

    @Override
    default Sink<? super A> source(S source) {
        return extract(source);
    }
}
