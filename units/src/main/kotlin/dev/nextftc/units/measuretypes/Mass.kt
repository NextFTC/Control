package dev.nextftc.units.measuretypes

import dev.nextftc.units.Measure
import dev.nextftc.units.unittypes.MassUnit

/**
 * Immutable measurement of mass.
 *
 * This class represents mass values like kilograms, pounds, etc. It supports arithmetic operations
 * and conversions between different mass units.
 */
class Mass
internal constructor(
    override val magnitude: Double,
    override val unit: MassUnit,
) : Measure<MassUnit> {
    override val baseUnitMagnitude: Double = unit.toBaseUnits(magnitude)

    override fun unaryMinus(): Mass = Mass(-magnitude, unit)

    override fun plus(other: Measure<out MassUnit>): Mass {
        val otherInBaseUnits = other.baseUnitMagnitude
        val sumInBaseUnits = this.baseUnitMagnitude + otherInBaseUnits
        return Mass(unit.fromBaseUnits(sumInBaseUnits), unit)
    }

    override fun minus(other: Measure<out MassUnit>): Mass {
        val otherInBaseUnits = other.baseUnitMagnitude
        val diffInBaseUnits = this.baseUnitMagnitude - otherInBaseUnits
        return Mass(unit.fromBaseUnits(diffInBaseUnits), unit)
    }

    override fun times(multiplier: Double): Mass = Mass(magnitude * multiplier, unit)

    override fun div(divisor: Double): Mass = Mass(magnitude / divisor, unit)
}
