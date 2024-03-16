package me.elephant1214.modularhud.configuration

import kotlinx.serialization.Serializable
import me.elephant1214.modularhud.api.part.Position

@Serializable
data class ModuleEntry(
    val id: String,
    var position: Position,
    var enabled: Boolean
)
