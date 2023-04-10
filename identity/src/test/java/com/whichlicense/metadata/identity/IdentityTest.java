/*
 * Copyright (c) 2023 - for information on the respective copyright owner
 * see the NOTICE file and/or the repository https://github.com/whichlicense/core-libs.
 *
 * SPDX-License-Identifier: Apache-2.0
 */
package com.whichlicense.metadata.identity;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static com.whichlicense.metadata.identity.Identity.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class IdentityTest {
    @Test
    void givenIdentityClassWhenCallingConstructorThenThrowAnUnsupportedOperationException() {
        assertThatThrownBy(Identity::new)
                .isExactlyInstanceOf(UnsupportedOperationException.class)
                .hasMessage("Static factory class Identity should not be instantiated!");
    }

    @ParameterizedTest
    @ValueSource(longs = {0, 1, 123, 123456, 654, 654321})
    void givenIdentitySpectraWithCustomEpochWhenCallingMillisecondsThenTheEncodedMillisecondsComponentWithCustomEpochOffsetShouldBeReturned(long milliseconds) {
        var spectra = generate(milliseconds, (byte) 3, (byte) 1, 14);
        assertThat(milliseconds(spectra)).isEqualTo(milliseconds);
    }

    @ParameterizedTest
    @ValueSource(longs = {Long.MIN_VALUE, -3, -2, -1, (1L << 41), (1L << 41) + 1, (1L << 41) + 2, Long.MAX_VALUE})
    void givenInvalidIdentitySpectraMillisecondsComponentWhenCallingMillisecondsThenTheReturnedMillisecondsComponentShouldNotMatchTheInputComponent(long milliseconds) {
        var spectra = generate(milliseconds, (byte) 3, (byte) 1, 14);
        assertThat(milliseconds(spectra)).isNotEqualTo(milliseconds);
    }

    @ParameterizedTest
    @ValueSource(longs = {0, 1, 123, 123456, 654, 654321})
    void givenIdentitySpectraWithCustomEpochWhenCallingTimeThenThenTheEpochAdjustedMillisecondsComponentShouldBeReturned(long milliseconds) {
        var spectra = generate(milliseconds, (byte) 1, (byte) 3, 41);
        assertThat(time(spectra)).isEqualTo(milliseconds + EPOCH);
    }

    @ParameterizedTest
    @ValueSource(longs = {Long.MIN_VALUE, -3, -2, -1, (1L << 41), (1L << 41) + 1, (1L << 41) + 2, Long.MAX_VALUE})
    void givenInvalidIdentitySpectraMillisecondsComponentWhenCallingTimeThenTheReturnedEpochAdjustedMillisecondsComponentShouldNotMatchTheInputComponent(long milliseconds) {
        var spectra = generate(milliseconds, (byte) 3, (byte) 1, 14);
        assertThat(milliseconds(spectra)).isNotEqualTo(milliseconds);
    }

    @Test
    void givenOlderIdentitySpectraMillisecondsComponentThanRhsWhenCallingCompareThenANegativeNumberShouldBeReturned() {
        var lhs = generate(123456, (byte) 0, (byte) 0, 0);
        var rhs = generate(654321, (byte) 0, (byte) 0, 0);
        assertThat(compare(lhs, rhs)).isNegative();
    }

    @Test
    void givenSameIdentitySpectraMillisecondComponentsWhenCallingCompareThenZeroShouldBeReturned() {
        var first = generate(654321, (byte) 0, (byte) 0, 0);
        var second = generate(123456, (byte) 0, (byte) 0, 0);
        assertThat(compare(first, first)).isZero();
        assertThat(compare(second, second)).isZero();
    }

    @Test
    void givenNewerIdentitySpectraMillisecondsComponentThanRhsWhenCallingCompareThenAPositiveNumberShouldBeReturned() {
        var lhs = generate(654321, (byte) 0, (byte) 0, 0);
        var rhs = generate(123456, (byte) 0, (byte) 0, 0);
        assertThat(compare(lhs, rhs)).isPositive();
    }

    @ParameterizedTest
    @ValueSource(bytes = {0, 1, 2, 3, 124, 125, 126, 127})
    void givenIdentitySpectraWithValidZoneWhenCallingZoneThenTheZoneComponentShouldBeReturned(byte zone) {
        var spectra = generate(654321, zone, (byte) 3, 14);
        assertThat(zone(spectra)).isEqualTo(zone);
    }

    @ParameterizedTest
    @ValueSource(bytes = {Byte.MIN_VALUE, -3, -2, -1})
    void givenIdentitySpectraWithInvalidZoneWhenCallingZoneThenTheReturnedZoneShouldNotMatchTheInputComponent(byte zone) {
        var spectra = generate(654321, zone, (byte) 3, 14);
        assertThat(zone(spectra)).isNotEqualTo(zone);
    }

    @ParameterizedTest
    @ValueSource(bytes = {0, 1, 2, 3})
    void givenIdentitySpectraWithValidReplicaWhenCallingReplicaThenTheReplicaComponentShouldBeReturned(byte replica) {
        var spectra = generate(123456, (byte) 3, replica, 14);
        assertThat(replica(spectra)).isEqualTo(replica);
    }

    @ParameterizedTest
    @ValueSource(bytes = {Byte.MIN_VALUE, -3, -2, -1, 4, 5, 6, Byte.MAX_VALUE})
    void givenIdentitySpectraWithInvalidReplicaWhenCallingReplicaThenTheReturnedReplicaComponentShouldNotMatchTheInputComponent(byte replica) {
        var spectra = generate(123456, (byte) 3, replica, 14);
        assertThat(replica(spectra)).isNotEqualTo(replica);
    }

    @ParameterizedTest
    @ValueSource(longs = {0, 1, 2, 4, 8, 16, 32, 64, 128, 256, 512, 1024, 4096, 8192, WRAP - 1})
    void givenIdentitySpectraWithValidContextWhenCallingContextThenTheContextComponentShouldBeReturned(long context) {
        var spectra = generate(654321, (byte) 1, (byte) 3, context);
        assertThat(context(spectra)).isEqualTo(context);
    }

    @ParameterizedTest
    @ValueSource(longs = {Long.MIN_VALUE, -8192 -4096, -1024, -512, -256, -128, -64, -32, -16, -8, -4, -2, -1, WRAP, WRAP + 1, WRAP + 2, WRAP + 3, Long.MAX_VALUE})
    void givenIdentitySpectraWithInvalidContextWhenCallingContextThenTheReturnedContextComponentShouldNotMatchTheInputComponent(long context) {
        var spectra = generate(654321, (byte) 1, (byte) 3, context);
        assertThat(context(spectra)).isNotEqualTo(context);
    }

    @ParameterizedTest
    @CsvSource(value = {"123456, 1, 3, 14", "123456, 1, 3, 8192", "123456, 3, 1, 41", "123456, 3, 1, 1024",
            "654321, 1, 3, 14", "654321, 1, 3, 4096", "654321, 3, 1, 41", "654321, 3, 1, 512"})
    void givenIdentitySpectraWithValidComponentsWhenCallingToHexThenTheHexOfTheHexEncodedSpectraShouldBeReturned(long milliseconds, byte zone, byte replica, long context) {
        var spectra = generate(milliseconds, zone, replica, context);
        assertThat(toHex(spectra)).isEqualTo(Long.toHexString(spectra));
    }

    @ParameterizedTest
    @ValueSource(longs = {1035624103950L, 1035624112128L, 1035624202281L, 1035624203264L,
            5488842489870L, 5488842493952L, 5488842588201L, 5488842588672L})
    void givenValidIdentitySpectraHexWhenCallingFromHexThenTheOriginalLongEncodedSpectraShouldBeReturned(long spectra) {
        assertThat(fromHex(Long.toHexString(spectra))).isEqualTo(spectra);
    }

    @ParameterizedTest
    @ValueSource(longs = {0, 1, 2, 4, 8, 16, 32, 64, 128, 256, 512, 1024, 4096, 8192, WRAP - 1})
    void givenInBoundsIdentitySpectraContextWhenCallingWrapAndGenerateThenTheContextComponentShouldBeEqualToTheInputContext(long context) {
        assertThat(context(wrapAndGenerate(context))).isEqualTo(context);
    }

    @ParameterizedTest
    @ValueSource(longs = {WRAP, WRAP + 1, WRAP + 2, WRAP + 3, Long.MAX_VALUE})
    void givenOutOfBoundsIdentitySpectraContextWhenCallingWrapAndGenerateThenTheContextComponentShouldBeEqualToTheWrappedValueOfTheInputContext(long context) {
        assertThat(context(wrapAndGenerate(context))).isEqualTo(context % WRAP);
    }
}
