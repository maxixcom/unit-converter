package converter.converters

sealed class ConverterUnit(val k: Double, val name: String) {
    sealed class Distance(k: Double, name: String) : ConverterUnit(k, name) {
        override fun sameGroupAs(unit: ConverterUnit) = unit is Distance
        override fun toString(): String = name

        object Meter : Distance(1.0, "meter")
        object Kilometer : Distance(1000.0, "kilometer")
        object Centimeter : Distance(0.01, "centimeter")
        object Millimeter : Distance(0.001, "millimeter")
        object Mile : Distance(1609.35, "mile")
        object Yard : Distance(0.9144, "yard")
        object Foot : Distance(0.3048, "foot")
        object Inch : Distance(0.0254, "inch")
    }

    sealed class Weight(k: Double, name: String) : ConverterUnit(k, name) {
        override fun sameGroupAs(unit: ConverterUnit) = unit is Weight
        override fun toString(): String = name

        object Gram : Weight(1.0, "gram")
        object Kilogram : Weight(1000.0, "kilogram")
        object Milligram : Weight(0.001, "milligram")
        object Pound : Weight(453.592, "pound")
        object Ounce : Weight(28.3495, "ounce")
    }

    sealed class Temperature(name: String) : ConverterUnit(0.0, name) {
        override fun sameGroupAs(unit: ConverterUnit) = unit is Temperature
        override fun toString(): String = name

        abstract fun convertTo(value: Double, unit: Temperature): Double

        object Celsius : Temperature("celsius") {
            override fun toString(): String = "degree Celsius"

            override fun convertTo(value: Double, unit: Temperature): Double =
                when (unit) {
                    Celsius -> value
                    Fahrenheit -> 32 + value * 9 / 5
                    Kelvin -> value + ABSOLUTE_ZERO
                }
        }

        object Fahrenheit : Temperature("fahrenheit") {
            override fun toString(): String = "degree Fahrenheit"

            override fun convertTo(value: Double, unit: Temperature): Double =
                when (unit) {
                    Celsius -> (value - 32) * 5 / 9
                    Fahrenheit -> value
                    Kelvin -> (value + 459.67) * 5 / 9
                }
        }

        object Kelvin : Temperature("kelvin") {
            override fun convertTo(value: Double, unit: Temperature): Double =
                when (unit) {
                    Celsius -> value - ABSOLUTE_ZERO
                    Fahrenheit -> value * 9 / 5 - 459.67
                    Kelvin -> value
                }
        }

        companion object {
            const val ABSOLUTE_ZERO = 273.15
        }
    }

    abstract fun sameGroupAs(unit: ConverterUnit): Boolean
}

private val units = mapOf(
    "(m|meters?)" to ConverterUnit.Distance.Meter,
    "(km|kilometers?)" to ConverterUnit.Distance.Kilometer,
    "^(cm|centimeters?)$" to ConverterUnit.Distance.Centimeter,
    "^(mm|millimeters?)$" to ConverterUnit.Distance.Millimeter,
    "^(mi|miles?)$" to ConverterUnit.Distance.Mile,
    "^(yd|yards?)$" to ConverterUnit.Distance.Yard,
    "^(ft|foot|feet)$" to ConverterUnit.Distance.Foot,
    "^(in|inch|inches)$" to ConverterUnit.Distance.Inch,

    "(g|grams?)" to ConverterUnit.Weight.Gram,
    "(kg|kilograms?)" to ConverterUnit.Weight.Kilogram,
    "(mg|milligrams?)" to ConverterUnit.Weight.Milligram,
    "(lb|pounds?)" to ConverterUnit.Weight.Pound,
    "(oz|ounces?)" to ConverterUnit.Weight.Ounce,

    "(celsius|dc|c?)" to ConverterUnit.Temperature.Celsius,
    "(fahrenheit|df|f?)" to ConverterUnit.Temperature.Fahrenheit,
    "(kelvins?|k?)" to ConverterUnit.Temperature.Kelvin,
)

fun String.toConverterUnit(): ConverterUnit? {
    return units.mapNotNull { (variants, unit) ->
        if (this.matches(variants.toRegex())) {
            unit
        } else {
            null
        }
    }.firstOrNull()
}

// fun main() {
//    val u = "kg".toConverterUnit()
//    println(u?.name)
//    println("isDistance: ${u is ConverterUnit.Distance}")
//    println("isWeight: ${u is ConverterUnit.Weight}")
//
//    val kg = "kilogram".toConverterUnit()!!
//    val m = "g".toConverterUnit()!!
//    println(kg.sameGroupAs(m))
//
//    println("Is equal: ${kg == u}")
// }
