/*
 * Copyright (c) 2023 - for information on the respective copyright owner
 * see the NOTICE file and/or the repository https://github.com/whichlicense/core-libs.
 *
 * SPDX-License-Identifier: Apache-2.0
 */
package com.whichlicense.metadata.sourcing.artifact.zip;

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

class ZipMetadataSourceResolverTest {
    static final MetadataSourceResolver RESOLVER = new ZipMetadataSourceResolver(null);
    static final KeyedConfiguration CONFIG = new ConfigurationMock();

    @ParameterizedTest
    @FileReferenceSource(path = "/some.zip")
    void givenZipMetadataSourceResolverWhenCallingHandlesWithZipFilePathThenTrueShouldBeReturned(Path path) {
        assertThat(RESOLVER.handles(path, CONFIG)).isTrue();
    }

    @ParameterizedTest
    @FileReferenceSource(path = "/some.jar")
    void givenZipMetadataSourceResolverWhenCallingHandlesWithNonZipFilePathThenFalseShouldBeReturned(Path path) {
        assertThat(RESOLVER.handles(path, CONFIG)).isFalse();
    }

    @ParameterizedTest
    @FileReferenceSource(path = "/example")
    void givenZipMetadataSourceResolverWhenCallingHandlesWithDirectoryPathThenFalseShouldBeReturned(Path path) {
        assertThat(RESOLVER.handles(path, CONFIG)).isFalse();
    }

    @Test
    void givenZipMetadataSourceResolverWhenCallingHandlesWithZipUrlThenTrueShouldBeReturned() throws MalformedURLException {
        assertThat(RESOLVER.handles(new URL("https://example.com/some.zip"), CONFIG)).isTrue();
        assertThat(RESOLVER.handles(new URL("https://github.com/whichlicense/frontend/archive/refs/heads/main.zip"), CONFIG)).isTrue();
    }

    @Test
    void givenZipMetadataSourceResolverWhenCallingHandlesWithNonZipUrlThenFalseShouldBeReturned() throws MalformedURLException {
        assertThat(RESOLVER.handles(new URL("https://example.com/some"), CONFIG)).isFalse();
        assertThat(RESOLVER.handles(new URL("https://example.com/some.jar"), CONFIG)).isFalse();
    }

    @Test
    void givenZipMetadataSourceResolverWhenCallingHandlesWithTopLevelZipDomainBasedUrlThenFalseShouldBeReturned() throws MalformedURLException {
        assertThat(RESOLVER.handles(new URL("https://example.zip"), CONFIG)).isFalse();
    }

    @ParameterizedTest
    @FileReferenceSource(path = "/some.zip")
    void givenZipMetadataSourceResolverWhenCallingHandleWithZipFilePathThenZipMetadataOriginShouldBeReturned(Path path) {
        assertThat(RESOLVER.handle(path, CONFIG)).extracting("origin").isEqualTo(new RawPath(path));
    }
}
