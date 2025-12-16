/*
 * Copyright (c)  NextFTC Team
 *
 *  Use of this source code is governed by an BSD-3-clause
 *  license that can be found in the LICENSE.md file at the root of this repository or at
 *  https://opensource.org/license/bsd-3-clause.
 */

package dev.nextftc.control.states

import dev.nextftc.units.Measure
import dev.nextftc.units.Unit
import dev.nextftc.units.measuretypes.Per
import dev.nextftc.units.unittypes.PerUnit
import dev.nextftc.units.unittypes.TimeUnit

/**
 * Represents a kinematic state with position, velocity, and acceleration.
 *
 * This generic interface defines the contract for kinematic state representations,
 * parameterized by the unit type [U]. It provides position, velocity (first derivative),
 * and acceleration (second derivative) properties, along with arithmetic operations
 * for combining and scaling states.
 *
 * Implementations include:
 * - [LinearState]: For linear (translational) motion using distance units
 * - [AngularState]: For angular (rotational) motion using angle units
 *
 * @param U The unit type for the position measurement (e.g., [dev.nextftc.units.unittypes.DistanceUnit],
 *          [dev.nextftc.units.unittypes.AngleUnit])
 * @param S The self type of the implementing class, used for covariant return types
 *
 * @see LinearState
 * @see AngularState
 */
interface State<U : Unit<U>, S : State<U, S>> {
    /**
     * The position component of this state.
     */
    val position: Measure<U>

    /**
     * The velocity component of this state (first time derivative of position).
     */
    val velocity: Per<U, TimeUnit>

    /**
     * The acceleration component of this state (second time derivative of position).
     */
    val acceleration: Per<PerUnit<U, TimeUnit>, TimeUnit>

    /**
     * Adds another state to this one component-wise.
     * @param other The state to add.
     * @return A new state with summed components.
     */
    operator fun plus(other: S): S

    /**
     * Subtracts another state from this one component-wise.
     * @param other The state to subtract.
     * @return A new state with the difference of components.
     */
    operator fun minus(other: S): S

    /**
     * Multiplies all components of this state by a scalar value.
     * @param scalar The value to multiply each component by.
     * @return A new state with scaled components.
     */
    operator fun times(scalar: Double): S

    /**
     * Divides all components of this state by a scalar value.
     * @param scalar The value to divide each component by.
     * @return A new state with divided components.
     */
    operator fun div(scalar: Double): S
}