package dev.nextftc.units.unittypes

import dev.nextftc.units.Measure

/**
 * Unit representing linear acceleration (velocity per time, or distance per time squared).
 *
 * Common examples:
 * - Meters per second squared (m/s²)
 * - Feet per second squared (ft/s²)
 * - Standard gravity (g ≈ 9.80665 m/s²)
 *
 * @param velocity the velocity unit (numerator)
 * @param time the time unit (denominator)
 */
class LinearAccelerationUnit(velocity: LinearVelocityUnit, time: TimeUnit) :
    PerUnit<PerUnit<DistanceUnit, TimeUnit>, TimeUnit>(velocity, time) {

    override fun of(
        magnitude: Double
    ): Measure<PerUnit<PerUnit<DistanceUnit, TimeUnit>, TimeUnit>> {
        return dev.nextftc.units.measuretypes.LinearAcceleration(magnitude, this)
    }

    override fun ofBaseUnits(
        baseUnitMagnitude: Double
    ): Measure<PerUnit<PerUnit<DistanceUnit, TimeUnit>, TimeUnit>> {
        return of(this.fromBaseUnits(baseUnitMagnitude))
    }
}

// Common linear acceleration units
val MetersPerSecondSquared = LinearAccelerationUnit(MetersPerSecond, Seconds)
val FeetPerSecondSquared = LinearAccelerationUnit(FeetPerSecond, Seconds)

// Standard gravity constant (approximately 9.80665 m/s²)
const val STANDARD_GRAVITY_MPS2 = 9.80665
