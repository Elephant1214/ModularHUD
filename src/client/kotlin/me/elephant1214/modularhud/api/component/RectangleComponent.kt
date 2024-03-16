package me.elephant1214.modularhud.api.component

import kotlinx.serialization.Serializable
import me.elephant1214.modularhud.api.component.condition.ConditionComposite
import me.elephant1214.modularhud.api.part.Color
import me.elephant1214.modularhud.api.part.Position
import me.elephant1214.modularhud.api.part.Size
import me.elephant1214.modularhud.component.context.ComponentHandler
import me.elephant1214.modularhud.serializers.RectangleComponentSerializer

@Serializable(with = RectangleComponentSerializer::class)
class RectangleComponent(
    val size: Size,
    val color: Color,
    position: Position,
    scale: Double,
    condition: ConditionComposite? = null
) : Component(position, scale, condition) {
    override fun render(handler: ComponentHandler) {
        handler.drawRectangle(size, color, position, scale)
    }
}
