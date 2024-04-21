package me.elephant1214.modularhud.api.module

import kotlinx.serialization.Serializable
import me.elephant1214.modularhud.api.component.Component
import me.elephant1214.modularhud.api.component.condition.ConditionComposite
import me.elephant1214.modularhud.api.part.Size
import me.elephant1214.modularhud.component.context.ComponentHandler
import me.elephant1214.modularhud.variable.VariableManager

@Serializable
class HUDModule(
    val id: String,
    val name: String,
    val authors: List<String>,
    val size: Size,
    val components: List<Component>,
    val condition: ConditionComposite? = null,
    val dependencies: List<String>
) {
    /**
     * @return A list of any missing dependencies
     */
    fun checkDependencies(): List<String> =
        this.dependencies.filterNot { VariableManager.isAddonRegistered(it) }

    fun shouldRender(handler: ComponentHandler): Boolean =
        this.condition == null || this.condition.compute(handler)
}
