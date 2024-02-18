package me.elephant1214.modularhud.render

import me.elephant1214.modularhud.module.Module
import me.elephant1214.modularhud.screens.ModuleScreen
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback
import net.minecraft.client.MinecraftClient
import net.minecraft.client.gui.DrawContext

object HUDHandler {
    private val client: MinecraftClient = MinecraftClient.getInstance()
    private val modules = ArrayList<Module>()

    private fun render(drawContext: DrawContext) {
        if (client.currentScreen !is ModuleScreen) {
            for (module: Module in this.modules) {
                module.render(drawContext)
            }
        }
    }

    fun addModule(module: Module) {
        this.modules.add(module)
    }

    fun getModules(): ArrayList<Module> = this.modules

    internal fun registerRenderListener() {
        HudRenderCallback.EVENT.register { drawContext, _ ->
            this.render(drawContext)
        }
    }
}