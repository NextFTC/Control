package dev.nextftc.units.measuretypes

import dev.nextftc.units.Measure
import dev.nextftc.units.unittypes.EnergyUnit
import dev.nextftc.units.unittypes.PerUnit
import dev.nextftc.units.unittypes.TimeUnit

/**
 * Immutable measurement of energy.
 *
 * This class represents energy values like joules, kilojoules, watt-hours, etc. It supports
 * arithmetic operations and conversions between different energy units.
 */
class Energy
internal constructor(
    override val magnitude: Double,
    override val unit: EnergyUnit,
) : Measure<EnergyUnit> {
    override val baseUnitMagnitude: Double = unit.toBaseUnits(magnitude)

    override fun unaryMinus(): Energy = Energy(-magnitude, unit)

    override fun plus(other: Measure<out EnergyUnit>): Energy {
        val sum = baseUnitMagnitude + other.baseUnitMagnitude
        return Energy(unit.fromBaseUnits(sum), unit)
    }

    override fun minus(other: Measure<out EnergyUnit>): Energy {
        val diff = baseUnitMagnitude - other.baseUnitMagnitude
        return Energy(unit.fromBaseUnits(diff), unit)
    }

    override fun times(multiplier: Double): Energy = Energy(magnitude * multiplier, unit)

    override fun div(divisor: Double): Energy = Energy(magnitude / divisor, unit)

    /**
     * Divides this energy by a time to get energy rate (energy per time).
     *
     * @param time the time to divide by
     * @return the energy rate (energy/time)
     */
    operator fun div(time: Time): Per<EnergyUnit, TimeUnit> {
        val rateUnit = PerUnit(unit, time.unit)
        return Per(magnitude / time.magnitude, rateUnit)
    }
}
