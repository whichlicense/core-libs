package com.whichlicense.versioning.semver.labels.identifiers;

import com.whichlicense.versioning.semver.Identifier;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TestAlphaReleaseIdentifier {
    @Test
    void givenNumericIdentifierWhenComparingToAlphaReleaseIdentifierThenANegativeNumberShouldBeReturned() {
        var current = new AlphaReleaseIdentifier("alpha");
        var other = new NumericIdentifier(21);

        assertThat((Identifier) current).isGreaterThan(other);
        assertThat((Identifier) other).isLessThan(current);
    }

    @Test
    void givenNightlyReleaseIdentifierWhenComparingToAlphaReleaseIdentifierThenAPositiveNumberShouldBeReturned() {
        var current = new AlphaReleaseIdentifier("alpha");
        var other = new NightlyReleaseIdentifier("nightly");

        assertThat((Identifier) current).isGreaterThan(other);
        assertThat((Identifier) other).isLessThan(current);
    }

    @Test
    void givenAlphaReleaseIdentifierWithIdenticalValueWhenComparingToAlphaReleaseIdentifierThenZeroShouldBeReturned() {
        var current = new AlphaReleaseIdentifier("alpha");
        var other = new AlphaReleaseIdentifier("alpha");

        assertThat(current).isEqualByComparingTo(current);
        assertThat(current).isEqualByComparingTo(other);
        assertThat(other).isEqualByComparingTo(other);
        assertThat(other).isEqualByComparingTo(current);
    }

    @Test
    void givenAlphaReleaseIdentifierWithALexicographicallyHigherValueWhenComparingToAlphaReleaseIdentifierThenAPositiveNumberShouldBeReturned() {
        var current = new AlphaReleaseIdentifier("alpha0");
        var other = new AlphaReleaseIdentifier("alpha");

        assertThat(current).isGreaterThan(other);
        assertThat(other).isLessThan(current);
    }

    @Test
    void givenBetaReleaseIdentifierWhenComparingToAlphaReleaseIdentifierThenANegativeNumberShouldBeReturned() {
        var current = new AlphaReleaseIdentifier("alpha");
        var other = new BetaReleaseIdentifier("beta");

        assertThat((Identifier) current).isLessThan(other);
        assertThat((Identifier) other).isGreaterThan(current);
    }

    @Test
    void givenCandidateReleaseIdentifierWhenComparingToAlphaReleaseIdentifierThenANegativeNumberShouldBeReturned() {
        var current = new AlphaReleaseIdentifier("alpha");
        var other = new CandidateReleaseIdentifier("rc");

        assertThat((Identifier) current).isLessThan(other);
        assertThat((Identifier) other).isGreaterThan(current);
    }

    @Test
    void givenUnclassifiedIdentifierWhenComparingToAlphaReleaseIdentifierThenANegativeNumberShouldBeReturned() {
        var current = new AlphaReleaseIdentifier("alpha");
        var other = new UnclassifiedIdentifier("meta-valid");

        assertThat((Identifier) current).isLessThan(other);
        assertThat((Identifier) other).isGreaterThan(current);
    }
}
