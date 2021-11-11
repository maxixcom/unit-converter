package converter.converters

enum class DistanceUnit(val k: Double, val longName: String) {
    Meter(1.0, "meter"),
    Kilometer(1000.0, "kilometer"),
    Centimeter(0.01, "centimeter"),
    Millimeter(0.001, "millimeter"),
    Mile(1609.35, "mile"),
    Yard(0.9144, "yard"),
    Foot(0.3048, "foot"),
    Inch(0.0254, "inch"),
}
