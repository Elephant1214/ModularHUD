package me.elephant1214.modularhud.module

import me.elephant1214.modularhud.api.component.Component
import me.elephant1214.modularhud.api.module.HUDModule
import me.elephant1214.modularhud.component.context.ComponentContextImpl
import net.minecraft.client.MinecraftClient
import net.minecraft.client.gui.DrawContext
import org.lwjgl.glfw.GLFW

class Module(
    val hudModule: HUDModule
) {
    private val client = MinecraftClient.getInstance()
    private val components = hudModule.components
    private val actionMenu = ModuleActionMenu(hudModule)

    private var dragging = false

    // TODO: Needs to be loaded (from config?) later on
    var x = 0
    var y = 0
    var dragX = 0
    var dragY = 0

    fun render(ctx: DrawContext) {
        for (component: Component in components) {
            component.render(ComponentContextImpl(ctx, this))
        }
        if (actionMenu.isOpen) {
            actionMenu.render(ctx)
        }
    }

    fun getWidth(): Int = hudModule.size.width

    fun getHeight(): Int = hudModule.size.height

    fun mouseClicked(mouseX: Double, mouseY: Double, button: Int) {
        if (this.isHovered(mouseX, mouseY)) {
            if (button == GLFW.GLFW_MOUSE_BUTTON_1) {
                this.dragging = true
                this.dragX = (mouseX - this.x).toInt()
                this.dragY = (mouseY - this.y).toInt()
            } else if (button == GLFW.GLFW_MOUSE_BUTTON_2) {
                actionMenu.isOpen = true
                actionMenu.x = mouseX.toInt()
                actionMenu.y = mouseY.toInt()
            }
        }
    }

    private fun isHovered(mouseX: Double, mouseY: Double): Boolean =
        mouseX > x && mouseX < x + this.getWidth() && mouseY > y && mouseY < y + this.getHeight()

    fun updatePosition(mouseX: Double, mouseY: Double) {
        if (this.dragging) {
            val targetX = (mouseX - this.dragX).toInt()
            val targetY = (mouseY - this.dragY).toInt()
            val easingFactor = 0.2
            this.x = (this.x + easingFactor * (targetX - this.x)).toInt()
            this.y = (this.y + easingFactor * (targetY - this.y)).toInt()
        } else if (!this.dragging && actionMenu.isOpen && !actionMenu.isHovered(mouseX, mouseY)) {
            actionMenu.isOpen = false
        }
    }

    fun mouseReleased(mouseX: Double, mouseY: Double, button: Int) {
        if (this.dragging && button == 0) this.dragging = false
        if (this.x < 0) this.x = 0
        if (this.y < 0) this.y = 0
        val maxX: Int = this.client.window.scaledWidth - this.getWidth()
        val maxY: Int = this.client.window.scaledHeight - this.getHeight()
        if (this.x > maxX) this.x = maxX
        if (this.y > maxY) this.y = maxY
    }

    fun onCloseModScreen() {
        actionMenu.isOpen = false
    }
}