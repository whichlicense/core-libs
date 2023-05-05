/*
 * Copyright (c) 2023 - for information on the respective copyright owner
 * see the NOTICE file and/or the repository https://github.com/whichlicense/core-libs.
 *
 * SPDX-License-Identifier: Apache-2.0
 */
package com.whichlicense.problem.builder;

import com.whichlicense.problem.Problem;
import com.whichlicense.problem.builder.internal.FullProblemBuilder;
import com.whichlicense.problem.builder.internal.PartialProblemBuilder;
import com.whichlicense.problem.builder.internal.ProblemBuilderStep;

import java.util.Map;

public sealed interface Build extends ProblemBuilderStep permits FullProblemBuilder, PartialProblemBuilder {
    Build field(String field, String value);

    Build fields(Map<String, String> fields);

    Problem build();
}
