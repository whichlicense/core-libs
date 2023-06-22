/*
 * Copyright (c) 2023 - for information on the respective copyright owner
 * see the NOTICE file and/or the repository https://github.com/whichlicense/core-libs.
 *
 * SPDX-License-Identifier: Apache-2.0
 */
package com.whichlicense.metadata.identification.license.internal;

public final class HashingAlgorithm {
    public static final String FUZZY = "fuzzy";
    public static final String GAOYA = "gaoya";
    public static final String FUZZY_PIPELINE = "fuzzy-pipeline";
    public static final String GAOYA_PIPELINE = "gaoya-pipeline";

    public static final class FuzzyAlgorithmArguments {
        public static final String EXIT_ON_EXACT_MATCH = "exit_on_exact_match";
    }

    public static final class GaoyaAlgorithmArguments {
        public static final String BAND_COUNT = "band_count";
        public static final String BAND_WIDTH = "band_width";
        public static final String SHINGLE_SIZE = "shingle_size";
    }
}
