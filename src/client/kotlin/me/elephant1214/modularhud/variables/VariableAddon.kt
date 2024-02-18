package me.elephant1214.modularhud.variables

import me.elephant1214.modularhud.ModularHUDClient
import me.elephant1214.modularhud.api.addon.MHUDAddon
import me.elephant1214.modularhud.api.variable.VariableInfo
import net.minecraft.client.MinecraftClient

object VariableAddon : MHUDAddon {
    private val client = MinecraftClient.getInstance()
    private val variableValues = hashMapOf<String, () -> VariableInfo<*>>()
    private val variables = mutableListOf<String>()

    internal fun registerVariables() {
        registerVariable("modularhud.player.health") { VariableInfo(client.player!!.health, Float::class.java) }
        registerVariable("modularhud.player.armor") { VariableInfo(client.player!!.armor, Int::class.java) }
        registerVariable("modularhud.player.autoJumpEnabled") { VariableInfo(client.player!!.isAutoJumpEnabled, Boolean::class.java) }
        registerVariable("modularhud.player.limitedCraftEnabled") { VariableInfo(client.player!!.isLimitedCraftingEnabled, Boolean::class.java) }
        registerVariable("modularhud.player.nauseaIntensity") { VariableInfo(client.player!!.nauseaIntensity, Float::class.java) }
        registerVariable("modularhud.player.moodPercentage") { VariableInfo(client.player!!.moodPercentage, Float::class.java) }
        registerVariable("modularhud.player.isRiding") { VariableInfo(client.player!!.isRiding, Boolean::class.java) }
        registerVariable("modularhud.player.underwaterVisibility") { VariableInfo(client.player!!.underwaterVisibility, Float::class.java) }
        registerVariable("modularhud.player.isCrawling") { VariableInfo(client.player!!.isCrawling, Boolean::class.java) }
        registerVariable("modularhud.player.isSwimming") { VariableInfo(client.player!!.isSwimming, Boolean::class.java) }

        registerVariable("modularhud.player.mount.canJump") { VariableInfo(client.player!!.jumpingMount != null, Boolean::class.java) }
        registerVariable("modularhud.player.mount.jumpStrength") { VariableInfo(client.player!!.mountJumpStrength, Float::class.java) }

        registerVariable("modularhud.player.pos.x") { VariableInfo(client.player!!.x, Double::class.java) }
        registerVariable("modularhud.player.pos.y") { VariableInfo(client.player!!.y, Double::class.java) }
        registerVariable("modularhud.player.pos.z") { VariableInfo(client.player!!.z, Double::class.java) }
        registerVariable("modularhud.player.pos.pitch") { VariableInfo(client.player!!.pitch, Float::class.java) }
        registerVariable("modularhud.player.pos.yaw") { VariableInfo(client.player!!.yaw, Float::class.java) }

        registerVariable("modularhud.player.inventory.selectedSlot") { VariableInfo(client.player!!.inventory.selectedSlot, Int::class.java) }
        registerVariable("modularhud.player.inventory.selectedSlot.durability") { VariableInfo(client.player!!.activeItem.damage, Int::class.java) }

        registerVariable("modularhud.player.armor.head") { VariableInfo(3, Int::class.java) }
        registerVariable("modularhud.player.armor.head.durability") { VariableInfo(client.player!!.inventory.armor[3].damage, Int::class.java) }
        registerVariable("modularhud.player.armor.chest") { VariableInfo(2, Int::class.java) }
        registerVariable("modularhud.player.armor.chest.durability") { VariableInfo(client.player!!.inventory.armor[2].damage, Int::class.java) }
        registerVariable("modularhud.player.armor.legs") { VariableInfo(1, Int::class.java) }
        registerVariable("modularhud.player.armor.legs.durability") { VariableInfo(client.player!!.inventory.armor[1].damage, Int::class.java) }
        registerVariable("modularhud.player.armor.feet") { VariableInfo(0, Int::class.java) }
        registerVariable("modularhud.player.armor.feet.durability") { VariableInfo(client.player!!.inventory.armor[0].damage, Int::class.java) }
    }

    private fun registerVariable(variable: String, value: () -> VariableInfo<*>) {
        this.variableValues[variable] = value
        this.variables.add(variable)
    }

    override fun getVariable(variable: String): VariableInfo<*> = this.variableValues[variable]!!.invoke()

    override fun getAllVariables(): List<String> = this.variables

    override fun getModID(): String = ModularHUDClient.MOD_ID
}
