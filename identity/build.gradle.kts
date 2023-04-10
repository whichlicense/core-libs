/*
 * Copyright (c) 2023 - for information on the respective copyright owner
 * see the NOTICE file and/or the repository https://github.com/whichlicense/core-libs.
 *
 * SPDX-License-Identifier: Apache-2.0
 */
plugins {
    id("java-library")
    id("maven-publish")
    id("signing")
}

group = "com.whichlicense"
version = "0.0.0-SNAPSHOT"

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(19))
    }
    withJavadocJar()
    withSourcesJar()
}

repositories {
    maven {
        url = uri("https://s01.oss.sonatype.org/content/repositories/snapshots")
    }
    mavenCentral()
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.2")
    testImplementation("org.junit.jupiter:junit-jupiter-params:5.9.2")
    testImplementation("org.assertj:assertj-core:3.24.2")
    testImplementation("com.whichlicense.testing:naming:0.0.0-SNAPSHOT")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.9.2")
}

tasks.withType<JavaCompile> {
    options.compilerArgs.add("--enable-preview")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
    jvmArgs("--enable-preview")
}

publishing {
    publications {
        create<MavenPublication>("identity") {
            artifactId = "identity"
            from(components["java"])
            versionMapping {
                usage("java-api") {
                    fromResolutionOf("runtimeClasspath")
                }
                usage("java-runtime") {
                    fromResolutionResult()
                }
            }
            pom {
                name.set("WhichLicense core-libs/identity")
                description.set("This library provides the Identity API to generate unique metadata identities in an uncoordinated manner.")
                url.set("https://github.com/whichlicense/core-libs/identity")
                licenses {
                    license {
                        name.set("The Apache License, Version 2.0")
                        url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                    }
                }
                developers {
                    developer {
                        id.set("grevend")
                        name.set("David Greven")
                        email.set("david.greven@whichlicense.com")
                    }
                }
                scm {
                    connection.set("scm:git:https://github.com/whichlicense/core-libs.git")
                    developerConnection.set("scm:git:git@github.com:whichlicense/core-libs.git")
                    url.set("https://github.com/whichlicense/core-libs")
                }
            }
        }
    }
    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/whichlicense/core-libs")
            credentials {
                username = project.findProperty("gpr.user") as String? ?: System.getenv("GITHUB_ACTOR")
                password = project.findProperty("gpr.token") as String? ?: System.getenv("GITHUB_TOKEN")
            }
        }
        maven {
            name = "OSSRH"
            url = uri(if ((project.version as String).endsWith("SNAPSHOT"))
                "https://s01.oss.sonatype.org/content/repositories/snapshots/" else
                "https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")
            credentials {
                username = project.findProperty("ossrh.user") as String? ?: System.getenv("OSSRH_USER")
                password = project.findProperty("ossrh.pw") as String? ?: System.getenv("OSSRH_PW")
            }
        }
    }
}

signing {
    if (project.hasProperty("CI")) {
        val signingKey = System.getenv("PKG_SIGNING_KEY")
        val signingPassword = System.getenv("PKG_SIGNING_PW")
        useInMemoryPgpKeys(signingKey, signingPassword)
        sign(publishing.publications["identity"])
    }
}

tasks.javadoc {
    if (JavaVersion.current().isJava9Compatible) {
        (options as StandardJavadocDocletOptions).addBooleanOption("html5", true)
    }
}
