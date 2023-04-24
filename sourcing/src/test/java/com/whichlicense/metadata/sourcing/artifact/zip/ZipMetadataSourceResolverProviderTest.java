/*
 * Copyright (c) 2023 - for information on the respective copyright owner
 * see the NOTICE file and/or the repository https://github.com/whichlicense/core-libs.
 *
 * SPDX-License-Identifier: Apache-2.0
 */
package com.whichlicense.metadata.sourcing.artifact.zip;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ZipMetadataSourceResolverProviderTest {
    @Test
    void givenZipMetadataSourceResolverWhenCallingResolverThenZipMetadataSourceResolverConstructorReferenceShouldBeReturned() {
        assertThat(new ZipMetadataSourceResolverProvider().resolver()).isNotNull();
    }

    @Test
    void givenZipMetadataSourceResolverWhenCallingPriorityThenZeroShouldBeReturned() {
        assertThat(new ZipMetadataSourceResolverProvider().priority()).isZero();
    }
}
