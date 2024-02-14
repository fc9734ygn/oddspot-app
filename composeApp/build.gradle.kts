import org.jetbrains.compose.ExperimentalComposeLibrary
import org.jetbrains.kotlin.gradle.dsl.KotlinCompile

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.serialization)
    alias(libs.plugins.sqldelight)
    alias(libs.plugins.ksp)
//    id("com.google.devtools.ksp")
    id("dev.icerock.mobile.multiplatform-resources") // For some reason alias() doesn't work here
}

kotlin {
    // https://github.com/google/ksp/issues/1569
    applyDefaultHierarchyTemplate()

    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
//            isStatic = true // https://github.com/JetBrains/compose-multiplatform/issues/3386#issuecomment-1656695188
            export(libs.moko.resources)
            export(libs.moko.graphics)
        }
    }

    sourceSets {
        val commonMain by getting {
            kotlin.srcDir("build/generated/ksp/metadata/commonMain/kotlin")
        }
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.ui)
            @OptIn(ExperimentalComposeLibrary::class)
            implementation(compose.components.resources)

            // Koin
            implementation(libs.koin.core)
            implementation(libs.koin.annotations)
            implementation(libs.koin.compose)

            // Ktor
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.json)
            implementation(libs.ktor.client.serialization)
            implementation(libs.ktor.serialization.kotlinx.json)
            implementation(libs.ktor.client.logging)
            implementation(libs.ktor.client.encoding)
            implementation(libs.ktor.client.contentNegotiation)
            implementation(libs.ktor.client.contentSerializationKotlinxJson)
            implementation(libs.result)

            // SQLDelight
            implementation(libs.sqlDelightCoroutinesExtensions)

            // Datetime
            implementation(libs.kotlinx.datetime)

            // MOKO
            api(libs.moko.resources)
            api(libs.moko.resources.compose)

            // Voyager
            implementation(libs.voyager.navigator)
            implementation(libs.voyager.screenModel)
            implementation(libs.voyager.bottomSheetNavigator)
            implementation(libs.voyager.tabNavigator)
            implementation(libs.voyager.transitions)
            implementation(libs.voyager.koin)

            // Logging
            implementation(libs.kermit)
            implementation(libs.kermit.crashlytics)
        }
        androidMain.dependencies {
            implementation(libs.androidx.activity.compose)
            implementation(libs.ktor.client.okhttp)
            implementation(libs.sqlDelightAndroidDriver)
        }
        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
            implementation(libs.sqlDelightiOSDriver)
            implementation("co.touchlab:stately-common:2.0.5") // https://github.com/cashapp/sqldelight/issues/4357
        }
    }
}

android {
    namespace = "com.homato.oddspot"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")

    defaultConfig {
        applicationId = "com.homato.oddspot"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    dependencies {
        debugImplementation(libs.compose.ui.tooling)
        implementation(libs.koin.core)
        implementation(libs.koin.android)
        implementation(libs.koin.annotations)
//        ksp(libs.koin.ksp.compiler) // It generated duplicate modules for some reason
    }
}

// KSP workaround

dependencies {
    add("kspCommonMainMetadata", "io.insert-koin:koin-ksp-compiler:1.3.0")
    // Add if there is a need for koin annotations in other that commonMain
//    add("kspAndroid", "io.insert-koin:koin-ksp-compiler:1.3.0")
//    add("kspIosX64", "io.insert-koin:koin-ksp-compiler:1.3.0")
//    add("kspIosArm64", "io.insert-koin:koin-ksp-compiler:1.3.0")
//    add("kspIosSimulatorArm64", "io.insert-koin:koin-ksp-compiler:1.3.0")
}
tasks.withType<KotlinCompile<*>>().configureEach {
    if (name != "kspCommonMainKotlinMetadata") {
        dependsOn("kspCommonMainKotlinMetadata")
    }
}
afterEvaluate {
    tasks.filter {
        it.name.contains("SourcesJar", true)
    }.forEach {
        println("SourceJarTask====>${it.name}")
        it.dependsOn("kspCommonMainKotlinMetadata")
    }
}

// SqlDelight config
sqldelight {
    databases {
        create("Database") {
            packageName.set("com.homato.oddspot")
        }
    }
}

// MOKO config
multiplatformResources {
    resourcesPackage.set("com.homato.oddspot")
}
