package me.elephant1214.modularhud.api.component

import kotlinx.serialization.Serializable
import me.elephant1214.modularhud.api.component.condition.ConditionComposite
import me.elephant1214.modularhud.api.part.Color
import me.elephant1214.modularhud.api.part.Position
import me.elephant1214.modularhud.component.context.ComponentHandler
import me.elephant1214.modularhud.serializers.TextComponentSerializer
import me.elephant1214.modularhud.variable.VariableManager

/**
 * @property content The text content.
 * Variables can be added using `${variable}`, and translations can be added using
 * `${translate(translationKey, variables...)}`.
 * You can set text color using `&color` where `color` is either an integer, or a hex
 * color in the format of `#hexColor`.
 */
@Serializable(TextComponentSerializer::class)
class TextComponent(
    val content: String,
    position: Position,
    scale: Double = 1.0,
    condition: ConditionComposite? = null
) : Component(position, scale, condition) {
    override fun render(handler: ComponentHandler) {
        handler.drawText(this.parse(handler), Color(-1), this.position, this.scale)
    }

    private fun parse(handler: ComponentHandler): String {
        val placeholders = Regex("\\$\\{([^}]*)\\}").findAll(this.content)

        if (!placeholders.iterator().hasNext()) {
            return this.content
        }

        val computedValues = placeholders.map {
            val variable = VariableManager.getVariable(it.groups[1]!!.value)
            return@map variable?.value?.toString() ?: "null"
        }.toList()

        val finalStr = StringBuilder(this.content)
        placeholders.forEachIndexed { index, match ->
            finalStr.replace(match.range.first, match.range.last + 1, computedValues[index])
        }

        return finalStr.toString()
    }
}
