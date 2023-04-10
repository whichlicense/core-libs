/*
 * Copyright (c) 2023 - for information on the respective copyright owner
 * see the NOTICE file and/or the repository https://github.com/whichlicense/core-libs.
 *
 * SPDX-License-Identifier: Apache-2.0
 */
package com.whichlicense.internal.spectra;

import com.whichlicense.metadata.identity.Identity;

import java.util.concurrent.atomic.LongAdder;

/**
 * The LocalIdentitySpectra class is an internal, locally embeddable alternative
 * to the WhichLicense Spectra network services for the CLI.
 *
 * @author David Greven
 * @version 0
 * @since 0.0.0
 */
public final class LocalIdentitySpectra {
    static final LongAdder COUNTER = new LongAdder();

    /**
     * Internal constructor to prevent instantiation of the LocalIdentitySpectra
     * class.
     *
     * <p>The LocalIdentitySpectra class only contains static methods and should
     * not be instantiated.</p>
     *
     * @throws UnsupportedOperationException if attempted to be instantiated
     * @since 0.0.0
     */
    LocalIdentitySpectra() {
        throw new UnsupportedOperationException("Static factory class LocalIdentitySpectra should not be instantiated!");
    }

    /**
     * Generates a new identity using an atomic rollover every 16_384 requests.
     *
     * @return the generated identity as a long value
     * @since 0.0.0
     */
    public static long generate() {
        var spectra = Identity.wrapAndGenerate(COUNTER.longValue());
        COUNTER.increment();
        if (COUNTER.longValue() >= 16_384L) COUNTER.reset();
        return spectra;
    }
}
