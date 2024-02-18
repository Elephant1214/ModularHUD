package me.elephant1214.modularhud.module

import me.elephant1214.modularhud.api.component.property.Color
import me.elephant1214.modularhud.api.module.HUDModule
import me.elephant1214.modularhud.utils.drawBox
import net.minecraft.client.MinecraftClient
import net.minecraft.client.gui.DrawContext

class ModuleActionMenu(private val hudModule: HUDModule) {
    private val client = MinecraftClient.getInstance()

    internal var isOpen = false
    internal var x = 0
    internal var y = 0

    fun render(ctx: DrawContext) {
        ctx.matrices.push()
        ctx.matrices.translate(0F, 0F, 255F)
        ctx.drawBox(
            x,
            y,
            getNameLen() + 2,
            client.textRenderer.fontHeight + 2,
            Color(0, 0, 0, 200).color,
            false
        )
        ctx.drawText(
            client.textRenderer,
            hudModule.name,
            x + 1,
            y + 1,
            -1,
            true
        )
        ctx.matrices.pop()
    }

    // TODO: Add action menu options to modules and check the height here
    private fun getHeight(): Int = client.textRenderer.fontHeight + 2

    fun isHovered(mouseX: Double, mouseY: Double): Boolean =
        mouseX > x - 1 && mouseX < x - 1 + (getNameLen() + 2) && mouseY > y - 1 && mouseY < y - 1 + this.getHeight()

    private fun getNameLen(): Int = client.textRenderer.getWidth(hudModule.name)
}