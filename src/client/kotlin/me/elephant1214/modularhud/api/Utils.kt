package me.elephant1214.modularhud.api

fun rgbaToInt(r: Int, g: Int, b: Int, a: Int) = (a shl 24) or (r shl 16) or (g shl 8) or b

fun hexToInt(hex: String): Int {
    val formattedHex = if (hex.startsWith("#")) hex.substring(1) else hex

    val r = formattedHex.substring(0, 2).toInt(16)
    val g = formattedHex.substring(2, 4).toInt(16)
    val b = formattedHex.substring(4, 6).toInt(16)
    val a = if (formattedHex.length == 8) formattedHex.substring(6, 8).toInt(16) else 255

    return rgbaToInt(r, g, b, a)
}
