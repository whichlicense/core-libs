/*
 * Copyright (c) 2023 - for information on the respective copyright owner
 * see the NOTICE file and/or the repository https://github.com/whichlicense/core-libs.
 *
 * SPDX-License-Identifier: Apache-2.0
 */
package com.whichlicense.problem.builder;

import com.whichlicense.problem.builder.internal.FullProblemBuilder;
import com.whichlicense.problem.builder.internal.ProblemBuilderStep;

sealed public interface Title<S extends ProblemBuilderStep> extends ProblemBuilderStep permits FullProblemBuilder {
    S title(String title);
}
