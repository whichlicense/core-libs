package com.whichlicense.versioning.semver;

import com.whichlicense.versioning.Label;
import com.whichlicense.versioning.Prefix;
import com.whichlicense.versioning.Version;
import com.whichlicense.versioning.VersionFormatException;
import com.whichlicense.versioning.semver.labels.BuildMetadata;
import com.whichlicense.versioning.semver.labels.PreReleaseMetadata;
import com.whichlicense.versioning.semver.labels.identifiers.NumericIdentifier;
import com.whichlicense.versioning.semver.labels.identifiers.UnclassifiedIdentifier;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static java.lang.Integer.parseUnsignedInt;
import static java.util.stream.Collectors.toUnmodifiableSet;

public record SemVerVersion(int major, int minor, int patch, Prefix prefix, PreReleaseMetadata preReleaseMetadata,
                            BuildMetadata buildMetadata) implements Version<SemVerVersion> {
    public SemVerVersion {
        if (major < 0 || minor < 0 || patch < 0) {
            throw new VersionFormatException("SemVer version components cannot be below zero.");
        }
    }

    private SemVerVersion(String major, String minor, String patch, String preReleaseMetadata) {
        this(parseUnsignedInt(major), parseUnsignedInt(minor), parseUnsignedInt(patch),
                null, parsePreReleaseMetadata(preReleaseMetadata).orElse(null), null);
    }

    private static Optional<PreReleaseMetadata> parsePreReleaseMetadata(String preReleaseMetadata) {
        var identifiers = preReleaseMetadata.split("\\.");
        if(identifiers.length == 0) return Optional.empty();
        return Optional.of(new PreReleaseMetadata(Stream.of(identifiers)
                .map(SemVerVersion::parsePreReleaseIdentifier).toArray(Identifier[]::new)));
    }

    private static Identifier parsePreReleaseIdentifier(String identifier) {
        try {
            return new NumericIdentifier(parseUnsignedInt(identifier));
        } catch (NumberFormatException e) {
            //TODO fix
            return new UnclassifiedIdentifier(identifier);
        }
    }

    @Override
    public String toString() {
        return "%s%d.%d.%d%s%s".formatted(prefix == null ? "" : prefix, major, minor, patch,
                preReleaseMetadata == null ? "" : "-%s".formatted(preReleaseMetadata),
                buildMetadata == null ? "" : "+%s".formatted(buildMetadata));
    }

    @Override
    public Set<Label> labels() {
        return Set.of(preReleaseMetadata, buildMetadata).stream().filter(Objects::nonNull).collect(toUnmodifiableSet());
    }

    //breaks the Liskov Substitution Principal???
    @Override
    public int compareTo(SemVerVersion other) {
        return 0;
    }

    public static SemVerVersion parse(String version) {
        String regex = "^(0|[1-9]\\d*)\\.(0|[1-9]\\d*)\\.(0|[1-9]\\d*)(?:-((?:0|[1-9]\\d*|\\d*[a-zA-Z-][0-9a-zA-Z-]*)(?:\\.(?:0|[1-9]\\d*|\\d*[a-zA-Z-][0-9a-zA-Z-]*))*))?(?:\\+([0-9a-zA-Z-]+(?:\\.[0-9a-zA-Z-]+)*))?$";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(version);

        if (matcher.matches()) {
            String major = matcher.group(1);
            String minor = matcher.group(2);
            String patch = matcher.group(3);
            String prerelease = matcher.group(4);
            String buildMetadata = matcher.group(5);

            System.out.println("Major version: " + major);
            System.out.println("Minor version: " + minor);
            System.out.println("Patch version: " + patch);
            System.out.println("Prerelease: " + prerelease);
            System.out.println("Build metadata: " + buildMetadata);
            var v = new SemVerVersion(major, minor, patch, prerelease);
            System.out.println(Arrays.toString(v.preReleaseMetadata.identifiers()));
            return v;
        } else {
            System.out.println("SemVer does not match the pattern.");
            return null;
        }
    }

    public static void main(String[] args) {
        System.out.println(parse("1.2.3-alpha+001"));
        System.out.println(parse("1.0.0-0A.is.legal"));
        System.out.println(parse("1.2.3----RC-SNAPSHOT.12.9.1--.12"));
    }
}
