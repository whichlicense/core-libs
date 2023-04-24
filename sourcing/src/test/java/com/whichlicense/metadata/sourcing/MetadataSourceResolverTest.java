/*
 * Copyright (c) 2023 - for information on the respective copyright owner
 * see the NOTICE file and/or the repository https://github.com/whichlicense/core-libs.
 *
 * SPDX-License-Identifier: Apache-2.0
 */
package com.whichlicense.metadata.sourcing;

import com.whichlicense.metadata.sourcing.artifact.zip.ZipMetadataSource;
import com.whichlicense.metadata.sourcing.repository.local.LocalMetadataRepositorySource;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MetadataSourceResolverTest {
    @Test
    void givenMetadataSourceResolverWhenCallingHandlesWithPathThenFalseShouldBeReturned(@Mock MetadataSourceResolver resolver) {
        when(resolver.handles((Path) null)).thenReturn(false);
        assertThat(resolver.handles((Path) null)).isFalse();
    }

    @Test
    void givenMetadataSourceResolverWhenCallingHandlesWithUrlThenFalseShouldBeReturned(@Mock MetadataSourceResolver resolver) {
        when(resolver.handles((URL) null)).thenReturn(false);
        assertThat(resolver.handles((URL) null)).isFalse();
    }

    @Test
    void givenMetadataSourceResolverWhenCallingHandleWithPathThenThrowUnsupportedOperationException(@Mock MetadataSourceResolver resolver) {
        when(resolver.handle((Path) null)).thenCallRealMethod();
        assertThatThrownBy(() -> resolver.handle((Path) null))
                .isExactlyInstanceOf(UnsupportedOperationException.class)
                .hasMessageStartingWith("Unsupported null for com.whichlicense."
                        + "metadata.sourcing.MetadataSourceResolver$MockitoMock");
    }

    @Test
    void givenMetadataSourceResolverWhenCallingHandleWithUrlThenThrowUnsupportedOperationException(@Mock MetadataSourceResolver resolver) {
        when(resolver.handle((URL) null)).thenCallRealMethod();
        assertThatThrownBy(() -> resolver.handle((URL) null))
                .isExactlyInstanceOf(UnsupportedOperationException.class)
                .hasMessageStartingWith("Unsupported null for com.whichlicense."
                        + "metadata.sourcing.MetadataSourceResolver$MockitoMock");
    }

    @Test
    void givenMetadataSourceResolverThatDoesNotHandlePathWhenCallingResolveWithPathThenOptionalEmptyShouldBeReturned(@Mock MetadataSourceResolver resolver) {
        var example = Paths.get("example");

        when(resolver.handles(example)).thenReturn(false);
        when(resolver.resolve(example)).thenCallRealMethod();

        assertThat(resolver.next()).isNull();
        assertThat(resolver.resolve(example)).isEmpty();
    }

    @Test
    void givenMetadataSourceResolverThatDoesNotHandleUrlWhenCallingResolveWithUrlThenOptionalEmptyShouldBeReturned(@Mock MetadataSourceResolver resolver) throws MalformedURLException {
        var example = new URL("https://example.com");

        when(resolver.handles(example)).thenReturn(false);
        when(resolver.resolve(example)).thenCallRealMethod();

        assertThat(resolver.next()).isNull();
        assertThat(resolver.resolve(example)).isEmpty();
    }

    @Test
    void givenMetadataSourceResolverThatDoesNotHandleStringWhenCallingResolveWithStringThenOptionalEmptyShouldBeReturned(@Mock MetadataSourceResolver resolver) throws MalformedURLException {
        var example = "example";

        //when(resolver.handles(Paths.get(example))).thenReturn(false);
        when(resolver.resolve(example)).thenCallRealMethod();

        assertThat(resolver.next()).isNull();
        assertThat(resolver.resolve(example)).isEmpty();
    }

    @Test
    void givenMetadataSourceResolverWithNextResolverThatDoesNotHandlePathWhenCallingResolveWithPathThenSkipToNextResolver(@Mock MetadataSourceResolver first, @Mock MetadataSourceResolver second) {
        var example = Paths.get("example");
        var source = new LocalMetadataRepositorySource(example);

        when(first.next()).thenReturn(second);
        when(first.handles(example)).thenReturn(false);
        when(first.resolve(example)).thenCallRealMethod();
        when(second.handles(example)).thenReturn(true);
        when(second.handle(example)).thenReturn(source);
        when(second.resolve(example)).thenCallRealMethod();

        assertThat(first.next()).isEqualTo(second);
        assertThat(first.resolve(example)).contains(source);
        verify(second, times(1)).handles(example);
    }

    @Test
    void givenMetadataSourceResolverWithNextResolverThatDoesNotHandleUrlWhenCallingResolveWithUrlThenSkipToNextResolver(@Mock MetadataSourceResolver first, @Mock MetadataSourceResolver second) throws MalformedURLException {
        var example = new URL("https://example.com");
        var source = Paths.get(".");
        var origin = new MetadataOrigin.RawURL(example);

        when(first.next()).thenReturn(second);
        when(first.handles(example)).thenReturn(false);
        when(first.resolve(example)).thenCallRealMethod();
        when(second.handles(example)).thenReturn(true);
        when(second.handle(example)).thenReturn(new ZipMetadataSource(source, origin));
        when(second.resolve(example)).thenCallRealMethod();

        assertThat(first.next()).isEqualTo(second);
        assertThat(first.resolve(example)).get()
                .extracting("origin").isEqualTo(origin);
        verify(second, times(1)).handles(example);
    }
}
