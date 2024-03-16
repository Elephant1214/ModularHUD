package me.elephant1214.modularhud.configuration

import kotlinx.serialization.Serializable
import me.elephant1214.modularhud.ModularHUDClient.LOGGER
import me.elephant1214.modularhud.api.part.Position

@Serializable
data class MHUDConfig(
    val modules: ArrayList<ModuleEntry>
) {
    fun getConfiguredModule(id: String): ModuleEntry? {
        for (module in this.modules) {
            if (module.id == id) {
                return module
            }
        }
        return null
    }

    internal fun updateModulePos(id: String, x: Int, y: Int) {
        val module = this.getConfiguredModule(id)
        if (module != null) {
            module.position = Position(x, y)
        } else {
            LOGGER.error("No configured module was found?")
        }
    }
}
