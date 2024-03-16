package me.elephant1214.modularhud

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import kotlinx.serialization.json.encodeToStream
import me.elephant1214.modularhud.configuration.MHUDConfig
import me.elephant1214.modularhud.module.ModuleManager
import me.elephant1214.modularhud.screens.ModuleScreen
import me.elephant1214.modularhud.variable.VariableAddon
import me.elephant1214.modularhud.variable.VariableManager
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper
import net.fabricmc.loader.api.FabricLoader
import net.minecraft.client.option.KeyBinding
import net.minecraft.client.util.InputUtil
import org.lwjgl.glfw.GLFW
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.io.File
import java.nio.file.Path
import kotlin.io.path.createDirectory
import kotlin.io.path.exists

object ModularHUDClient : ClientModInitializer {
    const val MOD_ID = "modularhud"

    internal val LOGGER: Logger = LoggerFactory.getLogger("Modular HUD")

    val configDir: Path =
        FabricLoader.getInstance().configDir.resolve(MOD_ID).apply { if (!exists()) createDirectory() }
    val moduleDir: Path = this.configDir.resolve("modules").apply { if (!exists()) createDirectory() }

    private val configFile: File = File(this.configDir.toFile(), "modularHudConfig.json").apply {
        if (!exists()) {
            createNewFile()
            Json.encodeToStream(MHUDConfig(arrayListOf()), this.outputStream())
        }
    }
    internal val config: MHUDConfig = Json.decodeFromStream(MHUDConfig.serializer(), this.configFile.inputStream())

    lateinit var screenBind: KeyBinding
        private set

    override fun onInitializeClient() {
        this.setupModuleScreen()
        this.addBaseVarAddon()
        this.setupModuleManager()
        this.setupEvents()
    }

    fun saveConfig() {
        Json.encodeToStream(config, this.configFile.outputStream())
    }

    private fun setupModuleScreen() {
        this.screenBind = KeyBindingHelper.registerKeyBinding(
            KeyBinding(
                "key.modularhud.screen", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_RIGHT_SHIFT, "category.modularhud.main"
            )
        )

        ClientTickEvents.END_CLIENT_TICK.register { client ->
            while (this.screenBind.wasPressed()) {
                if (client.currentScreen == null) {
                    client.setScreen(ModuleScreen())
                }
            }
        }
    }

    private fun addBaseVarAddon() {
        VariableAddon.registerVariables()
        VariableManager.registerAddon(VariableAddon)
    }

    private fun setupModuleManager() {
        ModuleManager.loadModules()
        ModuleManager.registerRenderListener()
    }

    private fun setupEvents() {
        ClientLifecycleEvents.CLIENT_STOPPING.register {
            this.saveConfig()
        }
    }
}
