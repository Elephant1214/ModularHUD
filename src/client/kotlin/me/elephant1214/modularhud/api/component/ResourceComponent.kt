package me.elephant1214.modularhud.api.component

import kotlinx.serialization.Serializable
import me.elephant1214.modularhud.api.component.condition.ConditionComposite
import me.elephant1214.modularhud.api.part.Position
import me.elephant1214.modularhud.component.context.ComponentHandler
import me.elephant1214.modularhud.serializers.ResourceComponentSerializer

@Serializable(with = ResourceComponentSerializer::class)
class ResourceComponent(
    val identifier: String,
    position: Position,
    scale: Double,
    condition: ConditionComposite? = null
) : Component(position, scale, condition) {
    override fun render(handler: ComponentHandler) {
        handler.drawResource(this.identifier, this.position, this.scale)
    }
}
