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

rootProject.name = "Nook"
include(":app")
include(":feature:phone")
include(":feature:tutorial")
include(":feature:todo")
include(":core:designsystem")
include(":core:domain")
include(":core:database")
include(":core:ui")
include(":core:datastore")
include(":core:common")
include(":feature:profile")
include(":core:testing")
