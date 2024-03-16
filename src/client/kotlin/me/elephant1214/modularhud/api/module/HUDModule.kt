package me.elephant1214.modularhud.api.module

import kotlinx.serialization.Serializable
import me.elephant1214.modularhud.api.component.Component
import me.elephant1214.modularhud.api.component.condition.ConditionComposite
import me.elephant1214.modularhud.api.part.Size
import me.elephant1214.modularhud.component.context.ComponentHandler

@Serializable
class HUDModule(
    val id: String,
    val name: String,
    val authors: List<String>,
    val size: Size,
    val components: List<Component>,
    val condition: ConditionComposite? = null
) {
    fun shouldRender(handler: ComponentHandler): Boolean {
        return if (this.condition == null) {
            true
        } else {
            this.condition.compute(handler)
        }
    }
}
