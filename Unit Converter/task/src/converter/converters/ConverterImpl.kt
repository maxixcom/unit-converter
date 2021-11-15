package converter.converters

import converter.exceptions.ImpossibleToConvertException

class ConverterImpl : Converter {
    override fun convert(value: Double, from: ConverterUnit, to: ConverterUnit): Double {
        if (from == to) {
            return value
        }

        if (!from.sameGroupAs(to)) {
            throw ImpossibleToConvertException("Conversion from $from to $to is impossible")
        }

        if (from is ConverterUnit.Temperature && to is ConverterUnit.Temperature) {
            return from.convertTo(value, to)
        }
        if (value < 0) {
            when (from) {
                is ConverterUnit.Distance -> throw (Exception("Length shouldn't be negative"))
                is ConverterUnit.Weight -> throw (Exception("Weight shouldn't be negative"))
                else -> {
                }
            }
        }

        return value * from.k / to.k
    }
}
