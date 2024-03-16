package me.elephant1214.modularhud.module

import me.elephant1214.modularhud.api.module.HUDModule
import me.elephant1214.modularhud.api.part.Color
import me.elephant1214.modularhud.utils.drawBox
import net.minecraft.client.gui.DrawContext

class ModuleActionMenu(private val hudModule: HUDModule) : ModuleHelper() {
    internal var isOpen = false

    fun render(ctx: DrawContext) {
        ctx.matrices.push()
        ctx.matrices.translate(0F, 0F, 255F)
        ctx.drawBox(
            x, y, this.width(), this.height(), Color(0, 0, 0, 255).color, false
        )
        ctx.drawText(
            this.client.textRenderer, hudModule.name, x + 1, y + 1, -1, true
        )
        ctx.matrices.pop()
    }

    override fun width(): Int = this.client.textRenderer.getWidth(hudModule.name) + 2

    /**
     * TODO: Add action menu options to modules and check the height here
     */
    override fun height(): Int = this.client.textRenderer.fontHeight + 2
}
