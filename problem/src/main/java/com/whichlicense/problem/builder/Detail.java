package com.whichlicense.problem.builder;

import com.whichlicense.problem.builder.internal.FullProblemBuilder;
import com.whichlicense.problem.builder.internal.PartialProblemBuilder;
import com.whichlicense.problem.builder.internal.ProblemBuilderStep;

public sealed interface Detail<S extends ProblemBuilderStep> extends ProblemBuilderStep permits FullProblemBuilder, PartialProblemBuilder {
    S detail(String detail);

    default S detail(String message, Object... args) {
        return detail(message.formatted(args));
    }
}
