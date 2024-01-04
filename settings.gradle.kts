pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "NookNook"
include(":app")
include(":feature:phone")
include(":feature:tutorial")
include(":feature:todo")
include(":core:designsystem")
include(":core:data")
include(":core:domain")
include(":core:database")
