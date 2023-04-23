/*
 * Copyright (c) 2023 - for information on the respective copyright owner
 * see the NOTICE file and/or the repository https://github.com/whichlicense/core-libs.
 *
 * SPDX-License-Identifier: Apache-2.0
 */
package com.whichlicense.metadata.sourcing.repository.local;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class LocalMetadataRepositorySourceResolverProviderTest {
    @Test
    void givenLocalMetadataRepositorySourceResolverWhenCallingResolverThenLocalMetadataRepositorySourceResolverConstructorReferenceShouldBeReturned() {
        assertThat(new LocalMetadataRepositorySourceResolverProvider().resolver()).isNotNull();
    }

    @Test
    void givenLocalMetadataRepositorySourceResolverWhenCallingPriorityThenZeroShouldBeReturned() {
        assertThat(new LocalMetadataRepositorySourceResolverProvider().priority()).isEqualTo(Integer.MIN_VALUE);
    }
}
