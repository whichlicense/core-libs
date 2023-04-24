/*
 * Copyright (c) 2023 - for information on the respective copyright owner
 * see the NOTICE file and/or the repository https://github.com/whichlicense/core-libs.
 *
 * SPDX-License-Identifier: Apache-2.0
 */

import com.whichlicense.metadata.sourcing.MetadataSourceResolverProvider;
import com.whichlicense.metadata.sourcing.artifact.jar.JarMetadataSourceResolverProvider;
import com.whichlicense.metadata.sourcing.artifact.zip.ZipMetadataSourceResolverProvider;
import com.whichlicense.metadata.sourcing.repository.local.LocalMetadataRepositorySourceResolverProvider;

module whichlicense.sourcing {
    requires java.logging;
    exports com.whichlicense.metadata.sourcing;
    exports com.whichlicense.metadata.sourcing.artifact;
    exports com.whichlicense.metadata.sourcing.repository;
    provides MetadataSourceResolverProvider with JarMetadataSourceResolverProvider, ZipMetadataSourceResolverProvider, LocalMetadataRepositorySourceResolverProvider;
    uses MetadataSourceResolverProvider;
}
