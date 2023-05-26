/*
 * Copyright (c) 2023 - for information on the respective copyright owner
 * see the NOTICE file and/or the repository https://github.com/whichlicense/core-libs.
 *
 * SPDX-License-Identifier: Apache-2.0
 */
package com.whichlicense.metadata.sourcing.artifact.tarball;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TarballMetadataSourceResolverProviderTest {
    @Test
    void givenTarballMetadataSourceResolverWhenCallingResolverThenTarballMetadataSourceResolverConstructorReferenceShouldBeReturned() {
        assertThat(new TarballMetadataSourceResolverProvider().resolver()).isNotNull();
    }

    @Test
    void givenTarballMetadataSourceResolverWhenCallingPriorityThenZeroShouldBeReturned() {
        assertThat(new TarballMetadataSourceResolverProvider().priority()).isZero();
    }
}
