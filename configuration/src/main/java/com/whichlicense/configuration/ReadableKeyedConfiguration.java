/*
 * Copyright (c) 2023 - for information on the respective copyright owner
 * see the NOTICE file and/or the repository https://github.com/whichlicense/core-libs.
 *
 * SPDX-License-Identifier: Apache-2.0
 */
package com.whichlicense.configuration;

import java.util.function.Consumer;

public sealed interface ReadableKeyedConfiguration permits KeyedConfiguration {
    boolean getBoolean(String key);

    void hasBoolean(String key, Consumer<Boolean> consumer);

    int getInteger(String key);

    void hasInteger(String key, Consumer<Integer> consumer);

    long getLong(String key);

    void hasLong(String key, Consumer<Long> consumer);

    String getString(String key);

    void hasString(String key, Consumer<String> consumer);
}
