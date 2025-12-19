@file:Suppress("ktlint:standard:property-naming")

package dev.nextftc.control.feedback

import dev.nextftc.control.model.LinearModel
import dev.nextftc.linalg.Matrix
import dev.nextftc.linalg.N1
import dev.nextftc.linalg.Nat
import dev.nextftc.linalg.Vector
import kotlin.math.pow

/**
 * A Linear Quadratic Regulator (LQR) for controlling a system modeled by state-space equations.
 *
 * LQR is a form of optimal control that finds the best control input to apply to a system
 * by minimizing a quadratic cost function. The cost function balances two competing goals:
 * 1.  **State Error**: How far the system is from its desired target state (penalized by the `Q` matrix).
 * 2.  **Control Effort**: How much energy or effort is used to control the system (penalized by the `R` matrix).
 *
 * The controller computes the optimal control input `u` using a simple state-feedback law: `u = -Kx`,
 * where `x` is the system's state error and `K` is the optimal gain matrix.
 **
 * Thank you to Tyler Veness and WPILib!
 *
 * @param A The state matrix.
 * @param B The input matrix.
 * @param Q The state cost matrix.
 * @param R The control cost matrix.
 * @param dt The time step for the discrete-time model (your loop time)
 *
 * @see <a href="https://en.wikipedia.org/wiki/Linear%E2%80%93quadratic_regulator">LQR on Wikipedia</a>
 * @see <a href="https://docs.wpilib.org/en/stable/docs/software/advanced-controls/state-space/state-space-intro.html#the-linear-quadratic-regulator">LQR in WPILib</a>
 */
class LQRController<States : Nat, Inputs : Nat, Outputs : Nat> @JvmOverloads constructor(
    A: Matrix<States, States>,
    B: Matrix<States, Inputs>,
    Q: Matrix<States, States>,
    R: Matrix<Inputs, Inputs>,
    private val dt: Double = 0.05,
) {
    private val K: Matrix<Inputs, States>

    init {
        require(dt > 0) { "Time step (dt) must be positive" }
        val (Ad, Bd) = discretizeSystem(A, B, dt)
        val (_, K) = computeLQRGain(Ad, Bd, Q, R)
        this.K = K
    }

    /**
     * Constructs a controller with the given coefficient matrices.
     *
     * @param A the state matrix
     * @param B the input matrix
     * @param Qelems the maximum state error for each state dimension
     * @param Relems the maximum control effort for each control input dimension
     * @param dt the time step for the discrete-time model (your loop time)
     */
    @JvmOverloads constructor(
        A: Matrix<States, States>,
        B: Matrix<States, Inputs>,
        Qelems: Vector<States>,
        Relems: Vector<Inputs>,
        dt: Double = 0.05,
    ) : this(A, B, makeBrysonMatrix(Qelems), makeBrysonMatrix(Relems), dt)

    /**
     * Constructs a controller with the given plant model and cost matrices.
     *
     * @param plant the plant model
     * @param Qelems the maximum state error for each state dimension
     * @param Relems the maximum control effort for each control input dimension
     * @param dt the time step for the discrete-time model (your loop time)
     */
    @JvmOverloads constructor(
        plant: LinearModel<States, Inputs, Outputs>,
        Qelems: Vector<States>,
        Relems: Vector<Inputs>,
        dt: Double = 0.05,
    ) : this(plant.A, plant.B, Qelems, Relems, dt)

    /**
     * Calculates the optimal control input to correct for the given state error.
     *
     * @param error The current state error of the system, represented as a Matrix.
     * @return The calculated optimal control input as a Matrix.
     * */
    fun update(error: Matrix<States, N1>): Matrix<Inputs, N1> = -K * error
}

internal fun <N : Nat> makeBrysonMatrix(tolerances: Vector<N>): Matrix<N, N> {
    val matrix = Matrix.zero(tolerances.natRows, tolerances.natRows)

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

        val W = Matrix.identity(H_K.natRows) + G_K * H_K

        val v1 = W.solve(A_K)
        val v2 = W.solve(G_K.transpose).transpose

        G_K = (G_K + A_K * v2 * A_K.transpose)

        H_K1 += v1.transpose * H_K * A_K

        A_K *= v1
    } while ((H_K1 - H_K).norm > epsilon * H_K1.norm && (maxIter < 0 || i++ < maxIter))

    return H_K1
}

/**
 * Computes the optimal gain matrix K using [solveDARE].
 *
 * @return Pair of DARE solution X and K.
 */
internal fun <States : Nat, Inputs : Nat> computeLQRGain(
    Ad: Matrix<States, States>,
    Bd: Matrix<States, Inputs>,
    Q: Matrix<States, States>,
    R: Matrix<Inputs, Inputs>,
    maxIter: Int = -1,
    epsilon: Double = 1e-6,
): Pair<Matrix<States, States>, Matrix<Inputs, States>> {
    val X = solveDARE(Ad, Bd, Q, R, maxIter, epsilon)

    val btx = Bd.transpose * X
    val btxb = btx * Bd
    val K = (R + btxb).inverse * btx * Ad

    return X to K
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
    var Ad = Matrix.identity(A.natRows)
    var Bd_integral = Matrix.identity(B.natRows).times(dt) // Start with I*dt

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
