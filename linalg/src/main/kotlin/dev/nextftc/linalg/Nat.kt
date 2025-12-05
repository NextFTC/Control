package dev.nextftc.linalg

/**
 * Type-level natural numbers for compile-time matrix dimension checking.
 * Use these as generic bounds on [Matrix] to ensure dimensional correctness at compile time.
 */
sealed interface Nat {
    val num: Int
}

/** Type-level representation of 1 */
data object N1 : Nat {
    override val num = 1
}

/** Type-level representation of 2 */
data object N2 : Nat {
    override val num = 2
}

/** Type-level representation of 3 */
data object N3 : Nat {
    override val num = 3
}

/** Type-level representation of 4 */
data object N4 : Nat {
    override val num = 4
}

/** Type-level representation of 5 */
data object N5 : Nat {
    override val num = 5
}

/** Type-level representation of 6 */
data object N6 : Nat {
    override val num = 6
}

/** Type-level representation of 7 */
data object N7 : Nat {
    override val num = 7
}

/** Type-level representation of 8 */
data object N8 : Nat {
    override val num = 8
}

/** Type-level representation of 9 */
data object N9 : Nat {
    override val num = 9
}

/** Type-level representation of 10 */
data object N10 : Nat {
    override val num = 10
}

/** Type-level representation of an unknown/dynamic dimension */
data class NDynamic(override val num: Int) : Nat
