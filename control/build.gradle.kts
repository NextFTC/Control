plugins {
    alias(libs.plugins.kotlin)
    alias(libs.plugins.spotless)
}

description = "A WPIMath inspired library for controls and other math classes and functions."

dependencies {
    api(project(":units"))
    api(project(":linalg"))
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
