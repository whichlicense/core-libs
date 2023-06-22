/*
 * Copyright (c) 2023 - for information on the respective copyright owner
 * see the NOTICE file and/or the repository https://github.com/whichlicense/core-libs.
 *
 * SPDX-License-Identifier: Apache-2.0
 */
package com.whichlicense.metadata.sourcing.artifact.tarball;

import com.whichlicense.configuration.KeyedConfiguration;
import com.whichlicense.metadata.sourcing.ConfigurationMock;
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
    static final KeyedConfiguration CONFIG = new ConfigurationMock();

    @ParameterizedTest
    @FileReferenceSource(path = "/some.tgz")
    void givenTarballMetadataSourceResolverWhenCallingHandlesWithTarballFilePathThenTrueShouldBeReturned(Path path) {
        assertThat(RESOLVER.handles(path, CONFIG)).isTrue();
    }

    @ParameterizedTest
    @FileReferenceSource(path = "/some.zip")
    void givenTarballMetadataSourceResolverWhenCallingHandlesWithNonTarballFilePathThenFalseShouldBeReturned(Path path) {
        assertThat(RESOLVER.handles(path, CONFIG)).isFalse();
    }

    @ParameterizedTest
    @FileReferenceSource(path = "/example")
    void givenTarballMetadataSourceResolverWhenCallingHandlesWithDirectoryPathThenFalseShouldBeReturned(Path path) {
        assertThat(RESOLVER.handles(path, CONFIG)).isFalse();
    }

    @Test
    void givenTarballMetadataSourceResolverWhenCallingHandlesWithTarballUrlThenTrueShouldBeReturned() throws MalformedURLException {
        assertThat(RESOLVER.handles(new URL("https://example.com/some.tgz"), CONFIG)).isTrue();
    }

    @Test
    void givenTarballMetadataSourceResolverWhenCallingHandlesWithNonTarballUrlThenFalseShouldBeReturned() throws MalformedURLException {
        assertThat(RESOLVER.handles(new URL("https://example.com/some"), CONFIG)).isFalse();
        assertThat(RESOLVER.handles(new URL("https://example.com/some.zip"), CONFIG)).isFalse();
    }

    @ParameterizedTest
    @FileReferenceSource(path = "/some.tgz")
    void givenTarballMetadataSourceResolverWhenCallingHandleWithTarballFilePathWithTgzExtensionThenTarballMetadataOriginShouldBeReturned(Path path) {
        assertThat(RESOLVER.handle(path, CONFIG)).extracting("origin").isEqualTo(new RawPath(path));
    }

    @ParameterizedTest
    @FileReferenceSource(path = "/some.tar.gz")
    void givenTarballMetadataSourceResolverWhenCallingHandleWithTarballFilePathWithTarGzExtensionThenTarballMetadataOriginShouldBeReturned(Path path) {
        assertThat(RESOLVER.handle(path, CONFIG)).extracting("origin").isEqualTo(new RawPath(path));
    }
}
