package dev.nextftc.units.measuretypes

import dev.nextftc.units.Measure
import dev.nextftc.units.unittypes.VoltageUnit

/**
 * Immutable measurement of electrical potential difference (voltage).
 *
 * This class represents a voltage value with a specific unit (e.g., volts, millivolts). It supports
 * arithmetic operations and conversions between different voltage units.
 */
class Voltage internal constructor(override val magnitude: Double, override val unit: VoltageUnit) :
    Measure<VoltageUnit> {
    override val baseUnitMagnitude: Double = unit.toBaseUnits(magnitude)

    /**
     * Returns a measure equivalent to this one equal to zero minus its current value.
     *
     * @return a measure equal to zero minus this measure
     */
    override fun unaryMinus(): Measure<VoltageUnit> {
        return Voltage(-magnitude, unit)
    }

    /**
     * Adds another voltage measurement to this one.
     *
     * @param other the measurement to add
     * @return a measure of the sum of both measures
     */
    override fun plus(other: Measure<out VoltageUnit>): Measure<VoltageUnit> {
        val otherInBaseUnits = other.baseUnitMagnitude
        val sumInBaseUnits = this.baseUnitMagnitude + otherInBaseUnits
        return Voltage(unit.fromBaseUnits(sumInBaseUnits), unit)
    }

    /**
     * Subtracts another voltage measurement from this one.
     *
     * @param other the measurement to subtract
     * @return a measure of the difference between the measures
     */
    override fun minus(other: Measure<out VoltageUnit>): Measure<VoltageUnit> {
        val otherInBaseUnits = other.baseUnitMagnitude
        val diffInBaseUnits = this.baseUnitMagnitude - otherInBaseUnits
        return Voltage(unit.fromBaseUnits(diffInBaseUnits), unit)
    }

    /**
     * Multiplies this voltage by a scalar unitless multiplier.
     *
     * @param multiplier the scalar multiplication factor
     * @return the scaled result
     */
    override fun times(multiplier: Double): Measure<VoltageUnit> {
        return Voltage(magnitude * multiplier, unit)
    }

    /**
     * Divides this voltage by a scalar and returns the result.
     *
     * @param divisor the value to divide by
     * @return the division result
     */
    override fun div(divisor: Double): Measure<VoltageUnit> {
        return Voltage(magnitude / divisor, unit)
    }
}
