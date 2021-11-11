package converter

fun String.toPlural(num: Number): String {
    val table = mapOf(
        "foot" to "feet",
        "inch" to "inches",
    )
    return table[this]?.let {
        if (num != 1.0) {
            it
        } else {
            this
        }
    } ?: if (num != 1.0) "${this}s" else this
}
