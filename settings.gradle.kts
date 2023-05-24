pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    @Suppress("UnstableApiUsage")
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)

    @Suppress("UnstableApiUsage")
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://devrepo.kakao.com/nexus/content/groups/public/") }
    }



    versionCatalogs {
        create("libs") {
            from(files("gradle/common.versions.toml"))
        }
    }
}
rootProject.name = "BeHealthy"
include (":app")
include(":data")
include(":domain")
include(":common")
