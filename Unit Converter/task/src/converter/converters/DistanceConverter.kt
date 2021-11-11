package converter.converters

class DistanceConverter(
    private val fromUnit: DistanceUnit
) : Converter {
    override fun fromUnitName(): String = fromUnit.longName

    override fun toUnitName(): String = "meter"

    override fun convert(value: Double): Double = value * fromUnit.k
}
