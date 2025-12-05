@file:JvmName("Matrices")

package dev.nextftc.linalg

import org.ejml.simple.SimpleMatrix

internal operator fun SimpleMatrix.unaryMinus() = this.times(-1.0)
internal operator fun SimpleMatrix.times(other: SimpleMatrix): SimpleMatrix = this.mult(other)
internal operator fun SimpleMatrix.times(other: Double): SimpleMatrix = this.scale(other)
internal operator fun Double.times(other: SimpleMatrix): SimpleMatrix = other.times(this)
