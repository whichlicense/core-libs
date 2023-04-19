package com.whichlicense.versioning.semver.labels.identifiers;

import com.whichlicense.versioning.semver.Identifier;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TestBetaReleaseIdentifier {
    @Test
    void givenNumericIdentifierWhenComparingToBetaReleaseIdentifierThenANegativeNumberShouldBeReturned() {
        var current = new BetaReleaseIdentifier("beta");
        var other = new NumericIdentifier(21);

        assertThat((Identifier) current).isGreaterThan(other);
        assertThat((Identifier) other).isLessThan(current);
    }

    @Test
    void givenNightlyReleaseIdentifierWhenComparingToBetaReleaseIdentifierThenAPositiveNumberShouldBeReturned() {
        var current = new BetaReleaseIdentifier("beta");
        var other = new NightlyReleaseIdentifier("nightly");

        assertThat((Identifier) current).isGreaterThan(other);
        assertThat((Identifier) other).isLessThan(current);
    }

    @Test
    void givenAlphaReleaseIdentifierWhenComparingToBetaReleaseIdentifierThenAPositiveNumberShouldBeReturned() {
        var current = new BetaReleaseIdentifier("beta");
        var other = new AlphaReleaseIdentifier("alpha");

        assertThat((Identifier) current).isGreaterThan(other);
        assertThat((Identifier) other).isLessThan(current);
    }

    @Test
    void givenBetaReleaseIdentifierWithIdenticalValueWhenComparingToBetaReleaseIdentifierThenZeroShouldBeReturned() {
        var current = new BetaReleaseIdentifier("beta");
        var other = new BetaReleaseIdentifier("beta");

        assertThat(current).isEqualByComparingTo(current);
        assertThat(current).isEqualByComparingTo(other);
        assertThat(other).isEqualByComparingTo(other);
        assertThat(other).isEqualByComparingTo(current);
    }

    @Test
    void givenBetaReleaseIdentifierWithALexicographicallyHigherValueWhenComparingToBetaReleaseIdentifierThenAPositiveNumberShouldBeReturned() {
        var current = new BetaReleaseIdentifier("beta0");
        var other = new BetaReleaseIdentifier("beta");

        assertThat(current).isGreaterThan(other);
        assertThat(other).isLessThan(current);
    }

    @Test
    void givenCandidateReleaseIdentifierWhenComparingToBetaReleaseIdentifierThenANegativeNumberShouldBeReturned() {
        var current = new BetaReleaseIdentifier("beta");
        var other = new CandidateReleaseIdentifier("rc");

        assertThat((Identifier) current).isLessThan(other);
        assertThat((Identifier) other).isGreaterThan(current);
    }

    @Test
    void givenUnclassifiedIdentifierWhenComparingToBetaReleaseIdentifierThenANegativeNumberShouldBeReturned() {
        var current = new BetaReleaseIdentifier("beta");
        var other = new UnclassifiedIdentifier("meta-valid");

        assertThat((Identifier) current).isLessThan(other);
        assertThat((Identifier) other).isGreaterThan(current);
    }
}
