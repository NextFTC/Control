/*
 * Copyright (c)  NextFTC Team
 *
 *  Use of this source code is governed by an BSD-3-clause
 *  license that can be found in the LICENSE.md file at the root of this repository or at
 *  https://opensource.org/license/bsd-3-clause.
 */

package dev.nextftc.control.states

import dev.nextftc.units.measuretypes.Angle
import dev.nextftc.units.measuretypes.AngularAcceleration
import dev.nextftc.units.measuretypes.AngularVelocity
import dev.nextftc.units.unittypes.AngleUnit
import dev.nextftc.units.unittypes.radians
import dev.nextftc.units.unittypes.radiansPerSecond
import dev.nextftc.units.unittypes.radiansPerSecondSquared

/**
 * Represents the kinematic state of an object in angular (rotational) motion.
 *
 * This data class encapsulates angle, angular velocity, and angular acceleration for rotational motion,
 * providing a convenient way to track and manipulate rotational state in control systems.
 *
 * @property angle The angular position/orientation of the object.
 * @property velocity The angular velocity of the object.
 * @property acceleration The angular acceleration of the object.
 * @constructor Creates an AngularState with the specified angle, velocity, and acceleration.
 *              All parameters default to zero.
 */
data class AngularState @JvmOverloads constructor(
    val angle: Angle = 0.0.radians,
    override val velocity: AngularVelocity = 0.0.radiansPerSecond,
    override val acceleration: AngularAcceleration = 0.0.radiansPerSecondSquared,
) : State<AngleUnit, AngularState> {
    /**
     * The angular position of this state. Alias for [angle].
     */
    override val position: Angle get() = angle
    /**
     * Creates an AngularState from raw double values.
     *
     * **All values are interpreted as radians-based units:**
     * - [angle] in **radians**
     * - [velocity] in **radians per second**
     * - [acceleration] in **radians per second squared**
     *
     * @param angle The angle in radians.
     * @param velocity The angular velocity in radians per second.
     * @param acceleration The angular acceleration in radians per second squared.
     */
    constructor(
        angle: Double,
        velocity: Double,
        acceleration: Double,
    ) : this(
        angle.radians,
        velocity.radiansPerSecond,
        acceleration.radiansPerSecondSquared,
    )

    /**
     * Adds two angular states component-wise.
     * @param other The state to add to this state.
     * @return A new [AngularState] with summed components.
     */
    override operator fun plus(other: AngularState): AngularState = AngularState(
        angle + other.angle,
        velocity + other.velocity,
        acceleration + other.acceleration,
    )

    /**
     * Subtracts another angular state from this one component-wise.
     * @param other The state to subtract from this state.
     * @return A new [AngularState] with the difference of components.
     */
    override operator fun minus(other: AngularState): AngularState = AngularState(
        angle - other.angle,
        velocity - other.velocity,
        acceleration - other.acceleration,
    )

    /**
     * Multiplies all components of this state by a scalar value.
     * @param scalar The value to multiply each component by.
     * @return A new [AngularState] with scaled components.
     */
    override operator fun times(scalar: Double): AngularState = AngularState(
        angle * scalar,
        velocity * scalar,
        acceleration * scalar,
    )

    /**
     * Divides all components of this state by a scalar value.
     * @param scalar The value to divide each component by.
     * @return A new [AngularState] with divided components.
     */
    override operator fun div(scalar: Double): AngularState = AngularState(
        angle / scalar,
        velocity / scalar,
        acceleration / scalar,
    )

    companion object {
        /** An [AngularState] with all components set to zero. */
        @JvmField val ZERO = AngularState()
    }
}