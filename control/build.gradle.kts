plugins {
    alias(libs.plugins.kotlin)
}

description = "A WPIMath inspired library for controls and other math classes and functions."

dependencies {
    api(project(":units"))
}

nextFTCPublishing {
    displayName = "NextControl"
    logoPath = "../assets/logo-icon.svg"
}

kotlin {
    jvmToolchain(8)
    compilerOptions {
        freeCompilerArgs.addAll("-Xconsistent-data-class-copy-visibility")
    }
}