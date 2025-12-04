package dev.nextftc.units.measuretypes

import dev.nextftc.units.Measure
import dev.nextftc.units.unittypes.AngleUnit
import dev.nextftc.units.unittypes.AngularAccelerationUnit
import dev.nextftc.units.unittypes.AngularVelocityUnit
import dev.nextftc.units.unittypes.PerUnit
import dev.nextftc.units.unittypes.TimeUnit

/**
 * Immutable measurement of angular acceleration (angular velocity per time, or angle per time
 * squared).
 *
 * This class represents angular acceleration values like radians per second squared. All arithmetic
 * operations return AngularAcceleration for type safety.
 */
class AngularAcceleration(magnitude: Double, unit: AngularAccelerationUnit) :
    Per<PerUnit<AngleUnit, TimeUnit>, TimeUnit>(magnitude, unit) {

    override fun unaryMinus(): AngularAcceleration {
        return AngularAcceleration(-magnitude, unit as AngularAccelerationUnit)
    }

    override fun plus(
        other: Measure<out PerUnit<PerUnit<AngleUnit, TimeUnit>, TimeUnit>>
    ): AngularAcceleration {
        val sum = baseUnitMagnitude + other.baseUnitMagnitude
        return AngularAcceleration(unit.fromBaseUnits(sum), unit as AngularAccelerationUnit)
    }

    override fun minus(
        other: Measure<out PerUnit<PerUnit<AngleUnit, TimeUnit>, TimeUnit>>
    ): AngularAcceleration {
        return this + -other
    }

    override fun times(multiplier: Double): AngularAcceleration {
        return AngularAcceleration(magnitude * multiplier, unit as AngularAccelerationUnit)
    }

    override fun div(divisor: Double): AngularAcceleration {
        return AngularAcceleration(magnitude / divisor, unit as AngularAccelerationUnit)
    }

    /**
     * Multiplies this angular acceleration by a time to get angular velocity.
     *
     * @param time the time to multiply by
     * @return the angular velocity achieved
     */
    operator fun times(time: Time): AngularVelocity {
        val velocityUnit: AngularVelocityUnit =
            when (unit.numerator) {
                is AngularVelocityUnit -> unit.numerator
                else -> AngularVelocityUnit(unit.numerator.numerator, unit.numerator.denominator)
            }
        val timeInCorrectUnit = time.into(unit.denominator)
        return AngularVelocity(magnitude * timeInCorrectUnit, velocityUnit)
    }
}
