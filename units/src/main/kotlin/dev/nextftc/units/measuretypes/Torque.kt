package dev.nextftc.units.measuretypes

import dev.nextftc.units.Measure
import dev.nextftc.units.unittypes.TorqueUnit

/**
 * Immutable measurement of torque.
 *
 * This class represents torque values like newton-meters, pound-feet, etc.
 * It supports arithmetic operations and conversions between different torque units.
 */
class Torque internal constructor(
    override val magnitude: Double,
    override val unit: TorqueUnit
) : Measure<TorqueUnit> {
    override val baseUnitMagnitude: Double = unit.toBaseUnits(magnitude)

    override fun unaryMinus(): Torque {
        return Torque(-magnitude, unit)
    }

    override fun plus(other: Measure<out TorqueUnit>): Torque {
        val sum = baseUnitMagnitude + other.baseUnitMagnitude
        return Torque(unit.fromBaseUnits(sum), unit)
    }

    override fun minus(other: Measure<out TorqueUnit>): Torque {
        val diff = baseUnitMagnitude - other.baseUnitMagnitude
        return Torque(unit.fromBaseUnits(diff), unit)
    }

    override fun times(multiplier: Double): Torque {
        return Torque(magnitude * multiplier, unit)
    }

    override fun div(divisor: Double): Torque {
        return Torque(magnitude / divisor, unit)
    }
}
