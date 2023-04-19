package com.whichlicense.versioning.semver.labels.identifiers;

import com.whichlicense.versioning.semver.Identifier;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TestCandidateReleaseIdentifier {
    @Test
    void givenNumericIdentifierWhenComparingToCandidateReleaseIdentifierThenANegativeNumberShouldBeReturned() {
        var current = new CandidateReleaseIdentifier("rc");
        var other = new NumericIdentifier(21);

        assertThat((Identifier) current).isGreaterThan(other);
        assertThat((Identifier) other).isLessThan(current);
    }

    @Test
    void givenNightlyReleaseIdentifierWhenComparingToCandidateReleaseIdentifierThenAPositiveNumberShouldBeReturned() {
        var current = new CandidateReleaseIdentifier("rc");
        var other = new NightlyReleaseIdentifier("nightly");

        assertThat((Identifier) current).isGreaterThan(other);
        assertThat((Identifier) other).isLessThan(current);
    }

    @Test
    void givenAlphaReleaseIdentifierWhenComparingToCandidateReleaseIdentifierThenAPositiveNumberShouldBeReturned() {
        var current = new CandidateReleaseIdentifier("rc");
        var other = new AlphaReleaseIdentifier("alpha");

        assertThat((Identifier) current).isGreaterThan(other);
        assertThat((Identifier) other).isLessThan(current);
    }

    @Test
    void givenBetaReleaseIdentifierWhenComparingToCandidateReleaseIdentifierThenAPositiveNumberShouldBeReturned() {
        var current = new CandidateReleaseIdentifier("rc");
        var other = new BetaReleaseIdentifier("beta");

        assertThat((Identifier) current).isGreaterThan(other);
        assertThat((Identifier) other).isLessThan(current);
    }

    @Test
    void givenCandidateReleaseIdentifierWithIdenticalValueWhenComparingToCandidateReleaseIdentifierThenZeroShouldBeReturned() {
        var current = new CandidateReleaseIdentifier("rc");
        var other = new CandidateReleaseIdentifier("rc");

        assertThat(current).isEqualByComparingTo(current);
        assertThat(current).isEqualByComparingTo(other);
        assertThat(other).isEqualByComparingTo(other);
        assertThat(other).isEqualByComparingTo(current);
    }

    @Test
    void givenCandidateReleaseIdentifierWithALexicographicallyHigherValueWhenComparingToCandidateReleaseIdentifierThenAPositiveNumberShouldBeReturned() {
        var current = new CandidateReleaseIdentifier("rc0");
        var other = new CandidateReleaseIdentifier("rc");

        assertThat(current).isGreaterThan(other);
        assertThat(other).isLessThan(current);
    }

    @Test
    void givenUnclassifiedIdentifierWhenComparingToBetaReleaseIdentifierThenANegativeNumberShouldBeReturned() {
        var current = new CandidateReleaseIdentifier("rc");
        var other = new UnclassifiedIdentifier("meta-valid");

        assertThat((Identifier) current).isGreaterThan(other);
        assertThat((Identifier) other).isLessThan(current);
    }
}
