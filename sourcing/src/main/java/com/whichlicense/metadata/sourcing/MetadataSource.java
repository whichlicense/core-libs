/*
 * Copyright (c) 2023 - for information on the respective copyright owner
 * see the NOTICE file and/or the repository https://github.com/whichlicense/core-libs.
 *
 * SPDX-License-Identifier: Apache-2.0
 */
package com.whichlicense.metadata.sourcing;

import com.whichlicense.metadata.sourcing.artifact.MetadataArtifactSource;
import com.whichlicense.metadata.sourcing.repository.MetadataRepositorySource;

import java.nio.file.Path;

public sealed interface MetadataSource permits MetadataArtifactSource, MetadataRepositorySource {
    MetadataOrigin origin();
    Path path();
}
