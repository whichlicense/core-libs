/*
 * Copyright (c) 2023 - for information on the respective copyright owner
 * see the NOTICE file and/or the repository https://github.com/whichlicense/core-libs.
 *
 * SPDX-License-Identifier: Apache-2.0
 */
package com.whichlicense.logging;

import java.io.IOException;
import java.nio.file.Path;
import java.time.Instant;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import static java.time.ZoneOffset.UTC;
import static java.util.logging.Level.ALL;
import static java.util.logging.Level.OFF;

public final class Logging {
    public static void configure(boolean enabled, Path outputDir) {
        var rootLogger = Logger.getLogger("");

        if (enabled) {
            rootLogger.setLevel(ALL);

            try {
                var fileHandler = new FileHandler(outputDir.toString() + "/whichlicense-"
                        + Instant.now().atZone(UTC).toEpochSecond() + ".log");
                fileHandler.setFormatter(new Formatter() {
                    @Override
                    public String format(LogRecord record) {
                        return String.format("%1$tY-%1$tm-%1$td %1$tH:%1$tM:%1$tS.%1$tL [%2$s] %3$-7s %4$-36s - %5$s%6$s%n",
                                record.getMillis(), Thread.currentThread().getName(), record.getLevel(),
                                record.getLoggerName(), record.getMessage(), (record.getThrown() != null ? "\n" + record.getThrown() : ""));
                    }
                });

                rootLogger.addHandler(fileHandler);
                rootLogger.setUseParentHandlers(false);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            rootLogger.setLevel(OFF);
        }
    }
}
