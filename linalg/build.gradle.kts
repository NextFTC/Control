plugins {
    alias(libs.plugins.kotlin)
}

description = "A custom linear algebra library for NextControl."

dependencies {
}

nextFTCPublishing {
    displayName = "NextControl Linear Algebra"
    logoPath = "../assets/logo-icon.svg"
}

kotlin {
    jvmToolchain(8)
    compilerOptions {
        freeCompilerArgs.addAll("-Xjvm-default=all", "-Xconsistent-data-class-copy-visibility")
    }
}