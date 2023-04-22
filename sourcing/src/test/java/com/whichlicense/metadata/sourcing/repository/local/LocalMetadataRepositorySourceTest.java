/*
 * Copyright (c) 2023 - for information on the respective copyright owner
 * see the NOTICE file and/or the repository https://github.com/whichlicense/core-libs.
 *
 * SPDX-License-Identifier: Apache-2.0
 */
package com.whichlicense.metadata.sourcing.repository.local;

import com.whichlicense.testing.nullable.NullSubstituteSource;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class LocalMetadataRepositorySourceTest {
    static final Path PATH = Paths.get(".");
    static final Arguments ARGS = Arguments.of(PATH);

    @ParameterizedTest
    @NullSubstituteSource("ARGS")
    void givenNullableArgumentsWhenCallingTheConstructorThenThrowNullPointerException(Path path) {
        assertThatThrownBy(() -> new LocalMetadataRepositorySource(path))
                .isExactlyInstanceOf(NullPointerException.class);
    }
}
