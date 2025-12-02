package dev.nextftc.units.unittypes

import dev.nextftc.units.Measure
import dev.nextftc.units.Unit
import dev.nextftc.units.measuretypes.Distance

/**
 * Unit of measurement for distance/length.
 *
 * Supported units include meters (base unit), millimeters, centimeters, kilometers,
 * inches, feet, yards, and miles.
 */
class DistanceUnit(
    baseUnit: DistanceUnit?,
    toBaseConverter: (Double) -> Double,
    fromBaseConverter: (Double) -> Double,
    unitName: String,
    unitSymbol: String
) : Unit<DistanceUnit>(
    baseUnit, toBaseConverter, fromBaseConverter, unitName, unitSymbol
) {
    internal constructor(
        baseUnit: DistanceUnit,
        baseUnitEquivalent: Double,
        name: String,
        symbol: String
    ) : this(
        baseUnit,
        { x -> x * baseUnitEquivalent },
        { x -> x / baseUnitEquivalent },
        name,
        symbol
    )

    /**
     * Creates a new immutable measurement of the given magnitude in terms of this unit.
     *
     * @param magnitude the magnitude of the measurement.
     * @return the measurement object
     */
    override fun of(magnitude: Double): Distance {
        return Distance(magnitude, this)
    }

    /**
     * Creates a new immutable measurement of the given magnitude in terms of this unit's base unit.
     *
     * @param baseUnitMagnitude the magnitude in terms of the base unit
     * @return the measurement object
     */
    override fun ofBaseUnits(baseUnitMagnitude: Double): Measure<DistanceUnit> {
        return of(this.fromBaseUnits(baseUnitMagnitude))
    }

    /**
     * Combines this unit with a unit of time to create a velocity unit.
     *
     * @param time the unit of time
     * @return the combined velocity unit
     */
    override fun per(time: TimeUnit): Unit<*> {
        TODO("Velocity units not yet implemented")
    }
}

// Conversion constants
private const val MILLIMETERS_PER_METER = 1e-3
private const val CENTIMETERS_PER_METER = 1e-2
private const val KILOMETERS_PER_METER = 1e3
private const val INCHES_PER_CM = 2.54
private const val FEET_PER_INCH = 12.0
private const val YARDS_PER_FOOT = 3.0
private const val MILES_PER_FOOT = 5280.0

// Metric units
val Meters = DistanceUnit(null, { it }, { it }, "meter", "m")
val Millimeters = DistanceUnit(Meters, MILLIMETERS_PER_METER, "millimeter", "mm")
val Centimeters = DistanceUnit(Meters, CENTIMETERS_PER_METER, "centimeter", "cm")
val Kilometers = DistanceUnit(Meters, KILOMETERS_PER_METER, "kilometer", "km")

// Imperial units
val Inches = DistanceUnit(Centimeters, INCHES_PER_CM, "inch", "in")
val Feet = DistanceUnit(Inches, FEET_PER_INCH, "foot", "ft")
val Yards = DistanceUnit(Feet, YARDS_PER_FOOT, "yard", "yd")
val Miles = DistanceUnit(Feet, MILES_PER_FOOT, "mile", "mi")

