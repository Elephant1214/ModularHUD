package me.elephant1214.modularhud.api.component

import kotlinx.serialization.Serializable
import me.elephant1214.modularhud.api.variable.VariableManager
import me.elephant1214.modularhud.api.component.ItemComponent.ItemType.*
import me.elephant1214.modularhud.api.component.context.ComponentContext
import me.elephant1214.modularhud.api.parts.Position

/**
 * @param type Where the item will come from
 * @param variable A hot bar or armor slot or an item ID
 */
@Serializable(with = ItemComponentSerializer::class)
class ItemComponent(
    val type: ItemType, val variable: String, position: Position, scale: Double
) : Component(position, scale) {
    override fun render(ctx: ComponentContext) {
        when (type) {
            INVENTORY -> ctx.drawItemWithCount(variable, position, scale)
            INVENTORY_DURABILITY -> ctx.drawItemWithDurability(variable, position, scale)
            HOT_BAR -> {
                if (variable.toIntOrNull() == null) {
                    ctx.drawHotBarItem(VariableManager.getVariable(variable)!!.value as Int, position, scale)
                } else {
                    ctx.drawHotBarItem(variable.toInt(), position, scale)
                }
            }
            OFFHAND -> ctx.drawOffhandItem(position, scale)
            ARMOR -> {
                if (variable.toIntOrNull() == null) {
                    ctx.drawArmorItem(VariableManager.getVariable(variable)!!.value as Int, position, scale)
                } else {
                    ctx.drawArmorItem(variable.toInt(), position, scale)
                }
            }
            FAKE -> ctx.drawItem(variable, position, scale)
        }
    }

    /**
     * @property INVENTORY An item from the player's inventory. Shows the total number of the specified item.
     * @property INVENTORY_DURABILITY The first item matching the ID provided by the variable, durability is displayed instead of item count.
     * @property HOT_BAR A hot bar position from the player's hot bar inventory
     * @property OFFHAND The item in the player's offhand
     * @property ARMOR An armor position
     * @property FAKE Takes the variable and displays the item even if the player has none.
     */
    @Serializable
    enum class ItemType {
        INVENTORY,
        INVENTORY_DURABILITY,
        HOT_BAR,
        OFFHAND,
        ARMOR,
        FAKE
    }
}