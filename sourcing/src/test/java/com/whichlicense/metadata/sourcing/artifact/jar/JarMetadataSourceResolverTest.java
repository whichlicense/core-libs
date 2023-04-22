/*
 * Copyright (c) 2023 - for information on the respective copyright owner
 * see the NOTICE file and/or the repository https://github.com/whichlicense/core-libs.
 *
 * SPDX-License-Identifier: Apache-2.0
 */
package com.whichlicense.metadata.sourcing.artifact.jar;

import com.whichlicense.metadata.sourcing.FileReferenceSource;
import com.whichlicense.metadata.sourcing.MetadataOrigin.RawPath;
import com.whichlicense.metadata.sourcing.MetadataSourceResolver;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

class JarMetadataSourceResolverTest {
    static final MetadataSourceResolver RESOLVER = new JarMetadataSourceResolver(null);

    @ParameterizedTest
    @FileReferenceSource(path = "/some.jar")
    void givenJarMetadataSourceResolverWhenCallingHandlesWithJarFilePathThenTrueShouldBeReturned(Path path) {
        assertThat(RESOLVER.handles(path)).isTrue();
    }

    @ParameterizedTest
    @FileReferenceSource(path = "/some.zip")
    void givenJarMetadataSourceResolverWhenCallingHandlesWithNonJarFilePathThenFalseShouldBeReturned(Path path) {
        assertThat(RESOLVER.handles(path)).isFalse();
    }

    @ParameterizedTest
    @FileReferenceSource(path = "/example")
    void givenJarMetadataSourceResolverWhenCallingHandlesWithDirectoryPathThenFalseShouldBeReturned(Path path) {
        assertThat(RESOLVER.handles(path)).isFalse();
    }

    @Test
    void givenJarMetadataSourceResolverWhenCallingHandlesWithJarUrlThenTrueShouldBeReturned() throws MalformedURLException {
        assertThat(RESOLVER.handles(new URL("https://example.com/some.jar"))).isTrue();
    }

    @Test
    void givenJarMetadataSourceResolverWhenCallingHandlesWithNonJarUrlThenFalseShouldBeReturned() throws MalformedURLException {
        assertThat(RESOLVER.handles(new URL("https://example.com/some"))).isFalse();
        assertThat(RESOLVER.handles(new URL("https://example.com/some.zip"))).isFalse();
    }

    @ParameterizedTest
    @FileReferenceSource(path = "/some.jar")
    void givenJarMetadataSourceResolverWhenCallingHandleWithJarFilePathThenJarMetadataSourceShouldBeReturned(Path path) {
        assertThat(RESOLVER.handle(path)).extracting("origin").isEqualTo(new RawPath(path));
    }
}
