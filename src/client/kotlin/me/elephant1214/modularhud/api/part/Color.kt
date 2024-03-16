package me.elephant1214.modularhud.api.part

import kotlinx.serialization.Serializable
import me.elephant1214.modularhud.api.hexToInt
import me.elephant1214.modularhud.api.rgbaToInt

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
