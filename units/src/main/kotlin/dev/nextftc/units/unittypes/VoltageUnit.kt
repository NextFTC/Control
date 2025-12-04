package dev.nextftc.units.unittypes

import dev.nextftc.units.Measure
import dev.nextftc.units.Unit
import dev.nextftc.units.measuretypes.Voltage

/**
 * Unit of measurement for electrical potential difference (voltage).
 *
 * Supported units include volts (base unit), millivolts, kilovolts, and microvolts.
 */
class VoltageUnit(
    baseUnit: VoltageUnit?,
    toBaseConverter: (Double) -> Double,
    fromBaseConverter: (Double) -> Double,
    unitName: String,
    unitSymbol: String,
) : Unit<VoltageUnit>(baseUnit, toBaseConverter, fromBaseConverter, unitName, unitSymbol) {
    internal constructor(
        baseUnit: VoltageUnit,
        baseUnitEquivalent: Double,
        name: String,
        symbol: String,
    ) : this(
        baseUnit,
        { x -> x * baseUnitEquivalent },
        { x -> x / baseUnitEquivalent },
        name,
        symbol,
    )

    /**
     * Creates a new immutable measurement of the given magnitude in terms of this unit.
     *
     * @param magnitude the magnitude of the measurement.
     * @return the measurement object
     */
    override fun of(magnitude: Double): Voltage = Voltage(magnitude, this)

    /**
     * Creates a new immutable measurement of the given magnitude in terms of this unit's base unit.
     *
     * @param baseUnitMagnitude the magnitude in terms of the base unit
     * @return the measurement object
     */
    override fun ofBaseUnits(baseUnitMagnitude: Double): Measure<VoltageUnit> = of(this.fromBaseUnits(baseUnitMagnitude))

    /**
     * Combines this unit with a unit of time. Not typically applicable for voltage.
     *
     * @param time the unit of time
     * @return the combined unit
     */
    override fun per(time: TimeUnit): Unit<*> {
        TODO("Voltage per time units not yet implemented")
    }
}

// Conversion constants
private const val MILLIVOLTS_PER_VOLT = 1e-3
private const val MICROVOLTS_PER_VOLT = 1e-6
private const val KILOVOLTS_PER_VOLT = 1e3

// Base unit: volts
val Volts = VoltageUnit(null, { it }, { it }, "volt", "V")

// Derived units
val Millivolts = VoltageUnit(Volts, MILLIVOLTS_PER_VOLT, "millivolt", "mV")
val Microvolts = VoltageUnit(Volts, MICROVOLTS_PER_VOLT, "microvolt", "µV")
val Kilovolts = VoltageUnit(Volts, KILOVOLTS_PER_VOLT, "kilovolt", "kV")
