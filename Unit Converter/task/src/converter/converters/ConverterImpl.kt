package converter.converters

import converter.exceptions.ImpossibleToConvertException

class ConverterImpl : Converter {
    override fun convert(value: Double, from: ConverterUnit, to: ConverterUnit): Double {
        if (from == to) {
            return value
        }

        if (!from.sameGroupAs(to)) {
            throw ImpossibleToConvertException("Conversion from ${from.name} to ${to.name} is impossible")
        }

        return value * from.k / to.k
    }
}
