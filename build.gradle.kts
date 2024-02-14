plugins {
    // this is necessary to avoid the plugins to be loaded multiple times
    // in each subproject's classloader
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.androidLibrary) apply false
    alias(libs.plugins.jetbrainsCompose) apply false
    alias(libs.plugins.kotlinMultiplatform) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.serialization) apply false
    alias(libs.plugins.detekt)
}

allprojects {
    apply(plugin = "io.gitlab.arturbosch.detekt")
    detekt {
        config.setFrom("$rootDir/default-detekt-config.yml")
    }
    // With this, the detekt task is able to find issues everywhere
    tasks.withType<io.gitlab.arturbosch.detekt.Detekt> detekt@{
        setSource(files(project.projectDir))
        exclude("**/*.kts")
        exclude("**/build/**")
    }
}

buildscript {
    dependencies {
        classpath(libs.moko.resources.generator)
    }
}