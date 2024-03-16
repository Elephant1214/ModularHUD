package me.elephant1214.modularhud.module

import net.minecraft.client.MinecraftClient

/**
 * Various functions and properties/variables that are shared across multiple classes
 * that handle parts of module rendering.
 *
 *
 */
abstract class ModuleHelper {
    protected val client: MinecraftClient = MinecraftClient.getInstance()

    var x = 0
    var y = 0

    fun isHovered(mouseX: Double, mouseY: Double): Boolean =
        mouseX >= x && mouseX <= x + this.width() && mouseY >= y && mouseY <= y + this.height()

    abstract fun width(): Int

    abstract fun height(): Int
}
