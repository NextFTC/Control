package dev.nextftc.units.unittypes

import dev.nextftc.units.Measure
import dev.nextftc.units.Unit
import dev.nextftc.units.measuretypes.Mass

/**
 * Unit of measurement for mass.
 *
 * Supported units include kilograms (base unit), grams, milligrams, metric tons,
 * pounds, and ounces.
 */
class MassUnit(
    baseUnit: MassUnit?,
    toBaseConverter: (Double) -> Double,
    fromBaseConverter: (Double) -> Double,
    unitName: String,
    unitSymbol: String
) : Unit<MassUnit>(
    baseUnit, toBaseConverter, fromBaseConverter, unitName, unitSymbol
) {
    internal constructor(
        baseUnit: MassUnit,
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

    override fun of(magnitude: Double): Mass {
        return Mass(magnitude, this)
    }

    override fun ofBaseUnits(baseUnitMagnitude: Double): Measure<MassUnit> {
        return of(this.fromBaseUnits(baseUnitMagnitude))
    }

    override fun per(time: TimeUnit): Unit<*> {
        TODO("Mass flow rate units not yet implemented")
    }
}

// Conversion constants
private const val GRAMS_PER_KILOGRAM = 1e-3
private const val MILLIGRAMS_PER_KILOGRAM = 1e-6
private const val METRIC_TONS_PER_KILOGRAM = 1000.0
private const val POUNDS_PER_KILOGRAM = 0.45359237
private const val OUNCES_PER_POUND = 1.0 / 16.0

// Metric units
val Kilograms = MassUnit(null, { it }, { it }, "kilogram", "kg")
val Grams = MassUnit(Kilograms, GRAMS_PER_KILOGRAM, "gram", "g")
val Milligrams = MassUnit(Kilograms, MILLIGRAMS_PER_KILOGRAM, "milligram", "mg")
val MetricTons = MassUnit(Kilograms, METRIC_TONS_PER_KILOGRAM, "metric ton", "t")

// Imperial units
val Pounds = MassUnit(Kilograms, POUNDS_PER_KILOGRAM, "pound", "lb")
val Ounces = MassUnit(Pounds, OUNCES_PER_POUND, "ounce", "oz")
