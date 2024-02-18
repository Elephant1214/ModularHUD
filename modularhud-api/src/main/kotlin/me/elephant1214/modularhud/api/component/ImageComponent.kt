package me.elephant1214.modularhud.api.component

import kotlinx.serialization.Serializable
import me.elephant1214.modularhud.api.component.context.ComponentContext
import me.elephant1214.modularhud.api.parts.Position

@Serializable(with = ImageComponentSerializer::class)
class ImageComponent(val url: String, position: Position, scale: Double) : Component(position, scale) {
    override fun render(ctx: ComponentContext) {
        ctx.drawUrlImage(url, position, scale)
    }
}
