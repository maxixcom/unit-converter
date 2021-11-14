package converter

fun String.toPlural(num: Number): String {
    val table = mapOf(
        "meter" to "meters",
        "foot" to "feet",
        "inch" to "inches",
        "yard" to "yards",
        "cm" to "centimeters",
        "mm" to "millimeters",

        "gram" to "grams",
        "g" to "grams"
    )
    if (table.values.contains(this)) {
        return this
    }
    return table[this]?.let {
        if (num != 1.0) {
            it
        } else {
            this
        }
    } ?: if (num != 1.0) "${this}s" else this
}
