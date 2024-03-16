package me.elephant1214.modularhud.variable

import me.elephant1214.modularhud.ModularHUDClient.LOGGER
import me.elephant1214.modularhud.api.addon.MHUDAddon
import me.elephant1214.modularhud.api.variable.VariableInfo
import net.minecraft.item.Item
import net.minecraft.item.Items
import net.minecraft.registry.Registries
import net.minecraft.util.Identifier

object VariableManager {
    private val addons = hashMapOf<String, MHUDAddon>()
    private val variableIndex = hashMapOf<String, MHUDAddon>()
    private val itemCache = hashMapOf<String, Item>()

    fun registerAddon(addon: MHUDAddon) {
        if (this.addons.containsValue(addon)) {
            LOGGER.error("Addon \"${addon.getModID()}\" is already registered, ignoring")
        } else {
            this.addons[addon.getModID()] = addon
            updateVariableIndex(addon)
        }
    }

    /**
     * @return A set of all registered addons.
     */
    fun getRegisteredAddons(): Set<String> = this.addons.keys

    /**
     * @return Whether [id] matches that of a registered addon
     */
    fun isAddonRegistered(id: String): Boolean = this.addons.containsKey(id)

    fun getVariable(variable: String): VariableInfo<*>? {
        val addon = this.variableIndex[variable]
        return if (addon == null) {
            LOGGER.error("Unable to find $variable in registered variables")
            null
        } else {
            addon.getVariable(variable)
        }
    }

    fun cacheItem(id: String): Boolean {
        if (!this.itemCache.containsKey(id)) {
            val resolved = Registries.ITEM.get(Identifier(id))
            if (resolved == Items.AIR) {
                return false
            }
            this.itemCache[id] = resolved
        }
        return true
    }

    fun getItem(id: String): Item {
        val item = this.itemCache[id]
        return item ?: error("No item with $id was found. This should not be possible!")
    }

    private fun updateVariableIndex(addon: MHUDAddon) {
        val addonVars = addon.getAllVariables()
        for (variable: String in addonVars) {
            if (this.variableIndex.containsKey(variable)) {
                LOGGER.error("The variable \"$variable\" was already registered by ${this.variableIndex[variable]!!.getModID()}")
            } else {
                this.variableIndex[variable] = addon
            }
        }
    }
}
