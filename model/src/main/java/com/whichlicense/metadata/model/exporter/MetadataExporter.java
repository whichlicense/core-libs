/*
 * Copyright (c) 2023 - for information on the respective copyright owner
 * see the NOTICE file and/or the repository https://github.com/whichlicense/core-libs.
 *
 * SPDX-License-Identifier: Apache-2.0
 */
package com.whichlicense.metadata.model.exporter;

import com.whichlicense.metadata.model.Metadata;
import com.whichlicense.metadata.model.algebra.MetadataSink;
import com.whichlicense.metadata.model.configuration.ReadableKeyedConfiguration;

public interface MetadataExporter<T, A extends Metadata & MetadataExporter<T, A>> extends MetadataSink<A> {
    T export(ReadableKeyedConfiguration configuration);
}
