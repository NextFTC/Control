package dev.nextftc.units.unittypes

import dev.nextftc.units.Measure
import dev.nextftc.units.Unit
import dev.nextftc.units.measuretypes.Angle

/**
 * Unit of measurement for angles.
 *
 * Supported units include radians (base unit), degrees, rotations (full circles),
 * and gradians.
 */
class AngleUnit(
    baseUnit: AngleUnit?,
    toBaseConverter: (Double) -> Double,
    fromBaseConverter: (Double) -> Double,
    unitName: String,
    unitSymbol: String
) : Unit<AngleUnit>(
    baseUnit, toBaseConverter, fromBaseConverter, unitName, unitSymbol
) {
    internal constructor(
        baseUnit: AngleUnit,
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
    override fun of(magnitude: Double): Angle {
        return Angle(magnitude, this)
    }

    /**
     * Creates a new immutable measurement of the given magnitude in terms of this unit's base unit.
     *
     * @param baseUnitMagnitude the magnitude in terms of the base unit
     * @return the measurement object
     */
    override fun ofBaseUnits(baseUnitMagnitude: Double): Measure<AngleUnit> {
        return of(this.fromBaseUnits(baseUnitMagnitude))
    }

    /**
     * Combines this unit with a unit of time to create an angular velocity unit.
     *
     * @param time the unit of time
     * @return the combined angular velocity unit
     */
    override fun per(time: TimeUnit): Unit<*> {
        TODO("Angular velocity units not yet implemented")
    }
}

// Conversion constants
private const val DEGREES_PER_RADIAN = Math.PI / 180.0
private const val ROTATIONS_PER_RADIAN = 2.0 * Math.PI
private const val GRADIANS_PER_RADIAN = Math.PI / 200.0

// Base unit: radians
val Radians = AngleUnit(null, { it }, { it }, "radian", "rad")

// Derived units
val Degrees = AngleUnit(Radians, DEGREES_PER_RADIAN, "degree", "deg")
val Rotations = AngleUnit(Radians, ROTATIONS_PER_RADIAN, "rotation", "rot")
val Gradians = AngleUnit(Radians, GRADIANS_PER_RADIAN, "gradian", "grad")

