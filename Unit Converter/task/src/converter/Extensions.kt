package converter

import kotlin.math.abs

fun String.toPlural(num: Double): String {
    val table = mapOf(
        "foot" to "feet",
        "inch" to "inches",
        "degree Celsius" to "degrees Celsius",
        "degree Fahrenheit" to "degrees Fahrenheit",
    )
    if (table.values.contains(this)) {
        return this
    }
    val n = abs(num)
    return table[this]?.let {
        if (n != 1.0) {
            it
        } else {
            this
        }
    } ?: if (n != 1.0) "${this}s" else this
}
