package me.elephant1214.modularhud.utils

import net.minecraft.client.gui.DrawContext

fun DrawContext.drawBox(x: Int, y: Int, width: Int, height: Int, color: Int, centered: Boolean) {
    if (centered) {
        this.fill(x - (width / 2), y - (height / 2), x + (width / 2), y + (height / 2), color)
    } else {
        this.fill(x, y, x + width, y + height, color)
    }
}
