/*
 * Copyright (c) 2023 - for information on the respective copyright owner
 * see the NOTICE file and/or the repository https://github.com/whichlicense/core-libs.
 *
 * SPDX-License-Identifier: Apache-2.0
 */
package com.whichlicense.metadata.identification.license.pipeline;

import com.whichlicense.metadata.identification.license.LicenseMatch;
import com.whichlicense.metadata.identification.license.pipeline.PipelineStepArgument.Regex;
import com.whichlicense.metadata.identification.license.pipeline.PipelineStepArgument.Text;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.regex.Pattern;

public sealed interface PipelineStep {
    static Remove remove(Pattern pattern) {
        return new Remove(new Regex(pattern));
    }

    static Remove remove(String string) {
        return new Remove(new Text(string));
    }

    static Replace replace(Pattern pattern, String replacement) {
        return new Replace(new Regex(pattern), replacement);
    }

    static Replace replace(String target, String replacement) {
        return new Replace(new Text(target), replacement);
    }

    static Custom custom(BiFunction<String, Set<LicenseMatch>, String> function) {
        return new Custom(function);
    }

    static Batch batch(List<PipelineStep> steps) {
        return new Batch(steps);
    }

    record Remove(PipelineStepArgument argument) implements PipelineStep {
        public Remove {
            Objects.requireNonNull(argument);
        }
    }

    record Replace(PipelineStepArgument argument, String string) implements PipelineStep {
        public Replace {
            Objects.requireNonNull(argument);
            Objects.requireNonNull(string);
        }
    }

    record Custom(BiFunction<String, Set<LicenseMatch>, String> function) implements PipelineStep {
        public Custom {
            Objects.requireNonNull(function);
        }
    }

    record Batch(List<PipelineStep> steps) implements PipelineStep {
        public Batch {
            Objects.requireNonNull(steps);
        }
    }
}
