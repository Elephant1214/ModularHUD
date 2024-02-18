package me.elephant1214.modularhud.api.component

import kotlinx.serialization.Serializable
import me.elephant1214.modularhud.api.component.context.ComponentContext
import me.elephant1214.modularhud.api.parts.Position

/**
 * This class is meant to be overridden and NOT used in a module.
 *
 * @param position The position relative to the inside of the HUD module
 * @param scale The amount to scale this module by
 */
@Serializable
sealed class Component(val position: Position, val scale: Double) {
    abstract fun render(ctx: ComponentContext)
}
