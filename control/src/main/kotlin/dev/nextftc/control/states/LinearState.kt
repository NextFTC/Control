/*
 * Copyright (c)  NextFTC Team
 *
 *  Use of this source code is governed by an BSD-3-clause
 *  license that can be found in the LICENSE.md file at the root of this repository or at
 *  https://opensource.org/license/bsd-3-clause.
 */

package dev.nextftc.control.states

import dev.nextftc.units.measuretypes.Distance
import dev.nextftc.units.measuretypes.LinearAcceleration
import dev.nextftc.units.measuretypes.LinearVelocity
import dev.nextftc.units.unittypes.DistanceUnit
import dev.nextftc.units.unittypes.inches
import dev.nextftc.units.unittypes.inchesPerSecond
import dev.nextftc.units.unittypes.inchesPerSecondSquared

/**
 * Represents the kinematic state of an object in linear (translational) motion.
 *
 * This data class encapsulates position, velocity, and acceleration for linear motion,
 * providing a convenient way to track and manipulate motion state in control systems.
 *
 * @property position The linear position/displacement of the object.
 * @property velocity The linear velocity of the object.
 * @property acceleration The linear acceleration of the object.
 * @constructor Creates a LinearState with the specified position, velocity, and acceleration.
 *              All parameters default to zero.
 */
data class LinearState @JvmOverloads constructor(
    override val position: Distance = 0.0.inches,
    override val velocity: LinearVelocity = 0.0.inchesPerSecond,
    override val acceleration: LinearAcceleration = 0.0.inchesPerSecondSquared,
) : State<DistanceUnit, LinearState> {
    /**
     * Creates a LinearState from raw double values.
     *
     * **All values are interpreted as inches-based units:**
     * - [position] in **inches**
     * - [velocity] in **inches per second**
     * - [acceleration] in **inches per second squared**
     *
     * @param position The position in inches.
     * @param velocity The velocity in inches per second.
     * @param acceleration The acceleration in inches per second squared.
     */
    constructor(
        position: Double,
        velocity: Double,
        acceleration: Double,
    ) : this(
        position.inches,
        velocity.inchesPerSecond,
        acceleration.inchesPerSecondSquared,
    )

    /**
     * Adds two linear states component-wise.
     * @param other The state to add to this state.
     * @return A new [LinearState] with summed components.
     */
    override operator fun plus(other: LinearState): LinearState = LinearState(
        position + other.position,
        velocity + other.velocity,
        acceleration + other.acceleration,
    )

    /**
     * Subtracts another linear state from this one component-wise.
     * @param other The state to subtract from this state.
     * @return A new [LinearState] with the difference of components.
     */
    override operator fun minus(other: LinearState): LinearState = LinearState(
        position - other.position,
        velocity - other.velocity,
        acceleration - other.acceleration,
    )

    /**
     * Multiplies all components of this state by a scalar value.
     * @param scalar The value to multiply each component by.
     * @return A new [LinearState] with scaled components.
     */
    override operator fun times(scalar: Double): LinearState = LinearState(
        position * scalar,
        velocity * scalar,
        acceleration * scalar,
    )

    /**
     * Divides all components of this state by a scalar value.
     * @param scalar The value to divide each component by.
     * @return A new [LinearState] with divided components.
     */
    override operator fun div(scalar: Double): LinearState = LinearState(
        position / scalar,
        velocity / scalar,
        acceleration / scalar,
    )

    companion object {
        /** A [LinearState] with all components set to zero. */
        @JvmField val ZERO = LinearState()
    }
}