plugins {
    alias(libs.plugins.kotlin)
}

description = "A custom units library for NextControl."

dependencies {
    testImplementation(libs.bundles.kotest)
}

nextFTCPublishing {
    displayName = "NextControl Units"
    logoPath = "../assets/logo-icon.svg"
}

kotlin {
    jvmToolchain(8)
    compilerOptions {
        freeCompilerArgs.addAll("-Xjvm-default=all", "-Xconsistent-data-class-copy-visibility")
    }
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}
