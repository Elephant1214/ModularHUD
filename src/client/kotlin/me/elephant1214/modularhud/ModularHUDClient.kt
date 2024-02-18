package me.elephant1214.modularhud

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import me.elephant1214.modularhud.api.component.ItemComponent
import me.elephant1214.modularhud.api.component.ItemComponent.ItemType
import me.elephant1214.modularhud.api.component.TextComponent
import me.elephant1214.modularhud.api.component.property.Color
import me.elephant1214.modularhud.api.module.HUDModule
import me.elephant1214.modularhud.api.parts.Position
import me.elephant1214.modularhud.api.parts.Size
import me.elephant1214.modularhud.api.variable.VariableManager
import me.elephant1214.modularhud.module.Module
import me.elephant1214.modularhud.render.HUDHandler
import me.elephant1214.modularhud.screens.ModuleScreen
import me.elephant1214.modularhud.variables.VariableAddon
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper
import net.minecraft.client.option.KeyBinding
import net.minecraft.client.util.InputUtil
import org.lwjgl.glfw.GLFW

object ModularHUDClient : ClientModInitializer {
    const val MOD_ID = "modularhud"

    lateinit var screenBind: KeyBinding
        private set

    override fun onInitializeClient() {
        screenBind = KeyBindingHelper.registerKeyBinding(
            KeyBinding(
                "key.modularhud.screen",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_RIGHT_SHIFT,
                "category.modularhud.main"
            )
        )

        ClientTickEvents.END_CLIENT_TICK.register { client ->
            while (screenBind.wasPressed()) {
                if (client.currentScreen == null) {
                    client.setScreen(ModuleScreen())
                }
            }
        }

        HUDHandler.registerRenderListener()
        VariableAddon.registerVariables()
        VariableManager.registerAddon(VariableAddon)
        val armorTest = Module(
            HUDModule(
                "armor",
                "Armor",
                Size(32, 81),
                listOf(
                    ItemComponent(
                        ItemType.ARMOR, "modularhud.player.armor.head", Position(0, 0), 1.0
                    ),
                    TextComponent(
                        "%var%", Color(-1), listOf("modularhud.player.armor.head.durability"), Position(18,3), 1.0
                    ),
                    ItemComponent(
                        ItemType.ARMOR, "modularhud.player.armor.chest", Position(0, 17), 1.0
                    ),
                    TextComponent(
                        "%var%", Color(-1), listOf("modularhud.player.armor.chest.durability"), Position(18,20), 1.0
                    ),
                    ItemComponent(
                        ItemType.ARMOR, "modularhud.player.armor.legs", Position(0, 33), 1.0
                    ),
                    TextComponent(
                        "%var%", Color(-1), listOf("modularhud.player.armor.legs.durability"), Position(18,36), 1.0
                    ),
                    ItemComponent(
                        ItemType.ARMOR, "modularhud.player.armor.feet", Position(0, 49), 1.0
                    ),
                    TextComponent(
                        "%var%", Color(-1), listOf("modularhud.player.armor.feet.durability"), Position(18,52), 1.0
                    ),
                    ItemComponent(
                        ItemType.HOT_BAR, "modularhud.player.inventory.selectedSlot", Position(0, 65), 1.0
                    ),
                    TextComponent(
                        "%var%", Color(-1), listOf("modularhud.player.inventory.selectedSlot.durability"), Position(18,68), 1.0
                    )
                )
            )
        )
        HUDHandler.addModule(armorTest)

        println(Json.encodeToString(armorTest.hudModule))
    }
}