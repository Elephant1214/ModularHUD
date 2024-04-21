package me.elephant1214.modularhud.module

import me.elephant1214.modularhud.api.component.Component
import me.elephant1214.modularhud.api.module.HUDModule
import me.elephant1214.modularhud.api.part.Position
import me.elephant1214.modularhud.component.context.ComponentHandler
import org.lwjgl.glfw.GLFW

/**
 * A sort of wrapper for a [HUDModule] that contains functions that don't belong in
 * [ComponentHandler].
 */
class ModuleHandler(
    val hudModule: HUDModule,
    pos: Position
) : ModuleHelper() {
    private val components = this.hudModule.components
    private val actionMenu = ModuleActionMenu(this.hudModule)

    private var dragging = false

    private var dragX = 0
    private var dragY = 0

    init {
        this.x = pos.x
        this.y = pos.y
    }

    fun render(handler: ComponentHandler) {
        this.checkAndFixPosition()

        for (component: Component in this.components) {
            if (component.shouldRender(handler)) {
                component.render(handler)
            }
        }
        if (this.actionMenu.isOpen) {
            this.actionMenu.render(handler.ctx)
        }
    }

    override fun width(): Int = this.hudModule.size.width

    override fun height(): Int = this.hudModule.size.height

    fun mouseClicked(mouseX: Double, mouseY: Double, button: Int) {
        if (this.isHovered(mouseX, mouseY)) {
            if (button == GLFW.GLFW_MOUSE_BUTTON_1) {
                this.dragging = true
                this.dragX = (mouseX - this.x).toInt()
                this.dragY = (mouseY - this.y).toInt()
            } else if (button == GLFW.GLFW_MOUSE_BUTTON_2) {
                this.actionMenu.isOpen = true
                this.actionMenu.x = mouseX.toInt()
                this.actionMenu.y = mouseY.toInt()
            }
        }
    }

    fun updatePosition(mouseX: Double, mouseY: Double) {
        if (this.dragging) {
            val targetX = (mouseX - this.dragX).toInt()
            val targetY = (mouseY - this.dragY).toInt()
            val easingFactor = 0.2
            this.x = (this.x + easingFactor * (targetX - this.x)).toInt()
            this.y = (this.y + easingFactor * (targetY - this.y)).toInt()
        } else if (!this.dragging && actionMenu.isOpen && !actionMenu.isHovered(mouseX, mouseY)) {
            this.actionMenu.isOpen = false
        }
    }

    fun mouseReleased(mouseX: Double, mouseY: Double, button: Int) {
        if (this.dragging && button == 0) this.dragging = false
        if (this.x < 0) this.x = 0
        if (this.y < 0) this.y = 0
        val maxX: Int = this.client.window.scaledWidth - this.width()
        val maxY: Int = this.client.window.scaledHeight - this.height()
        if (this.x > maxX) this.x = maxX
        if (this.y > maxY) this.y = maxY
    }

    fun onCloseModScreen() {
        this.actionMenu.isOpen = false
    }

    private fun checkAndFixPosition() {
        if (!this.dragging) {
            val window = this.client.window
            if (this.x >= window.scaledWidth) {
                this.x = window.scaledWidth - this.width()
            }
            if (this.y >= window.scaledHeight) {
                this.y = window.scaledHeight - this.height()
            }
        }
    }
}
