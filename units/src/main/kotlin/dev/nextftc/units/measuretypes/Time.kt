package dev.nextftc.units.measuretypes

import dev.nextftc.units.Measure
import dev.nextftc.units.unittypes.TimeUnit
import dev.nextftc.units.unittypes.durationUnit
import kotlin.time.Duration
import kotlin.time.DurationUnit
import kotlin.time.toDuration

class Time internal constructor(
    private val duration: Duration,
    override val unit: TimeUnit
) : Measure<TimeUnit> {
    override val magnitude = duration.toDouble(unit.durationUnit ?: DurationUnit.MILLISECONDS)

    override val baseUnitMagnitude: Double = duration.toDouble(unit.baseUnit.durationUnit ?: DurationUnit.MILLISECONDS)

    /**
     * Returns a measure equivalent to this one equal to zero minus its current value. For non-linear
     * unit types like temperature, the zero point is treated as the zero value of the base unit (eg
     * Kelvin). In effect, this means code like `Celsius.of(10).unaryMinus()` returns a value
     * equivalent to -10 Kelvin, and *not* -10° Celsius.
     *
     * @return a measure equal to zero minus this measure
     */
    override fun unaryMinus(): Measure<TimeUnit> {
        return this.times(-1.0)
    }

    /**
     * Adds another measure of the same unit type to this one.
     *
     * @param other the measurement to add
     * @return a measure of the sum of both measures
     */
    override fun plus(other: Measure<out TimeUnit>): Measure<TimeUnit> {
        val baseUnitDuration = baseUnit.durationUnit ?: DurationUnit.SECONDS
        val otherDuration = other.baseUnitMagnitude.toDuration(baseUnitDuration)

        return Time(this.duration + otherDuration, unit)
    }

    /**
     * Subtracts another measure of the same unit type from this one.
     *
     * @param other the measurement to subtract
     * @return a measure of the difference between the measures
     */
    override fun minus(other: Measure<out TimeUnit>): Measure<TimeUnit> {
        val baseUnitDuration = baseUnit.durationUnit ?: DurationUnit.SECONDS
        val otherDuration = other.baseUnitMagnitude.toDuration(baseUnitDuration)

        return Time(this.duration - otherDuration, unit)
    }

    /**
     * Multiplies this measure by a scalar unitless multiplier.
     *
     * @param multiplier the scalar multiplication factor
     * @return the scaled result
     */
    override fun times(multiplier: Double): Measure<TimeUnit> {
        return Time(this.duration * multiplier, unit)
    }

    /**
     * Divides this measure by a scalar and returns the result.
     *
     * @param divisor the value to divide by
     * @return the division result
     */
    override fun div(divisor: Double): Measure<TimeUnit> {
        return Time(this.duration / divisor, unit)
    }
}