package com.whichlicense.versioning.semver.labels;

import com.whichlicense.versioning.Label;
import com.whichlicense.versioning.semver.Identifier;

import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;

public record PreReleaseMetadata(Identifier... identifiers) implements Label {
    @Override
    public String toString() {
        return Stream.of(identifiers).map(Identifier::raw).collect(joining("."));
    }
}
