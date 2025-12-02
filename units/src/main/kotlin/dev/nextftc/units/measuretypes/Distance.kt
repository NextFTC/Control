package dev.nextftc.units.measuretypes

import dev.nextftc.units.Measure
import dev.nextftc.units.unittypes.DistanceUnit

/**
 * Immutable measurement of distance/length.
 *
 * This class represents a distance value with a specific unit (e.g., meters, feet).
 * It supports arithmetic operations and conversions between different distance units.
 */
class Distance internal constructor(
    override val magnitude: Double,
    override val unit: DistanceUnit
) : Measure<DistanceUnit> {
    override val baseUnitMagnitude: Double = unit.toBaseUnits(magnitude)

    /**
     * Returns a measure equivalent to this one equal to zero minus its current value.
     *
     * @return a measure equal to zero minus this measure
     */
    override fun unaryMinus(): Measure<DistanceUnit> {
        return Distance(-magnitude, unit)
    }

    /**
     * Adds another distance measurement to this one.
     *
     * @param other the measurement to add
     * @return a measure of the sum of both measures
     */
    override fun plus(other: Measure<out DistanceUnit>): Measure<DistanceUnit> {
        val otherInBaseUnits = other.baseUnitMagnitude
        val sumInBaseUnits = this.baseUnitMagnitude + otherInBaseUnits
        return Distance(unit.fromBaseUnits(sumInBaseUnits), unit)
    }

    /**
     * Subtracts another distance measurement from this one.
     *
     * @param other the measurement to subtract
     * @return a measure of the difference between the measures
     */
    override fun minus(other: Measure<out DistanceUnit>): Measure<DistanceUnit> {
        val otherInBaseUnits = other.baseUnitMagnitude
        val diffInBaseUnits = this.baseUnitMagnitude - otherInBaseUnits
        return Distance(unit.fromBaseUnits(diffInBaseUnits), unit)
    }

    /**
     * Multiplies this distance by a scalar unitless multiplier.
     *
     * @param multiplier the scalar multiplication factor
     * @return the scaled result
     */
    override fun times(multiplier: Double): Measure<DistanceUnit> {
        return Distance(magnitude * multiplier, unit)
    }

    /**
     * Divides this distance by a scalar and returns the result.
     *
     * @param divisor the value to divide by
     * @return the division result
     */
    override fun div(divisor: Double): Measure<DistanceUnit> {
        return Distance(magnitude / divisor, unit)
    }
}

