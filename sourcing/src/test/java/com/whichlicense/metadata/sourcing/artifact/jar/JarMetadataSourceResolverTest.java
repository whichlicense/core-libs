/*
 * Copyright (c) 2023 - for information on the respective copyright owner
 * see the NOTICE file and/or the repository https://github.com/whichlicense/core-libs.
 *
 * SPDX-License-Identifier: Apache-2.0
 */
package com.whichlicense.metadata.sourcing.artifact.jar;

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

class JarMetadataSourceResolverTest {
    static final MetadataSourceResolver RESOLVER = new JarMetadataSourceResolver(null);
    static final KeyedConfiguration CONFIG = new ConfigurationMock();

    @ParameterizedTest
    @FileReferenceSource(path = "/some.jar")
    void givenJarMetadataSourceResolverWhenCallingHandlesWithJarFilePathThenTrueShouldBeReturned(Path path) {
        assertThat(RESOLVER.handles(path, CONFIG)).isTrue();
    }

    @ParameterizedTest
    @FileReferenceSource(path = "/some.zip")
    void givenJarMetadataSourceResolverWhenCallingHandlesWithNonJarFilePathThenFalseShouldBeReturned(Path path) {
        assertThat(RESOLVER.handles(path, CONFIG)).isFalse();
    }

    @ParameterizedTest
    @FileReferenceSource(path = "/example")
    void givenJarMetadataSourceResolverWhenCallingHandlesWithDirectoryPathThenFalseShouldBeReturned(Path path) {
        assertThat(RESOLVER.handles(path, CONFIG)).isFalse();
    }

    @Test
    void givenJarMetadataSourceResolverWhenCallingHandlesWithJarUrlThenTrueShouldBeReturned() throws MalformedURLException {
        assertThat(RESOLVER.handles(new URL("https://example.com/some.jar"), CONFIG)).isTrue();
    }

    @Test
    void givenJarMetadataSourceResolverWhenCallingHandlesWithNonJarUrlThenFalseShouldBeReturned() throws MalformedURLException {
        assertThat(RESOLVER.handles(new URL("https://example.com/some"), CONFIG)).isFalse();
        assertThat(RESOLVER.handles(new URL("https://example.com/some.zip"), CONFIG)).isFalse();
    }

    @ParameterizedTest
    @FileReferenceSource(path = "/some.jar")
    void givenJarMetadataSourceResolverWhenCallingHandleWithJarFilePathThenJarMetadataOriginShouldBeReturned(Path path) {
        assertThat(RESOLVER.handle(path, CONFIG)).extracting("origin").isEqualTo(new RawPath(path));
    }
}
