/*
 * Copyright (c) 2025 NextFTC Team
 *
 *  Use of this source code is governed by an BSD-3-clause
 *  license that can be found in the LICENSE.md file at the root of this repository or at
 *  https://opensource.org/license/bsd-3-clause.
 */

package dev.nextftc.control.util

import dev.nextftc.linalg.Matrix
import dev.nextftc.linalg.Nat
import dev.nextftc.linalg.Vector
import kotlin.math.pow

internal fun <N : Nat> makeBrysonMatrix(tolerances: Vector<N>): Matrix<N, N> {
    val matrix = Matrix.Companion.zero(tolerances.natRows, tolerances.natRows)

    for (i in 0 until tolerances.numRows) {
        if (tolerances[i].isInfinite()) {
            matrix[i, i] = 0.0
        } else {
            matrix[i, i] = 1.0 / (tolerances[i].pow(2))
        }
    }

    return matrix
}

/**
 * Solves the Discrete-Time Algebraic Riccati Equation (DARE) using iterative method.
 * P = A'PA - (A'PB)(R + B'PB)⁻¹(B'PA) + Q
 *
 * @author Tyler Veness (C++ implementation)
 * @author Zach Harel (Kotlin implementation)
 */
internal fun <States : Nat, Inputs : Nat> solveDARE(
    Ad: Matrix<States, States>,
    Bd: Matrix<States, Inputs>,
    Q: Matrix<States, States>,
    R: Matrix<Inputs, Inputs>,
    maxIter: Int = -1,
    epsilon: Double = 1e-6,
): Matrix<States, States> {
    // Initialize matrices
    var A_K = Ad.copy()

    // Calculate G_k = B * R^-1 * B^T using Cholesky decomposition
    // Equivalent to B * R.llt().solve(B.transpose())
    var G_K = Bd * R.solve(Bd.transpose)

    var H_K1 = Q.copy()
    var H_K: Matrix<States, States>

    var i = 0

    do {
        H_K = H_K1.copy()

        val W = Matrix.Companion.identity(H_K.natRows) + G_K * H_K

        val v1 = W.solve(A_K)
        val v2 = W.solve(G_K.transpose).transpose

        G_K = (G_K + A_K * v2 * A_K.transpose)

        H_K1 += v1.transpose * H_K * A_K

        A_K *= v1
    } while ((H_K1 - H_K).norm > epsilon * H_K1.norm && (maxIter < 0 || i++ < maxIter))

    return H_K1
}

/**
 * Discretizes a continuous-time system (A, B) to a discrete-time system (Ad, Bd).
 * Uses the matrix exponential (approximated with a Taylor series).
 * Ad = e^(A*dt)
 * Bd = (∫ e^(Aτ) dτ from 0 to dt) * B ≈ (I*dt + A*dt²/2! + ...) * B
 */
internal fun <States : Nat, Inputs : Nat> discretizeSystem(
    A: Matrix<States, States>,
    B: Matrix<States, Inputs>,
    dt: Double,
    taylorTerms: Int = 10,
): Pair<Matrix<States, States>, Matrix<States, Inputs>> {
    val n = A.numRows
    var Ad = Matrix.Companion.identity(A.natRows)
    var Bd_integral = Matrix.Companion.identity(B.natRows).times(dt) // Start with I*dt

    var APowerDt = A.times(dt)
    var dtPower = dt
    var factorial = 1.0

    // Taylor series for e^(A*dt) and its integral
    for (i in 1..taylorTerms) {
        // Ad term: (A*dt)^i / i!
        Ad = Ad.plus(APowerDt.times(1.0 / factorial))

        // Bd integral term: A^(i-1) * dt^(i+1) / (i+1)!
        dtPower *= dt
        factorial *= (i + 1)
        Bd_integral = Bd_integral.plus(APowerDt.times(dt / (i + 1)))

        // Prepare for next iteration
        APowerDt = APowerDt.times(A.times(dt))
        factorial *= (i + 1)
    }

    val Bd = Bd_integral.times(B)
    return Pair(Ad, Bd)
}