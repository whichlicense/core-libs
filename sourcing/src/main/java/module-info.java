/*
 * Copyright (c) 2023 - for information on the respective copyright owner
 * see the NOTICE file and/or the repository https://github.com/whichlicense/core-libs.
 *
 * SPDX-License-Identifier: Apache-2.0
 */

import com.whichlicense.metadata.sourcing.MetadataSourceResolverProvider;
import com.whichlicense.metadata.sourcing.artifact.jar.JarMetadataSourceResolverProvider;
import com.whichlicense.metadata.sourcing.artifact.tarball.TarballMetadataSourceResolverProvider;
import com.whichlicense.metadata.sourcing.artifact.zip.ZipMetadataSourceResolverProvider;
import com.whichlicense.metadata.sourcing.repository.local.LocalMetadataRepositorySourceResolverProvider;

module whichlicense.sourcing {
    requires java.logging;
    requires org.apache.commons.compress;
    requires configuration.configuration;
    exports com.whichlicense.metadata.sourcing;
    exports com.whichlicense.metadata.sourcing.artifact;
    exports com.whichlicense.metadata.sourcing.repository;
    exports com.whichlicense.metadata.sourcing.internal to whichlicense.sourcing.github, whichlicense.sourcing.npm;
    provides MetadataSourceResolverProvider with JarMetadataSourceResolverProvider, TarballMetadataSourceResolverProvider, ZipMetadataSourceResolverProvider, LocalMetadataRepositorySourceResolverProvider;
    uses MetadataSourceResolverProvider;
}
