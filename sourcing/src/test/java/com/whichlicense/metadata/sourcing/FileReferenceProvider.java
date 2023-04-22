/*
 * Copyright (c) 2023 - for information on the respective copyright owner
 * see the NOTICE file and/or the repository https://github.com/whichlicense/testing-libs.
 *
 * SPDX-License-Identifier: Apache-2.0
 */
package com.whichlicense.metadata.sourcing;

import org.junit.jupiter.api.Named;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.support.AnnotationConsumer;

import java.nio.file.Paths;
import java.util.Objects;
import java.util.stream.Stream;

public class FileReferenceProvider implements ArgumentsProvider, AnnotationConsumer<FileReferenceSource> {
    private String path = "";

    @Override
    public void accept(FileReferenceSource source) {
        path = source.path();
    }

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) throws Exception {
        var reference = Objects.requireNonNull(getClass().getResource(path)).toURI();
        return Stream.of(Arguments.of(Named.of(path, Paths.get(reference))));
    }
}
