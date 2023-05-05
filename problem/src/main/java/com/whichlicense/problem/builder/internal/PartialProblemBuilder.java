/*
 * Copyright (c) 2023 - for information on the respective copyright owner
 * see the NOTICE file and/or the repository https://github.com/whichlicense/core-libs.
 *
 * SPDX-License-Identifier: Apache-2.0
 */
package com.whichlicense.problem.builder.internal;

import com.whichlicense.problem.Problem;
import com.whichlicense.problem.StaticProblemMembers;
import com.whichlicense.problem.builder.Build;
import com.whichlicense.problem.builder.Detail;
import com.whichlicense.problem.builder.Instance;

import java.net.URI;
import java.util.Map;

public final class PartialProblemBuilder implements Detail<Instance<Build>>, Instance<Build>, Build {
    private final FullProblemBuilder builder;

    public PartialProblemBuilder(StaticProblemMembers members) {
        this.builder = new FullProblemBuilder();
        this.builder.type(members.type());
        this.builder.title(members.title());
        this.builder.status(members.status());
    }

    @Override
    public Build field(String field, String value) {
        this.builder.field(field, value);
        return this;
    }

    @Override
    public Build fields(Map<String, String> fields) {
        this.builder.fields(fields);
        return this;
    }

    @Override
    public Problem build() {
        return this.builder.build();
    }

    @Override
    public Instance<Build> detail(String detail) {
        this.builder.detail(detail);
        return this;
    }

    @Override
    public Build instance(URI instance) {
        this.builder.instance(instance);
        return this;
    }
}
