package dev.nextftc.units.unittypes

import dev.nextftc.units.Measure
import dev.nextftc.units.Unit
import dev.nextftc.units.measuretypes.Time
import kotlin.time.DurationUnit
import kotlin.time.toDuration

class TimeUnit(
    baseUnit: TimeUnit?,
    toBaseConverter: (Double) -> Double,
    fromBaseConverter: (Double) -> Double,
    unitName: String,
    unitSymbol: String
) : Unit<TimeUnit>(
    baseUnit, toBaseConverter, fromBaseConverter, unitName, unitSymbol
) {
    internal constructor(
        baseUnit: TimeUnit,
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
     * Implementations are **strongly** recommended to sharpen the return type to a
     * unit-specific measurement implementation.
     *
     * @param magnitude the magnitude of the measurement.
     * @return the measurement object
     */
    override fun of(magnitude: Double): Time {
        require(this.durationUnit != null) { "Unit $this does not have a corresponding internal duration unit." }

        return Time(magnitude.toDuration(this.durationUnit!!), this)
    }

    /**
     * Creates a new immutable measurement of the given magnitude in terms of this unit's base unit.
     * Implementations are **strongly** recommended to sharpen the return type to a
     * unit-specific measurement implementation.
     *
     * @param baseUnitMagnitude the magnitude in terms of the base unit
     * @return the measurement object
     */
    override fun ofBaseUnits(baseUnitMagnitude: Double): Measure<TimeUnit> {
        return of(this.fromBaseUnits(baseUnitMagnitude))
    }

    /**
     * Combines this unit with a unit of time. This often - but not always - results in a velocity.
     * Subclasses should sharpen the return type to be unit-specific.
     *
     * @param time the unit of time
     * @return the combined unit
     */
    override fun per(time: TimeUnit): Unit<*> {
        TODO("Not yet implemented")
    }
}

val Seconds = TimeUnit(null, { it }, { it }, "second", "s")
val Milliseconds = TimeUnit(Seconds, 1e-3, "millisecond", "ms")
val Microseconds = TimeUnit(Seconds, 1e-6, "microsecond", "us")
val Nanoseconds = TimeUnit(Seconds, 1e-9, "nanosecond", "ns")
val Minutes = TimeUnit(Seconds, 60.0, "minute", "min")
val Hours = TimeUnit(Minutes, 60.0, "hour", "h")
val Days = TimeUnit(Hours, 24.0, "day", "d")


val DurationUnit.timeUnit get() = when(this) {
    DurationUnit.NANOSECONDS -> Nanoseconds
    DurationUnit.MICROSECONDS -> Microseconds
    DurationUnit.MILLISECONDS -> Milliseconds
    DurationUnit.SECONDS -> Seconds
    DurationUnit.MINUTES -> Minutes
    DurationUnit.HOURS -> Hours
    DurationUnit.DAYS -> Days
}

val TimeUnit.durationUnit get() = when (this) {
    Seconds -> DurationUnit.SECONDS
    Milliseconds -> DurationUnit.MILLISECONDS
    Microseconds -> DurationUnit.MICROSECONDS
    Nanoseconds -> DurationUnit.NANOSECONDS
    Minutes -> DurationUnit.MINUTES
    Hours -> DurationUnit.HOURS
    Days -> DurationUnit.DAYS
    else -> null
}