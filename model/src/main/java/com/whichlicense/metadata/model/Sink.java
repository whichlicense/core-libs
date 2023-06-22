/*
 * Copyright (c) 2023 - for information on the respective copyright owner
 * see the NOTICE file and/or the repository https://github.com/whichlicense/core-libs.
 *
 * SPDX-License-Identifier: Apache-2.0
 */
package com.whichlicense.metadata.model;

import com.whichlicense.metadata.model.Metadata;

@FunctionalInterface
public interface Sink<A extends Metadata> {
    void sink(A metadata);
}
