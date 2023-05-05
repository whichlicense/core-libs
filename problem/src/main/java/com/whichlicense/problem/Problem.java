/*
 * Copyright (c) 2023 - for information on the respective copyright owner
 * see the NOTICE file and/or the repository https://github.com/whichlicense/core-libs.
 *
 * SPDX-License-Identifier: Apache-2.0
 */
package com.whichlicense.problem;

import com.whichlicense.problem.builder.*;
import com.whichlicense.problem.builder.internal.FullProblemBuilder;
import com.whichlicense.problem.builder.internal.PartialProblemBuilder;

import java.net.URI;
import java.util.Map;

//Implementation for RFC 7807
public record Problem(URI type, String title, String detail, int status, URI instance, Map<String, String> otherFields) implements StaticProblemMembers {
    public static Type<Title<Detail<Status<Instance<Build>>>>> builder() {
        return new FullProblemBuilder();
    }
    public static Detail<Instance<Build>> builder(StaticProblemMembers members) {
        return new PartialProblemBuilder(members);
    }
}
