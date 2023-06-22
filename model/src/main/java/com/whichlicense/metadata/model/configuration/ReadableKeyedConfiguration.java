/*
 * Copyright (c) 2023 - for information on the respective copyright owner
 * see the NOTICE file and/or the repository https://github.com/whichlicense/core-libs.
 *
 * SPDX-License-Identifier: Apache-2.0
 */
package com.whichlicense.metadata.model.configuration;

public sealed interface ReadableKeyedConfiguration permits KeyedConfiguration {
    boolean getBoolean(String key);

    int getInteger(String key);

    long getLong(String key);

    String getString(String key);
}
