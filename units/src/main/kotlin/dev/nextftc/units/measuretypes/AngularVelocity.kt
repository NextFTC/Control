package dev.nextftc.units.measuretypes

import dev.nextftc.units.Measure
import dev.nextftc.units.unittypes.AngleUnit
import dev.nextftc.units.unittypes.AngularVelocityUnit
import dev.nextftc.units.unittypes.PerUnit
import dev.nextftc.units.unittypes.TimeUnit

/**
 * Immutable measurement of angular velocity (angle per time).
 *
 * This class represents angular velocity values like radians per second, RPM, etc. All arithmetic
 * operations return AngularVelocity for type safety.
 */
class AngularVelocity(magnitude: Double, unit: AngularVelocityUnit) :
    Per<AngleUnit, TimeUnit>(magnitude, unit) {

    override fun unaryMinus(): AngularVelocity {
        return AngularVelocity(-magnitude, unit as AngularVelocityUnit)
    }

    override fun plus(other: Measure<out PerUnit<AngleUnit, TimeUnit>>): AngularVelocity {
        val sum = baseUnitMagnitude + other.baseUnitMagnitude
        return AngularVelocity(unit.fromBaseUnits(sum), unit as AngularVelocityUnit)
    }

    override fun minus(other: Measure<out PerUnit<AngleUnit, TimeUnit>>): AngularVelocity {
        return this + -other
    }

    override fun times(multiplier: Double): AngularVelocity {
        return AngularVelocity(magnitude * multiplier, unit as AngularVelocityUnit)
    }

    override fun div(divisor: Double): AngularVelocity {
        return AngularVelocity(magnitude / divisor, unit as AngularVelocityUnit)
    }

    /**
     * Multiplies this angular velocity by a time to get angle.
     *
     * @param time the time to multiply by
     * @return the angle traveled
     */
    operator fun times(time: Time): Angle {
        val angleUnit = (unit as AngularVelocityUnit).numerator
        val timeInCorrectUnit = time.into((unit as AngularVelocityUnit).denominator)
        return Angle(magnitude * timeInCorrectUnit, angleUnit)
    }

    /**
     * Divides this angular velocity by a time to get angular acceleration.
     *
     * @param time the time to divide by
     * @return the angular acceleration (angular velocity/time)
     */
    operator fun div(time: Time): AngularAcceleration {
        val accelerationUnit =
            dev.nextftc.units.unittypes.AngularAccelerationUnit(
                unit as AngularVelocityUnit,
                time.unit,
            )
        return AngularAcceleration(magnitude / time.magnitude, accelerationUnit)
    }
}
