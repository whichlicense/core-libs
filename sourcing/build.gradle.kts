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
version = "0.7.7-SNAPSHOT"

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(20))
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

configurations.all {
    resolutionStrategy.cacheDynamicVersionsFor(10, "minutes")
}

dependencies {
    api("org.apache.commons:commons-compress:1.23.0")
    api("com.whichlicense:configuration:0.7.7-SNAPSHOT")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.3")
    testImplementation("org.junit.jupiter:junit-jupiter-params:5.9.3")
    testImplementation("org.mockito:mockito-subclass:5.4.0")
    testImplementation("org.mockito:mockito-junit-jupiter:5.4.0")
    testImplementation("org.assertj:assertj-core:3.24.2")
    testImplementation("com.whichlicense.testing:naming:0.7.6-SNAPSHOT")
    testImplementation("com.whichlicense.testing:fileref:0.7.6-SNAPSHOT")
    testImplementation("com.whichlicense.testing:nullable:0.7.6-SNAPSHOT")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.9.3")
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
        create<MavenPublication>("sourcing") {
            artifactId = "sourcing"
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
                name.set("WhichLicense core-libs/sourcing")
                description.set("This library provides the MetadataSourceResolverProvider SPI interface to facilitate metadata sourcing.")
                url.set("https://github.com/whichlicense/core-libs/sourcing")
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
        sign(publishing.publications["sourcing"])
    }
}

tasks.javadoc {
    if (JavaVersion.current().isJava9Compatible) {
        (options as StandardJavadocDocletOptions).addBooleanOption("html5", true)
    }
}
