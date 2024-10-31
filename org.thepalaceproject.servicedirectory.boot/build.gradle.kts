dependencies {
    coreLibraryDesugaring(libs.android.desugaring)

    implementation(project(":org.thepalaceproject.servicedirectory.main"))

    implementation(libs.androidx.lifecycle.livedata)
    implementation(libs.androidx.lifecycle.livedata.core)
    implementation(libs.kotlin.stdlib)
    implementation(libs.slf4j)
}
