/*
 * Copyright (c) 2025 NextFTC Team
 *
 *  Use of this source code is governed by an BSD-3-clause
 *  license that can be found in the LICENSE.md file at the root of this repository or at
 *  https://opensource.org/license/bsd-3-clause.
 */

package dev.nextftc.control.model

import dev.nextftc.linalg.Matrix
import dev.nextftc.linalg.Nat
import dev.nextftc.linalg.Vector

interface Model<State : Nat, Input : Nat, Output : Nat> {
    fun derivative(state: Vector<State>, input: Vector<Input>): Vector<State>

    fun output(state: Vector<State>, input: Vector<Input>): Vector<Output>
}

@Suppress("PropertyName")
class LinearModel<State : Nat, Input : Nat, Output : Nat>(
    val A: Matrix<State, State>,
    val B: Matrix<State, Input>,
    val C: Matrix<Output, State>,
    val D: Matrix<Output, Input>,
) : Model<State, Input, Output> {
    override fun derivative(state: Vector<State>, input: Vector<Input>): Vector<State> = Vector(A * state + B * input)

    override fun output(state: Vector<State>, input: Vector<Input>): Vector<Output> = Vector(C * state + D * input)
}
