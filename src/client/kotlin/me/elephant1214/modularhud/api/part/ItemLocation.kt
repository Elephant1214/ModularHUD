package me.elephant1214.modularhud.api.part

import kotlinx.serialization.Serializable

@Serializable
enum class ItemLocation {
    /**
     * An item from the player's inventory. Shows the total number of the specified item.
     */
    INVENTORY,

    /**
     * The first item matching the ID provided by the variable, durability is displayed instead of item count.
     */
    INVENTORY_DURABILITY,

    /**
     * A hot bar position from the player's hot bar
     */
    HOT_BAR,

    /**
     * The item in the player's offhand
     */
    OFFHAND,

    /**
     * An armor position
     */
    ARMOR,

    /**
     * Takes the variable and displays the item even if the player has none.
     */
    FAKE
}
