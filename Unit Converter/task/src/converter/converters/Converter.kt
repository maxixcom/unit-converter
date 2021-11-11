package converter.converters

interface Converter {
    fun fromUnitName(): String
    fun toUnitName(): String
    fun convert(value: Double): Double
}
