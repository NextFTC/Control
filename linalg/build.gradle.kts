plugins {
    alias(libs.plugins.kotlin)
    alias(libs.plugins.spotless)
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

spotless {
    kotlin {
        ktlint().editorConfigOverride(
            mapOf(
                "ktlint_code_style" to "intellij_idea",
                "indent_size" to "4",
                "continuation_indent_size" to "4",
                "ktlint_standard_no-wildcard-imports" to "disabled",
            ),
        )
    }
    kotlinGradle {
        ktlint().editorConfigOverride(
            mapOf(
                "ktlint_code_style" to "intellij_idea",
                "indent_size" to "4",
                "continuation_indent_size" to "4",
                "ktlint_standard_no-wildcard-imports" to "disabled",
            ),
        )
    }
}
