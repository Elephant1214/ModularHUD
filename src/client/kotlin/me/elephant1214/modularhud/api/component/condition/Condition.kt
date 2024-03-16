package me.elephant1214.modularhud.api.component.condition

import kotlinx.serialization.Serializable
import me.elephant1214.modularhud.component.context.ComponentHandler

@Serializable
sealed class Condition {
    abstract fun compute(handler: ComponentHandler): Boolean
}
