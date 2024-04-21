package me.elephant1214.modularhud.api.component

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import me.elephant1214.modularhud.api.component.condition.ConditionComposite
import me.elephant1214.modularhud.api.part.Position
import me.elephant1214.modularhud.component.context.ComponentHandler

/**
 * This class is meant to be overridden.
 *
 * @property position The position relative to the inside of the HUD module
 * @property scale The amount to scale this module by
 * @property condition The conditions for this component to be applied
 */
@Serializable
sealed class Component(
    @SerialName("pos") val position: Position,
    val scale: Double = 1.0,
    val condition: ConditionComposite? = null
) {
    fun shouldRender(handler: ComponentHandler): Boolean {
        return this.condition == null || this.condition.compute(handler)
    }

    abstract fun render(handler: ComponentHandler)
}
