package me.elephant1214.modularhud.api.variable

import me.elephant1214.modularhud.api.addon.MHUDAddon
import org.slf4j.LoggerFactory
import java.util.*

@Suppress("unused")
object VariableManager {
    private val logger = LoggerFactory.getLogger("ModularHUD Variable Manager")
    private val addons = HashMap<String, MHUDAddon>()
    private val variableIndex = HashMap<String, MHUDAddon>()

    fun registerAddon(addon: MHUDAddon) {
        if (addons.containsValue(addon)) {
            logger.error("${addon.getModID()} is already registered, ignoring")
        } else {
            addons[addon.getModID()] = addon
            updateVariableIndex(addon)
        }
    }

    fun getRegisteredAddons(): Set<String> = addons.keys

    fun isAddonRegistered(name: String): Boolean = addons.containsKey(name)

    fun getVariable(variable: String): VariableInfo<*>? {
        val addon = variableIndex[variable]
        return addon?.getVariable(variable)
    }

    private fun updateVariableIndex(addon: MHUDAddon) {
        val addonVars = addon.getAllVariables()
        for (variable: String in addonVars) {
            if (variableIndex.containsKey(variable)) {
               logger.error("The variable \"$variable\" was already registered by ${variableIndex[variable]!!.getModID()}")
            } else {
                variableIndex[variable] = addon
            }
        }
    }
}