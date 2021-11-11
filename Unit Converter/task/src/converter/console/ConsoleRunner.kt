package converter.console

import converter.converters.Converter
import converter.converters.DistanceConverter
import converter.converters.DistanceUnit
import converter.toPlural

class ConsoleRunner : Runnable {
    private val table = mapOf<Regex, Converter>(
        "^(m|meters?)$".toRegex() to DistanceConverter(DistanceUnit.Meter),
        "^(km|kilometers?)$".toRegex() to DistanceConverter(DistanceUnit.Kilometer),
        "^(cm|centimeters?)$".toRegex() to DistanceConverter(DistanceUnit.Centimeter),
        "^(mm|millimeters?)$".toRegex() to DistanceConverter(DistanceUnit.Millimeter),
        "^(mi|miles?)$".toRegex() to DistanceConverter(DistanceUnit.Mile),
        "^(yd|yards?)$".toRegex() to DistanceConverter(DistanceUnit.Yard),
        "^(ft|foot|feet)$".toRegex() to DistanceConverter(DistanceUnit.Foot),
        "^(in|inch|inches)$".toRegex() to DistanceConverter(DistanceUnit.Inch),
    )

    override fun run() {
        println("Enter a number and a measure of length:")
        val query = readLine()!!
        val queryRegex = "^(?<num>[+-]?([0-9]*[.])?[0-9]+)\\s+(?<unit>\\w+)$".toRegex(RegexOption.IGNORE_CASE)
        val output = queryRegex.matchEntire(query)?.let {
            val unit = it.groups["unit"]!!.value.lowercase()
            val value = it.groups["num"]!!.value.toDouble()

            table.asSequence().mapNotNull { (regex, converter) ->
                regex.find(unit)?.let {
                    val convertedValue = converter.convert(value)
                    "$value ${converter.fromUnitName().toPlural(value)}" +
                        " is " +
                        "$convertedValue ${converter.toUnitName().toPlural(convertedValue)}"
                }
            }.firstOrNull() ?: "Wrong input. Unknown unit $unit"
        } ?: "Wrong input."

        println(output)
    }
}
