/*
 * Copyright (c) 2025 NextFTC Team
 *
 *  Use of this source code is governed by an BSD-3-clause
 *  license that can be found in the LICENSE.md file at the root of this repository or at
 *  https://opensource.org/license/bsd-3-clause.
 */

package dev.nextftc.control.model

import dev.nextftc.linalg.Nat
import dev.nextftc.linalg.SizedMatrix
import dev.nextftc.linalg.SizedVector

interface Model<State : Nat, Input : Nat, Output : Nat> {
    fun derivative(state: SizedVector<State>, input: SizedVector<Input>): SizedVector<State>

    fun output(state: SizedVector<State>, input: SizedVector<Input>): SizedVector<Output>
}

@Suppress("PropertyName")
class LinearModel<State : Nat, Input : Nat, Output : Nat>(
    val A: SizedMatrix<State, State>,
    val B: SizedMatrix<State, Input>,
    val C: SizedMatrix<Output, State>,
    val D: SizedMatrix<Output, Input>,
) : Model<State, Input, Output> {
    override fun derivative(
        state: SizedVector<State>,
        input: SizedVector<Input>,
    ): SizedVector<State> = SizedVector(A * state + B * input)

    override fun output(state: SizedVector<State>, input: SizedVector<Input>): SizedVector<Output> =
        SizedVector(C * state + D * input)
}
