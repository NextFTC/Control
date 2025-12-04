package dev.nextftc.units.unittypes

import dev.nextftc.units.measuretypes.LinearVelocity

/**
 * Unit representing linear velocity (distance per time).
 *
 * Common examples:
 * - Meters per second (m/s)
 * - Miles per hour (mph)
 * - Kilometers per hour (km/h)
 * - Feet per second (ft/s)
 *
 * @param distance the distance unit (numerator)
 * @param time the time unit (denominator)
 */
class LinearVelocityUnit(
    distance: DistanceUnit,
    time: TimeUnit,
) : PerUnit<DistanceUnit, TimeUnit>(distance, time) {
    override fun of(magnitude: Double): LinearVelocity = LinearVelocity(magnitude, this)

    override fun ofBaseUnits(baseUnitMagnitude: Double): LinearVelocity = of(this.fromBaseUnits(baseUnitMagnitude))
}

// Common linear velocity units
val MetersPerSecond = LinearVelocityUnit(Meters, Seconds)
val KilometersPerHour = LinearVelocityUnit(Kilometers, Hours)
val MilesPerHour = LinearVelocityUnit(Miles, Hours)
val FeetPerSecond = LinearVelocityUnit(Feet, Seconds)
