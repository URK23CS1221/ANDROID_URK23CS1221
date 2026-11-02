pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "ANDROID_URK23CS1221"
include(":app")
include(":ex01")
include(":ex02")
include(":ex03")
include(":ex04")
include(":ex05")
include(":ex06")
include(":ex07")
include(":ex08")
include(":ex09")
include(":ex10")
include(":ex11")
