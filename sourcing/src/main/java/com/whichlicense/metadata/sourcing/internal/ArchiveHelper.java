/*
 * Copyright (c) 2023 - for information on the respective copyright owner
 * see the NOTICE file and/or the repository https://github.com/whichlicense/core-libs.
 *
 * SPDX-License-Identifier: Apache-2.0
 */
package com.whichlicense.metadata.sourcing.internal;

import java.io.IOException;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.logging.Logger;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public final class ArchiveHelper {
    public static Path resolveRoot(Path path) {
        Objects.requireNonNull(path);
        try {
            //Handle potential memory leaks?
            var archiveFS = FileSystems.newFileSystem(path, (ClassLoader) null);
            Logger.getLogger("whichlicense.sourcing.archive")
                    .finest("Reading from archive input source: " + path);
            return archiveFS.getPath("/");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Path resolveRoot(URL url) {
        Objects.requireNonNull(url);
        try {
            var fileName = url.getFile().lastIndexOf("/") + 1;
            var tempDir = Files.createTempDirectory("whichlicense-archive-");
            var tempArchiveFile = tempDir.resolve(url.getFile().substring(fileName));
            Files.copy(url.openStream(), tempArchiveFile, REPLACE_EXISTING);
            Logger.getLogger("whichlicense.sourcing.archive")
                    .finest("Archive input source temporarily downloaded to: " + tempArchiveFile);
            return resolveRoot(tempArchiveFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
