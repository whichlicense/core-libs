/*
 * Copyright (c) 2023 - for information on the respective copyright owner
 * see the NOTICE file and/or the repository https://github.com/whichlicense/core-libs.
 *
 * SPDX-License-Identifier: Apache-2.0
 */
package com.whichlicense.problem.builder.internal;

import com.whichlicense.problem.Problem;
import com.whichlicense.problem.StaticProblemMembers;

import java.net.URI;

import static com.whichlicense.problem.builder.internal.Main.ExampleProblems.MALFORMED_URL;

public class Main {
    enum ExampleProblems implements StaticProblemMembers {
        MALFORMED_URL(404, "Invalid url", URI.create("https://api.whichlicense.app/problem/invalid-url"));

        private final int status;
        private final String title;
        private final URI type;

        ExampleProblems(int status, String title, URI type) {
            this.status = status;
            this.title = title;
            this.type = type;
        }

        @Override
        public URI type() {
            return type;
        }

        @Override
        public String title() {
            return title;
        }

        @Override
        public int status() {
            return status;
        }
    }

    public static void main(String[] args) {
        var problem = Problem.builder().type(URI.create("https://api.whichlicense.app"))
                .title("problem").detail("more problem text: %s", "test")
                .status(100).instance(URI.create("https://whichlicense.app/discover/37843874"))
                .build();
        System.out.println(problem);
        var problem2 = Problem.builder(MALFORMED_URL).detail("more problem text: %s", "test")
                .instance(URI.create("https://whichlicense.app/discover/37843874"))
                .build();
        System.out.println(problem2);
    }
}
