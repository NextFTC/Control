package dev.nextftc.units.measuretypes

import dev.nextftc.units.Measure
import dev.nextftc.units.unittypes.ForceUnit

/**
 * Immutable measurement of force.
 *
 * This class represents force values like newtons, pounds-force, etc. It supports arithmetic
 * operations and conversions between different force units.
 */
class Force
internal constructor(
    override val magnitude: Double,
    override val unit: ForceUnit,
) : Measure<ForceUnit> {
    override val baseUnitMagnitude: Double = unit.toBaseUnits(magnitude)

    override fun unaryMinus(): Force = Force(-magnitude, unit)

    override fun plus(other: Measure<out ForceUnit>): Force {
        val otherInBaseUnits = other.baseUnitMagnitude
        val sumInBaseUnits = this.baseUnitMagnitude + otherInBaseUnits
        return Force(unit.fromBaseUnits(sumInBaseUnits), unit)
    }

    override fun minus(other: Measure<out ForceUnit>): Force {
        val otherInBaseUnits = other.baseUnitMagnitude
        val diffInBaseUnits = this.baseUnitMagnitude - otherInBaseUnits
        return Force(unit.fromBaseUnits(diffInBaseUnits), unit)
    }

    override fun times(multiplier: Double): Force = Force(magnitude * multiplier, unit)

    override fun div(divisor: Double): Force = Force(magnitude / divisor, unit)
}
