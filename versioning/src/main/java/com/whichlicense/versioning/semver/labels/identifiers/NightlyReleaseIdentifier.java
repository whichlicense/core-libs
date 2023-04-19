package com.whichlicense.versioning.semver.labels.identifiers;

import com.whichlicense.versioning.semver.Identifier;

public record NightlyReleaseIdentifier(String raw) implements AlphanumericIdentifier {
    @Override
    public int compareTo(Identifier other) {
        return switch (other) {
            case NumericIdentifier ignored -> GREATER_THAN;
            case AlphaReleaseIdentifier ignored -> LESS_THAN;
            case BetaReleaseIdentifier ignored -> LESS_THAN;
            case CandidateReleaseIdentifier ignored -> LESS_THAN;
            default -> raw.compareTo(other.raw());
        };
    }

    @Override
    public int precedence(Identifier other) {
        return 0;
    }
}
