package com.whichlicense.versioning.semver;

import com.whichlicense.versioning.Precedence;
import com.whichlicense.versioning.semver.labels.identifiers.AlphanumericIdentifier;
import com.whichlicense.versioning.semver.labels.identifiers.NumericIdentifier;

public sealed interface Identifier extends Comparable<Identifier>, Precedence<Identifier> permits AlphanumericIdentifier, NumericIdentifier {
    String raw();
}
