package me.elephant1214.modularhud.api.component.condition

import kotlinx.serialization.Serializable
import me.elephant1214.modularhud.api.part.ItemLocation
import me.elephant1214.modularhud.api.part.ItemLocation.*
import me.elephant1214.modularhud.component.context.ComponentHandler
import me.elephant1214.modularhud.serializers.ItemCountConditionSerializer
import me.elephant1214.modularhud.variable.VariableManager

/**
 * Item count condition for a component.
 * True: An item stack is present and has a count of at least `threshold`.
 * False: No item stack is present or has a count less than `threshold`.
 *
 * @property location Where the item should be fetched from. Cannot be used with [INVENTORY_DURABILITY] or [FAKE] and
 * will throw an error when loaded.
 * @property threshold The item count threshold.
 * @property variable The variable for the slot numer when using [HOT_BAR] or [ARMOR], or
 * an item ID when using [INVENTORY].
 */
@Suppress("UnsafeCallOnNullableType")
@Serializable(with = ItemCountConditionSerializer::class)
data class ItemCountCondition(
    val location: ItemLocation,
    val variable: String? = null,
    val threshold: Int
) : Condition() {
    @Suppress("ReturnCount")
    override fun compute(handler: ComponentHandler): Boolean {
        return when (this.location) {
            INVENTORY -> handler.getTotalCount(this.variable!!) >= this.threshold
            HOT_BAR -> {
                val variable = VariableManager.getVariable(this.variable!!)
                return variable != null && handler.getInventoryItemCount(variable.value as Int) >= this.threshold
            }

            OFFHAND -> handler.getOffHandItemCount() >= this.threshold
            ARMOR -> {
                val variable = VariableManager.getVariable(this.variable!!)
                return variable != null && handler.getArmorCount(variable.value as Int) >= this.threshold
            }

            INVENTORY_DURABILITY, FAKE -> error("Shouldn't get here")
        }
    }
}
