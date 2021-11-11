package converter.console

import converter.converters.Converter
import converter.converters.ConverterImpl
import converter.converters.toConverterUnit
import converter.exceptions.ImpossibleToConvertException
import converter.toPlural

class ConsoleRunner : Runnable {
    private val converter: Converter = ConverterImpl()
    private val queryRegex = "^(?<value>[+-]?([0-9]*[.])?[0-9]+)\\s+(?<from>\\w+)\\s+\\w+\\s+(?<to>\\w+)$"
        .toRegex(RegexOption.IGNORE_CASE)

    override fun run() {

        while (true) {
            println("Enter what you want to convert (or exit):")
            val query = readLine()!!
            if (query.matches("exit".toRegex())) {
                break
            }
            try {
                val result = queryRegex.matchEntire(query) ?: throw Exception("Wrong input.")

                val value = result.groups["value"]!!.value.toDouble()
                val from = result.groups["from"]!!.value
                val to = result.groups["to"]!!.value

                val fromUnit = from.lowercase().toConverterUnit()
                val toUnit = to.lowercase().toConverterUnit()

                val convertedValue = try {
                    if (fromUnit == null || toUnit == null) {
                        throw ImpossibleToConvertException("Unknown unit")
                    }
                    converter.convert(value, fromUnit, toUnit)
                } catch (e: ImpossibleToConvertException) {
                    println(
                        "Conversion from ${fromUnit?.let { from } ?: "???"}" +
                            " to " +
                            "${toUnit?.let { to } ?: "???"} is impossible"
                    )
                    continue
                }
                println(
                    "$value ${fromUnit.name.toPlural(value)}" +
                        " is " +
                        "$convertedValue ${toUnit.name.toPlural(convertedValue)}"
                )
            } catch (e: Exception) {
                println(e.message)
            }
        }
    }
}
