package me.elephant1214.modularhud.screens

import me.elephant1214.modularhud.ModularHUDClient
import me.elephant1214.modularhud.api.part.Color
import me.elephant1214.modularhud.module.ModuleManager
import net.minecraft.client.gui.DrawContext
import net.minecraft.client.gui.screen.Screen
import net.minecraft.text.Text
import org.lwjgl.glfw.GLFW

class ModuleScreen : Screen(Text.empty()) {
    override fun render(context: DrawContext?, mouseX: Int, mouseY: Int, delta: Float) {
        this.renderInGameBackground(context)

        ModuleManager.getModules().forEach { module ->
            context!!.drawBorder(
                module.x - 1, module.y - 1, module.width() + 1, module.height() + 1, Color(255, 255, 255, 128).color
            )
            module.render(context)

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
