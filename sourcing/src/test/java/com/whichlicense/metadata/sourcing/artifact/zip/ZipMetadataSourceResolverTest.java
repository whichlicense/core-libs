/*
 * Copyright (c) 2023 - for information on the respective copyright owner
 * see the NOTICE file and/or the repository https://github.com/whichlicense/core-libs.
 *
 * SPDX-License-Identifier: Apache-2.0
 */
package com.whichlicense.metadata.sourcing.artifact.zip;

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

    @ParameterizedTest
    @FileReferenceSource(path = "/some.zip")
    void givenZipMetadataSourceResolverWhenCallingHandlesWithZipFilePathThenTrueShouldBeReturned(Path path) {
        assertThat(RESOLVER.handles(path)).isTrue();
    }

    @ParameterizedTest
    @FileReferenceSource(path = "/some.jar")
    void givenZipMetadataSourceResolverWhenCallingHandlesWithNonZipFilePathThenFalseShouldBeReturned(Path path) {
        assertThat(RESOLVER.handles(path)).isFalse();
    }

    @ParameterizedTest
    @FileReferenceSource(path = "/example")
    void givenZipMetadataSourceResolverWhenCallingHandlesWithDirectoryPathThenFalseShouldBeReturned(Path path) {
        assertThat(RESOLVER.handles(path)).isFalse();
    }

    @Test
    void givenZipMetadataSourceResolverWhenCallingHandlesWithZipUrlThenTrueShouldBeReturned() throws MalformedURLException {
        assertThat(RESOLVER.handles(new URL("https://example.com/some.zip"))).isTrue();
    }

    @Test
    void givenZipMetadataSourceResolverWhenCallingHandlesWithNonZipUrlThenFalseShouldBeReturned() throws MalformedURLException {
        assertThat(RESOLVER.handles(new URL("https://example.com/some"))).isFalse();
        assertThat(RESOLVER.handles(new URL("https://example.com/some.jar"))).isFalse();
    }

    @Test
    void givenZipMetadataSourceResolverWhenCallingHandlesWithTopLevelZipDomainBasedUrlThenFalseShouldBeReturned() throws MalformedURLException {
        assertThat(RESOLVER.handles(new URL("https://example.zip"))).isFalse();
    }

    @ParameterizedTest
    @FileReferenceSource(path = "/some.zip")
    void givenZipMetadataSourceResolverWhenCallingHandleWithZipFilePathThenZipMetadataOriginShouldBeReturned(Path path) {
        assertThat(RESOLVER.handle(path)).extracting("origin").isEqualTo(new RawPath(path));
    }
}
