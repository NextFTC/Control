/*
 * Copyright (c) 2025 Hermes FTC
 *
 * Use of this source code is governed by an MIT-style
 * license that can be found in the LICENSE file at the root of this repository or at
 * https://opensource.org/licenses/MIT.
 */

@file:JvmName("SizedVectors")

package dev.nextftc.linalg

import org.ejml.simple.SimpleMatrix

/**
 * A dimensionally type-safe column vector of doubles.
 *
 * The dimension [N] is encoded at the type level using [Nat] types,
 * allowing the compiler to catch dimension mismatches at compile time.
 *
 * Example:
 * ```kotlin
 * val a: SizedVector<N3> = SizedVector.of(N3, 1.0, 2.0, 3.0)
 * val b: SizedVector<N3> = SizedVector.of(N3, 4.0, 5.0, 6.0)
 * val c: SizedVector<N3> = a + b  // Compiles!
 * val dot: Double = a dot b
 * // val d: SizedVector<N2> = a + SizedVector.zero(N2)  // Won't compile - dimensions don't match
 * ```
 *
 * @param N The dimension type
 */
class SizedVector<N : Nat> internal constructor(simple: SimpleMatrix, internal val dimNat: N) :
    SizedMatrix<N, N1>(simple, dimNat, N1) {
    init {
        require(simple.numCols() == 1) { "Vector must have exactly one column" }
    }

    @Suppress("ktlint")
    companion object {
        /**
         * Creates a zero vector with dimension specified by the [Nat] type parameter.
         */
        @JvmStatic
        fun <N : Nat> zero(dim: N): SizedVector<N> = SizedVector(SimpleMatrix(dim.num, 1), dim)

        /**
         * Creates a vector from the given values.
         */
        @JvmStatic
        fun <N : Nat> of(dim: N, vararg data: Double): SizedVector<N> {
            require(data.size == dim.num) { "Data size must match dimension" }
            return SizedVector(SimpleMatrix(data.size, 1, false, data), dim)
        }

        /**
         * Creates a vector from a collection.
         */
        @JvmStatic
        fun <N : Nat> of(dim: N, data: Collection<Double>): SizedVector<N> =
            of(dim, *data.toDoubleArray())

        /** Creates a 1-dimensional vector. */
        @JvmStatic
        fun of(x: Double): SizedVector<N1> =
            SizedVector(SimpleMatrix(1, 1, false, doubleArrayOf(x)), N1)

        /** Creates a 2-dimensional vector. */
        @JvmStatic
        fun of(x: Double, y: Double): SizedVector<N2> =
            SizedVector(SimpleMatrix(2, 1, false, doubleArrayOf(x, y)), N2)

        /** Creates a 3-dimensional vector. */
        @JvmStatic
        fun of(x: Double, y: Double, z: Double): SizedVector<N3> =
            SizedVector(SimpleMatrix(3, 1, false, doubleArrayOf(x, y, z)), N3)

        /** Creates a 4-dimensional vector. */
        @JvmStatic
        fun of(x1: Double, x2: Double, x3: Double, x4: Double): SizedVector<N4> =
            SizedVector(SimpleMatrix(4, 1, false, doubleArrayOf(x1, x2, x3, x4)), N4)

        /** Creates a 5-dimensional vector. */
        @JvmStatic
        fun of(x1: Double, x2: Double, x3: Double, x4: Double, x5: Double): SizedVector<N5> =
            SizedVector(SimpleMatrix(5, 1, false, doubleArrayOf(x1, x2, x3, x4, x5)), N5)

        /** Creates a 6-dimensional vector. */
        @JvmStatic
        fun of(x1: Double, x2: Double, x3: Double, x4: Double, x5: Double, x6: Double): SizedVector<N6> =
            SizedVector(SimpleMatrix(6, 1, false, doubleArrayOf(x1, x2, x3, x4, x5, x6)), N6)

        /** Creates a 7-dimensional vector. */
        @JvmStatic
        fun of(x1: Double, x2: Double, x3: Double, x4: Double, x5: Double, x6: Double, x7: Double): SizedVector<N7> =
            SizedVector(SimpleMatrix(7, 1, false, doubleArrayOf(x1, x2, x3, x4, x5, x6, x7)), N7)

        /** Creates an 8-dimensional vector. */
        @JvmStatic
        fun of(x1: Double, x2: Double, x3: Double, x4: Double, x5: Double, x6: Double, x7: Double, x8: Double): SizedVector<N8> =
            SizedVector(SimpleMatrix(8, 1, false, doubleArrayOf(x1, x2, x3, x4, x5, x6, x7, x8)), N8,)

        /** Creates a 9-dimensional vector. */
        @JvmStatic
        fun of(x1: Double, x2: Double, x3: Double, x4: Double, x5: Double, x6: Double, x7: Double, x8: Double, x9: Double): SizedVector<N9> =
            SizedVector(SimpleMatrix(9, 1, false, doubleArrayOf(x1, x2, x3, x4, x5, x6, x7, x8, x9)), N9)

        /** Creates a 10-dimensional vector. */
        @JvmStatic
        fun of(x1: Double, x2: Double, x3: Double, x4: Double, x5: Double, x6: Double, x7: Double, x8: Double, x9: Double, x10: Double): SizedVector<N10> =
            SizedVector(SimpleMatrix(10, 1, false, doubleArrayOf(x1, x2, x3, x4, x5, x6, x7, x8, x9, x10)), N10)
    }

    /** The dimension (length) of this vector. */
    @JvmField
    val dimension: Int = numRows

    /** Returns the element at the given index. */
    operator fun get(i: Int): Double = simple[i, 0]

    /** Sets the element at the given index. */
    operator fun set(i: Int, value: Double) {
        simple[i, 0] = value
    }

    /** Returns a copy of this vector. */
    override fun copy(): SizedVector<N> = SizedVector(simple.copy(), dimNat)

    /** Negates all elements of this vector. */
    override operator fun unaryMinus(): SizedVector<N> = SizedVector(simple.negative(), dimNat)

    /** Adds another vector with the same dimension. */
    operator fun plus(other: SizedVector<N>): SizedVector<N> =
        SizedVector(simple + other.simple, dimNat)

    /** Subtracts another vector with the same dimension. */
    operator fun minus(other: SizedVector<N>): SizedVector<N> =
        SizedVector(simple - other.simple, dimNat)

    /** Multiplies this vector by a scalar. */
    override operator fun times(scalar: Double): SizedVector<N> =
        SizedVector(simple.scale(scalar), dimNat)

    /** Multiplies this vector by a scalar. */
    override operator fun times(scalar: Int): SizedVector<N> = times(scalar.toDouble())

    /** Computes the dot product of this vector with another vector of the same dimension. */
    infix fun dot(other: SizedVector<N>): Double = simple.transpose().mult(other.simple)[0, 0]

    /** Returns the Euclidean norm (magnitude) of this vector. */
    @get:JvmName("magnitude")
    val magnitude: Double
        get() = simple.normF()

    /** Returns a normalized (unit) vector in the same direction. */
    fun normalized(): SizedVector<N> = this * (1.0 / magnitude)

    /** Converts to a [DynamicVector]. */
    fun toDynamicVector(): DynamicVector = DynamicVector(simple)

    override fun toString(): String = buildString {
        append("SizedVector<$dimension>: [")
        for (i in 0 until dimension) {
            append("%10.4f".format(simple[i, 0]))
            if (i < dimension - 1) append(", ")
        }
        append("]")
    }
}

/** Scalar multiplication from the left. */
operator fun <N : Nat> Double.times(vector: SizedVector<N>): SizedVector<N> = vector * this

/** Scalar multiplication from the left. */
operator fun <N : Nat> Int.times(vector: SizedVector<N>): SizedVector<N> = vector * this
