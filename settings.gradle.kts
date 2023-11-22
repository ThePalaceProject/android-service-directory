pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
        google()
    }
}

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            from(files("$rootDir/org.thepalaceproject.android.platform/build_libraries.toml"))
        }
    }

    repositories {
        mavenCentral()
        google()
    }
}

rootProject.name = "org.thepalaceproject.servicedirectory"

include(":org.thepalaceproject.servicedirectory.boot")
include(":org.thepalaceproject.servicedirectory.demo")
include(":org.thepalaceproject.servicedirectory.main")
include(":org.thepalaceproject.servicedirectory.tests")
