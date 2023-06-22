/*
 * Copyright (c) 2023 - for information on the respective copyright owner
 * see the NOTICE file and/or the repository https://github.com/whichlicense/core-libs.
 *
 * SPDX-License-Identifier: Apache-2.0
 */
package com.whichlicense.metadata.model.algebra;

import com.whichlicense.metadata.model.Metadata;
import com.whichlicense.metadata.model.Sink;

public interface MetadataSource<S, A extends Metadata> {
    Sink<? super A> source(S source);
}
