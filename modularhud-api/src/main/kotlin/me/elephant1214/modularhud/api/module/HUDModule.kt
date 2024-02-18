package me.elephant1214.modularhud.api.module

import kotlinx.serialization.Serializable
import me.elephant1214.modularhud.api.component.Component
import me.elephant1214.modularhud.api.parts.Size

@Serializable
class HUDModule(val id: String, val name: String, val size: Size, val components: List<Component>)
