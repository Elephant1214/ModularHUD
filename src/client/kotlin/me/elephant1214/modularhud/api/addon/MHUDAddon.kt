package me.elephant1214.modularhud.api.addon

import me.elephant1214.modularhud.api.variable.VariableInfo

interface MHUDAddon {
    /**
     * @return The mod ID for the mod that adds this addon.
     * Should be unique as this is also used when registering variables.
     */
    fun getModID(): String

    /**
     * @return A list containing all variables added by this addon.
     */
    fun getAllVariables(): List<String>

    /**
     * @return A variable registered by this addon wrapped with [VariableInfo].
     */
    fun getVariable(variable: String): VariableInfo<*>
}
