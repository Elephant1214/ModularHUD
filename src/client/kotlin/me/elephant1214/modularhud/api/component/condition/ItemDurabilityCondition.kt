package me.elephant1214.modularhud.api.component.condition

import kotlinx.serialization.Serializable
import me.elephant1214.modularhud.api.part.ItemLocation
import me.elephant1214.modularhud.api.part.ItemLocation.*
import me.elephant1214.modularhud.component.context.ComponentHandler
import me.elephant1214.modularhud.serializers.ItemDurabilityConditionSerializer
import me.elephant1214.modularhud.variable.VariableManager

/**
 * Item durability condition for a component.
 * True: The item is present and has at least `threshold` durability or has no durability.
 * False: The is not present or does not have at least `threshold` durability.
 *
 * @property location Where the item should be fetched from. Cannot be used with [INVENTORY] or [FAKE] and will throw
 * an error when loaded.
 * @property threshold The durability threshold. Must be more than 0.
 * @property variable The variable for the slot numer when using [HOT_BAR] or [ARMOR], or an item ID when using
 * [INVENTORY_DURABILITY].
 */
@Suppress("UnsafeCallOnNullableType")
@Serializable(with = ItemDurabilityConditionSerializer::class)
data class ItemDurabilityCondition(
    val location: ItemLocation,
    val variable: String? = null,
    val threshold: Int
) : Condition() {
    @Suppress("ReturnCount")
    override fun compute(handler: ComponentHandler): Boolean {
        return when (this.location) {
            INVENTORY_DURABILITY -> {
                val durability = handler.getDurabilityForFirstItem(this.variable!!)
                return durability == 0 || durability >= this.threshold
            }

            HOT_BAR -> {
                val variable = VariableManager.getVariable(this.variable!!)
                return variable != null && handler.getHotBarItemDurability(variable.value as Int) >= this.threshold
            }

            OFFHAND -> handler.getOffHandItemDurability() >= this.threshold
            ARMOR -> {
                val variable = VariableManager.getVariable(this.variable!!)
                return variable != null && handler.getArmorDurability(variable.value as Int) >= this.threshold
            }

            INVENTORY, FAKE -> error("Shouldn't get here")
        }
    }
}
