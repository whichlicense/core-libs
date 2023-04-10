/*
 * Copyright (c) 2023 - for information on the respective copyright owner
 * see the NOTICE file and/or the repository https://github.com/whichlicense/core-libs.
 *
 * SPDX-License-Identifier: Apache-2.0
 */
package com.whichlicense.metadata.identity;

import static java.lang.System.currentTimeMillis;

/**
 * The Identity class is a critical component of the WhichLicense Spectra network service,
 * providing robust, lossless encoding and decoding algorithms for generating unique and
 * high-throughput metadata identifiers. Spectra has stringent requirements, including
 * achieving a minimum of 15k unique IDs per millisecond and instance for a minimum of
 * 65 years while ensuring zone, replica and context independence in an uncoordinated manner
 * across multiple data centres and deployment regions. Furthermore, the generated IDs must
 * maintain rough time ordering, be language and tooling agnostic for lossless encoding and
 * decoding, and allow for performant decomposition into the material used to generate the
 * ID while also fitting in a 64-bit number.
 *
 * <p>Every generated identity spectra are composed of the following material (components):</p>
 * <ul>
 *     <li><strong>milliseconds</strong> - 41 bits (timestamp utilizing a custom epoch to accommodate a 69-year usage window)</li>
 *     <li><strong>zone</strong> - 7 bits (128 possible independent cross-region deployments that share a single ID pool)</li>
 *     <li><strong>replica</strong> - 2 bits (4 possible independent replicas per zone)</li>
 *     <li><strong>context</strong> - 14 bits (either an atomic rollover every 16_384 requests or the wrapped thread identifier of the requests processor)</li>
 * </ul>
 *
 * @author David Greven
 * @version 0
 * @since 0.0.0
 */
public final class Identity {
    static final long WRAP = 16_384L;
    static final long EPOCH = 1680979723157L;

    /**
     * Internal constructor to prevent instantiation of the Identity class.
     *
     * <p>The Identity class only contains static methods and should not be instantiated.</p>
     *
     * @throws UnsupportedOperationException if attempted to be instantiated
     * @since 0.0.0
     */
    Identity() {
        throw new UnsupportedOperationException("Static factory class Identity should not be instantiated!");
    }

    /**
     * Extracts the milliseconds component from an identity.
     *
     * @param identity The identity to extract the milliseconds from.
     * @return The milliseconds component of the identity.
     * @since 0.0.0
     */
    public static long milliseconds(long identity) {
        return (identity >>> 23) & ((1L << 41) - 1);
    }

    /**
     * Calculates the time in milliseconds represented by an identity.
     *
     * @param identity The identity to calculate the time from.
     * @return The time in milliseconds represented by the identity.
     * @since 0.0.0
     */
    public static long time(long identity) {
        return milliseconds(identity) + EPOCH;
    }

    /**
     * Compares two identities based on their milliseconds component.
     *
     * @param lhs The left-hand side identity for comparison.
     * @param rhs The right-hand side identity for comparison.
     * @return An int value representing the comparison result. Returns a
     * negative value if lhs is less than rhs, a positive value if lhs is
     * greater than rhs, and 0 if lhs is equal to rhs.
     * @since 0.0.0
     */
    public static int compare(long lhs, long rhs) {
        return Long.compareUnsigned(milliseconds(lhs), milliseconds(rhs));
    }

    /**
     * Extracts the zone component from an identity.
     *
     * @param identity The identity to extract the zone from.
     * @return The zone component of the identity.
     * @since 0.0.0
     */
    public static byte zone(long identity) {
        return (byte) ((identity >>> 16) & 0x7F);
    }

    /**
     * Extracts the replica component from an identity.
     *
     * @param identity The identity to extract the replica from.
     * @return The replica component of the identity.
     * @since 0.0.0
     */
    public static byte replica(long identity) {
        return (byte) ((identity >>> 14) & 0x3);
    }

    /**
     * Extracts the context component from the given identity.
     *
     * @param identity the identity value
     * @return the context component of the identity
     * @since 0.0.0
     */
    public static long context(long identity) {
        return identity & ((1L << 14) - 1);
    }

    /**
     * Converts a given identity to its corresponding hexadecimal representation.
     *
     * @param identity the identity value to convert
     * @return the hexadecimal representation of the identity
     * @since 0.0.0
     */
    public static String toHex(long identity) {
        return Long.toHexString(identity);
    }

    /**
     * Parses a hexadecimal string and returns the corresponding identity.
     *
     * @param identity the hexadecimal string to parse
     * @return the identity represented by the hexadecimal string
     * @throws NumberFormatException if the input string is not a valid hexadecimal
     *                               representation
     * @since 0.0.0
     */
    public static long fromHex(String identity) {
        return Long.parseUnsignedLong(identity, 16);
    }

    /**
     * Generates a new identity using the provided components.
     *
     * @param milliseconds the milliseconds component (41 bits)
     * @param zone         the zone component (7 bits)
     * @param replica      the replica component (2 bits)
     * @param context      the context component (14 bits)
     * @return the generated identity as a long value
     * @since 0.0.0
     */
    public static long generate(long milliseconds, byte zone, byte replica, long context) {
        long identity = (milliseconds & ((1L << 41) - 1)) << 23;
        identity |= (zone & 0x7F) << 16;
        identity |= (replica & 0x3) << 14;
        identity |= context & ((1L << 14) - 1);
        return identity;
    }

    /**
     * Generates a new identity using the current system time offset by a custom
     * epoch as the milliseconds component.
     *
     * @param zone    the zone component (7 bits)
     * @param replica the replica component (2 bits)
     * @param context the context component (14 bits)
     * @return the generated identity as a long value
     * @since 0.0.0
     */
    public static long generate(byte zone, byte replica, long context) {
        return generate(currentTimeMillis() - EPOCH, zone, replica, context);
    }

    /**
     * Generates a new identity using a wrapped context and the current system time
     * offset by a custom epoch as the milliseconds component.
     *
     * @param context the context component (14 bits)
     * @return the generated identity as a long value
     * @since 0.0.0
     */
    public static long wrapAndGenerate(long context) {
        return generate((byte) 0, (byte) 0, context % WRAP);
    }
}
