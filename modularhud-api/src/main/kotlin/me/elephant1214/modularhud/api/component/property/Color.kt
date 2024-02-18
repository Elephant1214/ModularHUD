package me.elephant1214.modularhud.api.component.property

import kotlinx.serialization.Serializable

/**
 * Represents a color
 *
 * @param color The color as a single integer
 */
@Serializable
data class Color(val color: Int) {
    /**
     * @param r Red value
     * @param g Green value
     * @param b Blue value
     * @param a Alpha value
     */
    constructor(r: Int, g: Int, b: Int, a: Int) : this(rgbaToInt(r, g, b, a))

    /**
     * @param hex Six or eight character long HEX value. `#` is not required in front.
     */
    constructor(hex: String) : this(hexToInt(hex))
}

private fun rgbaToInt(r: Int, g: Int, b: Int, a: Int) = (a shl 24) or (r shl 16) or (g shl 8) or b

private fun hexToInt(hex: String): Int {
    val formattedHex = if (hex.startsWith("#")) hex.substring(1) else hex

    val r = formattedHex.substring(0, 2).toInt(16)
    val g = formattedHex.substring(2, 4).toInt(16)
    val b = formattedHex.substring(4, 6).toInt(16)
    val a = if (formattedHex.length == 8) formattedHex.substring(6, 8).toInt(16) else 255

    return rgbaToInt(r, g, b, a)
}
