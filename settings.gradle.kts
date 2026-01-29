pluginManagement {
    repositories {
        mavenLocal()
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        maven("https://maven.aliyun.com/repository/gradle-plugin")
        maven("https://maven.aliyun.com/repository/public")
        maven("https://maven.pkg.jetbrains.space/kotlin/p/kotlin/releases")
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        mavenLocal()
        google()
        maven("https://maven.aliyun.com/repository/public")
        maven("https://maven.pkg.jetbrains.space/kotlin/p/kotlin/releases")
        mavenCentral()
    }
}

rootProject.name = "FlagQuizApp"
include(":app")
