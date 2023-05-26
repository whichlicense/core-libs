/*
 * Copyright (c) 2023 - for information on the respective copyright owner
 * see the NOTICE file and/or the repository https://github.com/whichlicense/core-libs.
 *
 * SPDX-License-Identifier: Apache-2.0
 */
package com.whichlicense.foreign;

import java.io.File;
import java.io.IOException;
import java.lang.foreign.MemoryLayout;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.SegmentScope;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.util.Objects;
import java.util.logging.Logger;

import static java.nio.channels.FileChannel.MapMode.READ_ONLY;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import static java.nio.file.StandardOpenOption.READ;

public final class ForeignRuntimeHelper {
    static <T> T requireNonNull(T obj, String symbolName) {
        if (obj == null) {
            throw new UnsatisfiedLinkError("unresolved symbol: " + symbolName);
        }
        return obj;
    }

    static MemorySegment asArray(MemorySegment addr, MemoryLayout layout, long numElements, SegmentScope scope) {
        return MemorySegment.ofAddress(addr.address(), numElements * layout.byteSize(), scope);
    }

    private static void loadLibrary(ClassLoader loader, String name) {
        var os = lookupOS();
        var arch = System.getProperty("os.arch");
        var extension = lookupLibraryExtension(os);
        var libPath = "lib" + name + "-" + os + "-" + arch + "." + extension;

        Logger.getLogger("whichlicense.foreign")
                .finest("Searching: " + libPath);

        try (var inputStream = loader.getResourceAsStream(libPath)) {
            Objects.requireNonNull(inputStream);

            var libFile = File.createTempFile("whichlicense-foreign-", "-" + libPath);
            libFile.deleteOnExit();

            Files.copy(inputStream, libFile.toPath(), REPLACE_EXISTING);

            Logger.getLogger("whichlicense.foreign")
                    .finest("Loading: " + libFile.getAbsolutePath());

            System.load(libFile.getAbsolutePath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static String lookupOS() {
        var os = System.getProperty("os.name");
        if (os.contains("Windows")) return "windows";
        else if (os.contains("Mac") || os.contains("Darwin")) return "darwin";
        else if (os.contains("Linux")) return "linux";
        else throw new IllegalStateException("OS " + os + " is currently unsupported!");
    }

    private static String lookupLibraryExtension(String os) {
        return switch (os) {
            case "windows" -> "dll";
            case "darwin" -> "dylib";
            case "linux" -> "so";
            default -> throw new IllegalStateException("OS " + os + " is currently unsupported!");
        };
    }

    public static MemoryMappedFileStats mapResource(String name, ClassLoader loader) {
        try (var inputStream = loader.getResourceAsStream(name)) {
            Objects.requireNonNull(inputStream);

            var temp = File.createTempFile("whichlicense.foreign." + name + ".", ".json");
            temp.deleteOnExit();

            Files.copy(inputStream, temp.toPath(), REPLACE_EXISTING);

            MemorySegment segment;
            long size;
            try (var channel = FileChannel.open(temp.toPath(), READ)) {
                size = channel.size();
                segment = channel.map(READ_ONLY, 0L, size, SegmentScope.global());
                segment.force();
                segment.load();
            }

            return new MemoryMappedFileStats(segment, size);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
