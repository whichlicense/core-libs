package com.whichlicense.versioning.semver.labels.identifiers;

import com.whichlicense.versioning.semver.Identifier;

public record CandidateReleaseIdentifier(String raw) implements AlphanumericIdentifier {
    @Override
    public int compareTo(Identifier other) {
        return switch (other) {
            case NumericIdentifier ignored -> GREATER_THAN;
            case NightlyReleaseIdentifier ignored -> GREATER_THAN;
            case AlphaReleaseIdentifier ignored -> GREATER_THAN;
            case BetaReleaseIdentifier ignored -> GREATER_THAN;
            default -> raw.compareTo(other.raw());
        };
    }

    @Override
    public int precedence(Identifier other) {
        return 0;
    }
}
