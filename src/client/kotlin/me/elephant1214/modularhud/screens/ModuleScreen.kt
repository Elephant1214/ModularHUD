package me.elephant1214.modularhud.screens

import me.elephant1214.modularhud.api.component.property.Color
import me.elephant1214.modularhud.render.HUDHandler
import net.minecraft.client.MinecraftClient
import net.minecraft.client.gui.DrawContext
import net.minecraft.client.gui.screen.Screen
import net.minecraft.text.Text
import org.lwjgl.glfw.GLFW

class ModuleScreen : Screen(Text.empty()) {
    override fun render(context: DrawContext?, mouseX: Int, mouseY: Int, delta: Float) {
        this.renderInGameBackground(context)

        HUDHandler.getModules().forEach { module ->
            context!!.drawBorder(
                module.x - 1, module.y - 1, module.getWidth() + 2, module.getHeight() + 2, Color(255, 255, 255, 128).color
            )
            module.render(context)
            module.updatePosition(mouseX.toDouble(), mouseY.toDouble())
        }
    }

    override fun mouseClicked(mouseX: Double, mouseY: Double, button: Int): Boolean {
        HUDHandler.getModules().forEach { module -> module.mouseClicked(mouseX, mouseY, button) }
        return super.mouseClicked(mouseX, mouseY, button)
    }

    override fun mouseReleased(mouseX: Double, mouseY: Double, button: Int): Boolean {
        HUDHandler.getModules().forEach { module -> module.mouseReleased(mouseX, mouseY, button) }
        return super.mouseReleased(mouseX, mouseY, button)
    }

    override fun keyPressed(keyCode: Int, scanCode: Int, modifiers: Int): Boolean {
        if (keyCode == GLFW.GLFW_KEY_RIGHT_SHIFT) {
            MinecraftClient.getInstance().setScreen(null)
        }
        return super.keyPressed(keyCode, scanCode, modifiers)
    }

    override fun close() {
        HUDHandler.getModules().forEach { it.onCloseModScreen() }
        super.close()
    }
}