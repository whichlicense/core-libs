/*
 * Copyright (c) 2023 - for information on the respective copyright owner
 * see the NOTICE file and/or the repository https://github.com/whichlicense/core-libs.
 *
 * SPDX-License-Identifier: Apache-2.0
 */
package com.whichlicense.metadata.sourcing.repository.local;

import com.whichlicense.metadata.sourcing.MetadataOrigin.RawPath;
import com.whichlicense.metadata.sourcing.MetadataSourceResolver;
import com.whichlicense.testing.fileref.FileReferenceSource;
import org.junit.jupiter.params.ParameterizedTest;

import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

class LocalMetadataRepositorySourceResolverTest {
    static final MetadataSourceResolver RESOLVER = new LocalMetadataRepositorySourceResolver(null);

    @ParameterizedTest
    @FileReferenceSource(path = "/example")
    void givenLocalMetadataRepositorySourceResolverWhenCallingHandlesWithDirectoryPathThenTrueShouldBeReturned(Path path) {
        assertThat(RESOLVER.handles(path)).isTrue();
    }

    @ParameterizedTest
    @FileReferenceSource(path = "/some.zip")
    void givenLocalMetadataRepositorySourceResolverWhenCallingHandlesWithFilePathThenFalseShouldBeReturned(Path path) {
        assertThat(RESOLVER.handles(path)).isFalse();
    }

    @ParameterizedTest
    @FileReferenceSource(path = "/example")
    void givenLocalMetadataRepositorySourceResolverWhenCallingHandleWithDirectoryPathThenLocalMetadataRepositorySourceShouldBeReturned(Path path) {
        assertThat(RESOLVER.handle(path)).extracting("origin").isEqualTo(new RawPath(path));
    }
}
