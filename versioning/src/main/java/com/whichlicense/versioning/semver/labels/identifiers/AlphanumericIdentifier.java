package com.whichlicense.versioning.semver.labels.identifiers;

import com.whichlicense.versioning.semver.Identifier;

public sealed interface AlphanumericIdentifier extends Identifier permits AlphaReleaseIdentifier, BetaReleaseIdentifier, CandidateReleaseIdentifier, NightlyReleaseIdentifier, UnclassifiedIdentifier {
}
