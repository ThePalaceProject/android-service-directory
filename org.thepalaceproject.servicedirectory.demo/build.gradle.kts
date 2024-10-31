dependencies {
    coreLibraryDesugaring(libs.android.desugaring)

    implementation(project(":org.thepalaceproject.servicedirectory.boot"))
    implementation(project(":org.thepalaceproject.servicedirectory.main"))

    implementation(libs.androidx.activity)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.appcompat.resources)
    implementation(libs.androidx.arch.core.common)
    implementation(libs.androidx.arch.core.runtime)
    implementation(libs.androidx.collection)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.constraintlayout.core)
    implementation(libs.androidx.constraintlayout.solver)
    implementation(libs.androidx.core)
    implementation(libs.androidx.customview)
    implementation(libs.androidx.drawerlayout)
    implementation(libs.androidx.emoji2)
    implementation(libs.androidx.emoji2.views)
    implementation(libs.androidx.emoji2.views.helper)
    implementation(libs.androidx.loader)
    implementation(libs.androidx.fragment)
    implementation(libs.androidx.lifecycle.common)
    implementation(libs.androidx.lifecycle.extensions)
    implementation(libs.androidx.lifecycle.livedata)
    implementation(libs.androidx.lifecycle.livedata.core)
    implementation(libs.androidx.lifecycle.process)
    implementation(libs.androidx.lifecycle.runtime)
    implementation(libs.androidx.lifecycle.viewmodel)
    implementation(libs.androidx.lifecycle.viewmodel.savedstate)
    implementation(libs.androidx.savedstate)
    implementation(libs.androidx.startup.runtime)
    implementation(libs.androidx.tracing)
    implementation(libs.androidx.vectordrawable)

    implementation(libs.kotlin.stdlib)
    implementation(libs.logback.android)
    implementation(libs.slf4j)
}

fun gitCommit(): String {
    val proc = ProcessBuilder("git", "rev-parse", "--short", "HEAD")
        .redirectOutput(ProcessBuilder.Redirect.PIPE)
        .redirectError(ProcessBuilder.Redirect.PIPE)
        .start()

    proc.waitFor(10L, TimeUnit.SECONDS)
    return proc.inputStream.bufferedReader().readText().trim()
}

android {
    this.buildFeatures.buildConfig = true

    defaultConfig {
        this.versionName = project.extra["VERSION_NAME"] as String
        this.versionCode = 1000
        setProperty("archivesBaseName", "palace-servicedirectory")

        buildConfigField("String", "GROUP_NAME", "\"${project.extra["GROUP"]}\"")
        buildConfigField("String", "ARTIFACT_NAME", "\"${project.extra["POM_ARTIFACT_ID"]}\"")
        buildConfigField("String", "VERSION_NAME", "\"${this.versionName}\"")
        buildConfigField("String", "COMMIT_NAME", "\"${gitCommit()}\"")
    }
}
