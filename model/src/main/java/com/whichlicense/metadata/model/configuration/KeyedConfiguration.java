/*
 * Copyright (c) 2023 - for information on the respective copyright owner
 * see the NOTICE file and/or the repository https://github.com/whichlicense/core-libs.
 *
 * SPDX-License-Identifier: Apache-2.0
 */
package com.whichlicense.metadata.model.configuration;

import com.whichlicense.metadata.model.configuration.ReadableKeyedConfiguration;
import com.whichlicense.metadata.model.configuration.WritableKeyedConfiguration;

public non-sealed interface KeyedConfiguration extends ReadableKeyedConfiguration, WritableKeyedConfiguration {
}
