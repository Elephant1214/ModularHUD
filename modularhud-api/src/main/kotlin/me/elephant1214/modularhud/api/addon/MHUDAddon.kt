package me.elephant1214.modularhud.api.addon

import me.elephant1214.modularhud.api.variable.VariableInfo

interface MHUDAddon {
    fun getModID(): String

    fun getAllVariables(): List<String>

    fun getVariable(variable: String): VariableInfo<*>
}