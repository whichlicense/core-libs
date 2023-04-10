/*
 * Copyright (c) 2023 - for information on the respective copyright owner
 * see the NOTICE file and/or the repository https://github.com/whichlicense/core-libs.
 *
 * SPDX-License-Identifier: Apache-2.0
 */
package com.whichlicense.internal.spectra;

import org.junit.jupiter.api.Test;

import static com.whichlicense.internal.spectra.LocalIdentitySpectra.generate;
import static com.whichlicense.metadata.identity.Identity.context;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class LocalIdentitySpectraTest {
    @Test
    void givenLocalIdentitySpectraClassWhenCallingConstructorThenThrowAnUnsupportedOperationException() {
        assertThatThrownBy(LocalIdentitySpectra::new)
                .isExactlyInstanceOf(UnsupportedOperationException.class)
                .hasMessage("Static factory class LocalIdentitySpectra should not be instantiated!");
    }

    @Test
    void givenPotentiallyPreInitializedLocalIdentitySpectraCounterWhenCallingGenerateThenVerifyContextIncrement() {
        LocalIdentitySpectra.COUNTER.reset();
        assertThat(context(generate())).isZero();
        assertThat(context(generate())).isOne();
        assertThat(context(generate())).isEqualTo(2);
        LocalIdentitySpectra.COUNTER.reset();
    }

    @Test
    void givenWrapThresholdPreInitializedLocalIdentitySpectraCounterWhenCallingGenerateThenVerifyContextAndCounterWrap() {
        LocalIdentitySpectra.COUNTER.reset();
        LocalIdentitySpectra.COUNTER.add(16_383L);
        assertThat(context(generate())).isEqualTo(16_383L);
        assertThat(LocalIdentitySpectra.COUNTER.longValue()).isZero();
        assertThat(context(generate())).isZero();
        LocalIdentitySpectra.COUNTER.reset();
    }
}
