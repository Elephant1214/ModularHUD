package me.elephant1214.modularhud.api.component

import kotlinx.serialization.Serializable
import me.elephant1214.modularhud.api.component.context.ComponentContext
import me.elephant1214.modularhud.api.parts.Position
import me.elephant1214.modularhud.api.parts.Size
import me.elephant1214.modularhud.api.component.property.Color

@Serializable(with = RectangleComponentSerializer::class)
class RectangleComponent(
    val size: Size, val color: Color, position: Position, scale: Double
) : Component(position, scale) {
    override fun render(ctx: ComponentContext) {
        ctx.drawRectangle(size, color, position, scale)
    }
}
