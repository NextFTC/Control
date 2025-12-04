package dev.nextftc.units.unittypes

import dev.nextftc.units.Measure

/**
 * Unit representing angular velocity (angle per time).
 *
 * Common examples:
 * - Radians per second (rad/s)
 * - Degrees per second (deg/s)
 * - Rotations per minute (RPM)
 * - Revolutions per second (Hz when measuring rotation frequency)
 *
 * @param angle the angle unit (numerator)
 * @param time the time unit (denominator)
 */
class AngularVelocityUnit(angle: AngleUnit, time: TimeUnit) :
    PerUnit<AngleUnit, TimeUnit>(angle, time) {

    override fun of(magnitude: Double): Measure<PerUnit<AngleUnit, TimeUnit>> {
        return dev.nextftc.units.measuretypes.AngularVelocity(magnitude, this)
    }

    override fun ofBaseUnits(baseUnitMagnitude: Double): Measure<PerUnit<AngleUnit, TimeUnit>> {
        return of(this.fromBaseUnits(baseUnitMagnitude))
    }
}

// Common angular velocity units
val RadiansPerSecond = AngularVelocityUnit(Radians, Seconds)
val DegreesPerSecond = AngularVelocityUnit(Degrees, Seconds)
val RotationsPerMinute = AngularVelocityUnit(Rotations, Minutes)
val RotationsPerSecond = AngularVelocityUnit(Rotations, Seconds)
