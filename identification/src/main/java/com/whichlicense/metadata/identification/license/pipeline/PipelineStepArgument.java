/*
 * Copyright (c) 2023 - for information on the respective copyright owner
 * see the NOTICE file and/or the repository https://github.com/whichlicense/core-libs.
 *
 * SPDX-License-Identifier: Apache-2.0
 */
package com.whichlicense.metadata.identification.license.pipeline;

import java.util.Objects;
import java.util.regex.Pattern;

public sealed interface PipelineStepArgument {
    record Regex(Pattern pattern) implements PipelineStepArgument {
        public Regex {
            Objects.requireNonNull(pattern);
        }
    }

    record Text(String string) implements PipelineStepArgument {
        public Text {
            Objects.requireNonNull(string);
        }
    }
}
