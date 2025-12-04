package dev.nextftc.units.measuretypes

import dev.nextftc.units.Measure
import dev.nextftc.units.unittypes.PowerUnit

/**
 * Immutable measurement of power.
 *
 * This class represents power values like watts, kilowatts, horsepower, etc.
 * It supports arithmetic operations and conversions between different power units.
 */
class Power internal constructor(
    override val magnitude: Double,
    override val unit: PowerUnit
) : Measure<PowerUnit> {
    override val baseUnitMagnitude: Double = unit.toBaseUnits(magnitude)

    override fun unaryMinus(): Power {
        return Power(-magnitude, unit)
    }

    override fun plus(other: Measure<out PowerUnit>): Power {
        val otherInBaseUnits = other.baseUnitMagnitude
        val sumInBaseUnits = this.baseUnitMagnitude + otherInBaseUnits
        return Power(unit.fromBaseUnits(sumInBaseUnits), unit)
    }

    override fun minus(other: Measure<out PowerUnit>): Power {
        val otherInBaseUnits = other.baseUnitMagnitude
        val diffInBaseUnits = this.baseUnitMagnitude - otherInBaseUnits
        return Power(unit.fromBaseUnits(diffInBaseUnits), unit)
    }

    override fun times(multiplier: Double): Power {
        return Power(magnitude * multiplier, unit)
    }

    override fun div(divisor: Double): Power {
        return Power(magnitude / divisor, unit)
    }
}

