/*
 * Copyright (c) 2023 - for information on the respective copyright owner
 * see the NOTICE file and/or the repository https://github.com/whichlicense/core-libs.
 *
 * SPDX-License-Identifier: Apache-2.0
 */
package com.whichlicense.metadata.model.configuration;

public sealed interface WritableKeyedConfiguration permits KeyedConfiguration {
    void setBoolean(String key, boolean value);

    void setInteger(String key, int value);

    void setLong(String key, long value);

    void setString(String key, String value);
}
