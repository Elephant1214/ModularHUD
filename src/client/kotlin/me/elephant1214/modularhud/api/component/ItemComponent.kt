package me.elephant1214.modularhud.api.component

import kotlinx.serialization.Serializable
import me.elephant1214.modularhud.api.component.condition.ConditionComposite
import me.elephant1214.modularhud.api.part.ItemLocation
import me.elephant1214.modularhud.api.part.ItemLocation.*
import me.elephant1214.modularhud.api.part.Position
import me.elephant1214.modularhud.component.context.ComponentHandler
import me.elephant1214.modularhud.serializers.ItemComponentSerializer
import me.elephant1214.modularhud.variable.VariableManager

/**
 * @property location Where the item will come from
 * @property variable A hot bar or armor slot variable
 */
@Suppress("UnsafeCallOnNullableType")
@Serializable(with = ItemComponentSerializer::class)
class ItemComponent(
    val location: ItemLocation,
    val variable: String? = null,
    position: Position,
    scale: Double = 1.0,
    condition: ConditionComposite? = null
) : Component(position, scale, condition) {
    override fun render(handler: ComponentHandler) {
        when (this.location) {
            INVENTORY -> handler.drawItemWithCount(this.variable!!, this.position, this.scale)
            DURABILITY -> handler.drawInvItemWithDurability(this.variable!!, this.position, this.scale)
            HOT_BAR -> {
                val variable = VariableManager.getVariable(this.variable!!)
                if (variable != null) {
                    handler.drawHotBarItem(variable.value as Int, this.position, this.scale)
                }
            }

            OFFHAND -> handler.drawOffhandItem(this.position, this.scale)
            ARMOR -> {
                val variable = VariableManager.getVariable(this.variable!!)
                if (variable != null) {
                    handler.drawArmorItem(variable.value as Int, this.position, this.scale)
                }
            }

            FAKE -> handler.drawItem(this.variable!!, this.position, this.scale)
        }
    }
}
