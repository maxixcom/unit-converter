package converter.converters

interface Converter {
    fun convert(value: Double, from: ConverterUnit, to: ConverterUnit): Double
}
