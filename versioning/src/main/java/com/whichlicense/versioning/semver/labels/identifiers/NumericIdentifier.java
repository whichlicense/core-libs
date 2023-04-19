package com.whichlicense.versioning.semver.labels.identifiers;

import com.whichlicense.versioning.semver.Identifier;

import static java.lang.Integer.compareUnsigned;

public record NumericIdentifier(int num) implements Identifier {
    @Override
    public int compareTo(Identifier other) {
        return switch (other) {
            case NumericIdentifier(int otherNum) -> compareUnsigned(num, otherNum);
            default -> LESS_THAN;
        };
    }

    @Override
    public int precedence(Identifier other) {
        return 0;
    }

    @Override
    public String raw() {
        return Integer.toString(num);
    }
}
