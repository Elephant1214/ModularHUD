package me.elephant1214.modularhud.api.part

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class ItemLocation {
    /**
     * An item from the player's inventory. Shows the total number of the specified item.
     */
    @SerialName("inventory") INVENTORY,

    /**
     * The first item matching the ID provided by the variable, durability is displayed instead of item count.
     */
    @SerialName("durability") DURABILITY,

    /**
     * A hot bar position from the player's hot bar
     */
    @SerialName("hot_bar") HOT_BAR,

    /**
     * The item in the player's offhand
     */
    @SerialName("offhand") OFFHAND,

    /**
     * An armor position
     */
    @SerialName("armor") ARMOR,

    /**
     * Takes the variable and displays the item even if the player has none.
     */
    @SerialName("fake") FAKE
}
