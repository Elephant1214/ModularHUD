package me.elephant1214.modularhud.module

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import me.elephant1214.modularhud.ModularHUDClient
import me.elephant1214.modularhud.ModularHUDClient.LOGGER
import me.elephant1214.modularhud.api.module.HUDModule
import me.elephant1214.modularhud.api.part.Position
import me.elephant1214.modularhud.configuration.ModuleEntry
import me.elephant1214.modularhud.screens.ModuleScreen
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback
import net.minecraft.client.MinecraftClient
import net.minecraft.client.gui.DrawContext

object ModuleManager {
    private val client: MinecraftClient = MinecraftClient.getInstance()

    private val modules = hashMapOf<String, ModuleHandler>()

    /**
     * Registers a module.
     */
    fun registerModule(module: HUDModule, pos: Position) {
        if (isRegistered(module.id)) {
            LOGGER.error("A module with the ID \"${module.id}\" is already registered, ignoring")
        } else {
            modules[module.id] = ModuleHandler(module, pos)
        }
    }

    /**
     * @return Whether a module with the id [id] is registered.
     */
    fun isRegistered(id: String): Boolean = getModuleNames().contains(id)

    /**
     * @return A set of all registered module IDs
     */
    fun getModuleNames(): Set<String> = modules.keys

    /**
     * @return A collection of all registered modules
     */
    fun getModules(): Collection<ModuleHandler> = modules.values

    /**
     * Looks through the modules folder and loads all modules.
     */
    internal fun loadModules() {
        var failed = 0
        var ignored = 0
        for (file in ModularHUDClient.moduleDir.toFile().walk()) {
            if (!file.isFile || !file.isFile || !file.name.endsWith(".json")) continue

            try {
                val decoded = Json.decodeFromStream(HUDModule.serializer(), file.inputStream())
                val configured = ModularHUDClient.config.getConfiguredModule(decoded.id)

                if (configured == null) {
                    val entry = ModuleEntry(decoded.id, Position(0, 0), true)
                    ModularHUDClient.config.modules.add(entry)
                    registerModule(decoded, entry.position)
                } else if (configured.enabled) {
                    registerModule(decoded, configured.position)
                } else {
                    ignored++
                }

            } catch (e: IllegalArgumentException) {
                LOGGER.error("Caught an exception while trying to deserialize ${file.name}:\n${e.stackTraceToString()}")
                failed++
            }
        }

        ModularHUDClient.saveConfig()

        LOGGER.info("Loaded ${modules.size} module(s). Ignored $ignored disabled module(s).")
        if (failed > 0) {
            LOGGER.warn("$failed module(s) failed to load with an exception")
        }
    }

    private fun render(drawContext: DrawContext) {
        if (client.currentScreen !is ModuleScreen) {
            for (module in modules.values) {
                module.render(drawContext)
            }
        }
    }

    internal fun registerRenderListener() {
        HudRenderCallback.EVENT.register { drawContext, _ ->
            render(drawContext)
        }
    }
}
