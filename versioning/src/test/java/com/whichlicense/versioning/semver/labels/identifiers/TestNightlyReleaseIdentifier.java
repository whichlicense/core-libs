package com.whichlicense.versioning.semver.labels.identifiers;

import com.whichlicense.versioning.semver.Identifier;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TestNightlyReleaseIdentifier {
    @Test
    void givenNumericIdentifierWhenComparingToNightlyReleaseIdentifierThenANegativeNumberShouldBeReturned() {
        var current = new NightlyReleaseIdentifier("nightly");
        var other = new NumericIdentifier(21);

        assertThat((Identifier) current).isGreaterThan(other);
        assertThat((Identifier) other).isLessThan(current);
    }

    @Test
    void givenNightlyReleaseIdentifierWithIdenticalValueWhenComparingToNightlyReleaseIdentifierThenZeroShouldBeReturned() {
        var current = new NightlyReleaseIdentifier("nightly");
        var other = new NightlyReleaseIdentifier("nightly");

        assertThat(current).isEqualByComparingTo(current);
        assertThat(current).isEqualByComparingTo(other);
        assertThat(other).isEqualByComparingTo(other);
        assertThat(other).isEqualByComparingTo(current);
    }

    @Test
    void givenNightlyReleaseIdentifierWithALexicographicallyHigherValueWhenComparingToNightlyReleaseIdentifierThenAPositiveNumberShouldBeReturned() {
        var current = new NightlyReleaseIdentifier("nightly0");
        var other = new NightlyReleaseIdentifier("nightly");

        assertThat(current).isGreaterThan(other);
        assertThat(other).isLessThan(current);
    }

    @Test
    void givenNightlyReleaseIdentifierWhenComparingToNightlyReleaseIdentifierThenANegativeNumberShouldBeReturned() {
        var current = new NightlyReleaseIdentifier("nightly");
        var other = new AlphaReleaseIdentifier("alpha");

        assertThat((Identifier) current).isLessThan(other);
        assertThat((Identifier) other).isGreaterThan(current);
    }

    @Test
    void givenBetaReleaseIdentifierWhenComparingToNightlyReleaseIdentifierThenANegativeNumberShouldBeReturned() {
        var current = new NightlyReleaseIdentifier("nightly");
        var other = new BetaReleaseIdentifier("beta");

        assertThat((Identifier) current).isLessThan(other);
        assertThat((Identifier) other).isGreaterThan(current);
    }

    @Test
    void givenCandidateReleaseIdentifierWhenComparingToNightlyReleaseIdentifierThenANegativeNumberShouldBeReturned() {
        var current = new NightlyReleaseIdentifier("nightly");
        var other = new CandidateReleaseIdentifier("rc");

        assertThat((Identifier) current).isLessThan(other);
        assertThat((Identifier) other).isGreaterThan(current);
    }

    @Test
    void givenUnclassifiedIdentifierWhenComparingToNightlyReleaseIdentifierThenANegativeNumberShouldBeReturned() {
        var current = new NightlyReleaseIdentifier("nightly");
        var other = new UnclassifiedIdentifier("meta-valid");

        assertThat((Identifier) current).isGreaterThan(other);
        assertThat((Identifier) other).isLessThan(current);
    }
}
