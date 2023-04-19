package com.whichlicense.versioning.semver.labels.identifiers;

import com.whichlicense.versioning.semver.Identifier;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class TestNumericIdentifier {
    static Stream<Arguments> alphanumericTestIdentifiers() {
        return Stream.of(new NightlyReleaseIdentifier("nightly"), new AlphaReleaseIdentifier("alpha"),
                new BetaReleaseIdentifier("beta"), new CandidateReleaseIdentifier("rc"),
                new UnclassifiedIdentifier("meta-valid")).map(Arguments::of);
    }

    @Test
    void givenNumericIdentifierWithIdenticalValueWhenComparingToNumericIdentifierThenZeroShouldBeReturned() {
        var current = new NumericIdentifier(12);
        var other = new NumericIdentifier(12);

        assertThat(current).isEqualByComparingTo(current);
        assertThat(current).isEqualByComparingTo(other);
        assertThat(other).isEqualByComparingTo(other);
        assertThat(other).isEqualByComparingTo(current);
    }

    @Test
    void givenNumericIdentifierWithLesserValueWhenComparingToNumericIdentifierThenAPositiveNumberShouldBeReturned() {
        var current = new NumericIdentifier(12);
        var other = new NumericIdentifier(6);

        assertThat(current).isGreaterThan(other);
        assertThat(other).isLessThan(current);
    }

    @Test
    void givenNumericIdentifierWithGreaterValueWhenComparingToNumericIdentifierThenANegativeNumberShouldBeReturned() {
        var current = new NumericIdentifier(12);
        var other = new NumericIdentifier(21);

        assertThat(current).isLessThan(other);
        assertThat(other).isGreaterThan(current);
    }

    @ParameterizedTest
    @MethodSource("alphanumericTestIdentifiers")
    void givenAlphanumericIdentifierWhenComparingToNumericIdentifierThanANegativeNumberShouldBeReturned(AlphanumericIdentifier identifier) {
        assertThat((Identifier) new NumericIdentifier(6)).isLessThan(identifier);
        assertThat((Identifier) new NumericIdentifier(12)).isLessThan(identifier);
        assertThat((Identifier) new NumericIdentifier(21)).isLessThan(identifier);
    }
}
