package me.elephant1214.modularhud.api.component

import kotlinx.serialization.Serializable
import me.elephant1214.modularhud.api.variable.VariableManager
import me.elephant1214.modularhud.api.component.context.ComponentContext
import me.elephant1214.modularhud.api.parts.Position
import me.elephant1214.modularhud.api.parts.Translation
import me.elephant1214.modularhud.api.component.property.Color

/**
 * @param content Some text
 * @param color The color for the text to be rendered in
 * @param variables Variables to replace placeholders in the string with. Translations should be wrapped with `t()` and include any variables needed for their placeholders seperated by commas.
 */
@Serializable(with = TextComponentSerializer::class)
class TextComponent(
    val content: String, val color: Color, val variables: List<String>, position: Position, scale: Double
) : Component(position, scale) {
    override fun render(ctx: ComponentContext) {
        var finalContent = content

        if (variables.isNotEmpty()) {
            val finalVars = mutableListOf<Any>()

            for (vrb: String in variables) {
                if (vrb.startsWith("t(")) {
                    val translationCtx = vrb.removePrefix("t(").removeSuffix(")").split(", ")
                    finalVars.add(Translation(translationCtx[0], translationCtx.drop(1).toTypedArray()))
                } else {
                    finalVars.add(vrb)
                }
            }

            finalContent = parseVariables(ctx, content, finalVars)
        }

        ctx.drawText(finalContent, color, position, scale)
    }
}

private fun parseVariables(ctx: ComponentContext, content: String, vars: List<Any>): String {
    var modifiedContent = content

    for (vrb: Any in vars) {
        if (vrb is Translation) {
            modifiedContent = modifiedContent.replaceFirst("%t%", ctx.translateKey(vrb))
        } else if (vrb is String && vrb.contains(".")) {
            val value = VariableManager.getVariable(vrb)!!.value
            val test = value.toString().toIntOrNull()
            modifiedContent = if (test == 0 || test == null) {
                modifiedContent.replaceFirst("%var%", "")
            } else {
                modifiedContent.replaceFirst("%var%", value.toString())
            }
        }
    }

    return modifiedContent
}
