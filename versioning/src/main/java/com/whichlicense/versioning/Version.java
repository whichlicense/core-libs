/*
 * Copyright (c) 2023 - for information on the respective copyright owner
 * see the NOTICE file and/or the repository https://github.com/whichlicense/core-libs.
 *
 * SPDX-License-Identifier: Apache-2.0
 */
package com.whichlicense.versioning;

import java.util.Set;

import static java.util.Objects.isNull;

/**
 *
 */
public interface Version<E extends Version<E>> extends Comparable<E>, Precedence<E> {
    /**
     * @return
     */
    Prefix prefix();

    /**
     * @return
     */
    default boolean isPrefixed() {
        return !isNull(prefix());
    }

    /**
     * @return
     */
    Set<Label> labels();

    /**
     * @return
     */
    default boolean isLabeled() {
        return !labels().isEmpty();
    }

    /**
     * @return
     */
    default boolean isNightly() {
        return false;
    }

    /**
     * @return
     */
    default boolean isAlpha() {
        return false;
    }

    /**
     * @return
     */
    default boolean isBeta() {
        return false;
    }

    /**
     * @return
     */
    default boolean isCandidate() {
        return false;
    }

    @Override
    default int precedence(E other) {
        return this.compareTo(other);
    }
}
