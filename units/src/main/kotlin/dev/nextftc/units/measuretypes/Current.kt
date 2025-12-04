package dev.nextftc.units.measuretypes

import dev.nextftc.units.Measure
import dev.nextftc.units.unittypes.CurrentUnit

/**
 * Immutable measurement of electrical current.
 *
 * This class represents current values like amperes, milliamperes, etc.
 * It supports arithmetic operations and conversions between different current units.
 */
class Current internal constructor(
    override val magnitude: Double,
    override val unit: CurrentUnit
) : Measure<CurrentUnit> {
    override val baseUnitMagnitude: Double = unit.toBaseUnits(magnitude)

    override fun unaryMinus(): Current {
        return Current(-magnitude, unit)
    }

    override fun plus(other: Measure<out CurrentUnit>): Current {
        val otherInBaseUnits = other.baseUnitMagnitude
        val sumInBaseUnits = this.baseUnitMagnitude + otherInBaseUnits
        return Current(unit.fromBaseUnits(sumInBaseUnits), unit)
    }

    override fun minus(other: Measure<out CurrentUnit>): Current {
        val otherInBaseUnits = other.baseUnitMagnitude
        val diffInBaseUnits = this.baseUnitMagnitude - otherInBaseUnits
        return Current(unit.fromBaseUnits(diffInBaseUnits), unit)
    }

    override fun times(multiplier: Double): Current {
        return Current(magnitude * multiplier, unit)
    }

    override fun div(divisor: Double): Current {
        return Current(magnitude / divisor, unit)
    }
}

