package me.elephant1214.modularhud.screens

import me.elephant1214.modularhud.ModularHUDClient
import me.elephant1214.modularhud.api.part.Color
import me.elephant1214.modularhud.component.context.ComponentHandler
import me.elephant1214.modularhud.module.ModuleManager
import net.minecraft.client.gui.DrawContext
import net.minecraft.client.gui.screen.Screen
import net.minecraft.text.Text
import org.lwjgl.glfw.GLFW

class ModuleScreen : Screen(Text.empty()) {
    override fun render(ctx: DrawContext, mouseX: Int, mouseY: Int, delta: Float) {
        this.renderInGameBackground(ctx)

        ModuleManager.getModules().forEach { module ->
            val handler = ComponentHandler(ctx, module)

            if (!module.hudModule.shouldRender(handler)) return

            ctx.drawBorder(
                module.x, module.y, module.width(), module.height(), Color(255, 255, 255, 128).color
            )
            module.render(handler)

            module.updatePosition(mouseX.toDouble(), mouseY.toDouble())
        }
    }

    override fun mouseClicked(mouseX: Double, mouseY: Double, button: Int): Boolean {
        ModuleManager.getModules().forEach { module -> module.mouseClicked(mouseX, mouseY, button) }
        return super.mouseClicked(mouseX, mouseY, button)
    }

    override fun mouseReleased(mouseX: Double, mouseY: Double, button: Int): Boolean {
        ModuleManager.getModules().forEach { module -> module.mouseReleased(mouseX, mouseY, button) }
        return super.mouseReleased(mouseX, mouseY, button)
    }

    override fun keyPressed(keyCode: Int, scanCode: Int, modifiers: Int): Boolean {
        if (keyCode == GLFW.GLFW_KEY_RIGHT_SHIFT || keyCode == GLFW.GLFW_KEY_ESCAPE) {
            this.close()
        }
        return true
    }

    override fun close() {
        ModuleManager.getModules().forEach {
            it.onCloseModScreen()
            ModularHUDClient.config.updateModulePos(it.hudModule.id, it.x, it.y)
        }
        ModularHUDClient.saveConfig()
        super.close()
    }
}
