/*
 * Copyright (c) 2023 - for information on the respective copyright owner
 * see the NOTICE file and/or the repository https://github.com/whichlicense/core-libs.
 *
 * SPDX-License-Identifier: Apache-2.0
 */
package com.whichlicense.metadata.sourcing.artifact.tarball;

import com.whichlicense.metadata.sourcing.MetadataOrigin.RawPath;
import com.whichlicense.metadata.sourcing.MetadataSourceResolver;
import com.whichlicense.testing.fileref.FileReferenceSource;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

class TarballMetadataSourceResolverTest {
    static final MetadataSourceResolver RESOLVER = new TarballMetadataSourceResolver(null);

    @ParameterizedTest
    @FileReferenceSource(path = "/some.tgz")
    void givenTarballMetadataSourceResolverWhenCallingHandlesWithTarballFilePathThenTrueShouldBeReturned(Path path) {
        assertThat(RESOLVER.handles(path)).isTrue();
    }

    @ParameterizedTest
    @FileReferenceSource(path = "/some.zip")
    void givenTarballMetadataSourceResolverWhenCallingHandlesWithNonTarballFilePathThenFalseShouldBeReturned(Path path) {
        assertThat(RESOLVER.handles(path)).isFalse();
    }

    @ParameterizedTest
    @FileReferenceSource(path = "/example")
    void givenTarballMetadataSourceResolverWhenCallingHandlesWithDirectoryPathThenFalseShouldBeReturned(Path path) {
        assertThat(RESOLVER.handles(path)).isFalse();
    }

    @Test
    void givenTarballMetadataSourceResolverWhenCallingHandlesWithTarballUrlThenTrueShouldBeReturned() throws MalformedURLException {
        assertThat(RESOLVER.handles(new URL("https://example.com/some.tgz"))).isTrue();
    }

    @Test
    void givenTarballMetadataSourceResolverWhenCallingHandlesWithNonTarballUrlThenFalseShouldBeReturned() throws MalformedURLException {
        assertThat(RESOLVER.handles(new URL("https://example.com/some"))).isFalse();
        assertThat(RESOLVER.handles(new URL("https://example.com/some.zip"))).isFalse();
    }

    @ParameterizedTest
    @FileReferenceSource(path = "/some.tgz")
    void givenTarballMetadataSourceResolverWhenCallingHandleWithTarballFilePathWithTgzExtensionThenTarballMetadataOriginShouldBeReturned(Path path) {
        assertThat(RESOLVER.handle(path)).extracting("origin").isEqualTo(new RawPath(path));
    }

    @ParameterizedTest
    @FileReferenceSource(path = "/some.tar.gz")
    void givenTarballMetadataSourceResolverWhenCallingHandleWithTarballFilePathWithTarGzExtensionThenTarballMetadataOriginShouldBeReturned(Path path) {
        assertThat(RESOLVER.handle(path)).extracting("origin").isEqualTo(new RawPath(path));
    }
}
