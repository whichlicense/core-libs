/*
 * Copyright (c) 2023 - for information on the respective copyright owner
 * see the NOTICE file and/or the repository https://github.com/whichlicense/core-libs.
 *
 * SPDX-License-Identifier: Apache-2.0
 */
package com.whichlicense.problem.builder.internal;

import com.whichlicense.problem.Problem;
import com.whichlicense.problem.builder.*;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import static java.util.Objects.requireNonNull;

public final class FullProblemBuilder implements Type<Title<Detail<Status<Instance<Build>>>>>, Title<Detail<Status<Instance<Build>>>>, Detail<Status<Instance<Build>>>, Status<Instance<Build>>, Instance<Build>, Build {
    private final Map<String, String> otherFields = new HashMap<>();
    private URI type;
    private String title;
    private String detail;
    private int status;
    private URI instance;

    @Override
    public Build field(String field, String value) {
        otherFields.put(requireNonNull(field), requireNonNull(value));
        return this;
    }

    @Override
    public Build fields(Map<String, String> fields) {
        otherFields.putAll(requireNonNull(fields));
        return this;
    }

    @Override
    public Problem build() {
        return new Problem(type, title, detail, status, instance, otherFields);
    }

    @Override
    public Status<Instance<Build>> detail(String detail) {
        this.detail = requireNonNull(detail);
        return this;
    }

    @Override
    public Build instance(URI instance) {
        this.instance = requireNonNull(instance);
        return this;
    }

    @Override
    public Instance<Build> status(int status) {
        this.status = status;
        return this;
    }

    @Override
    public Detail<Status<Instance<Build>>> title(String title) {
        this.title = requireNonNull(title);
        return this;
    }

    @Override
    public Title<Detail<Status<Instance<Build>>>> type(URI type) {
        this.type = requireNonNull(type);
        return this;
    }
}
