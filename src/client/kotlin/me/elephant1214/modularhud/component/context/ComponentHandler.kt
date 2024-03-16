package me.elephant1214.modularhud.component.context

import me.elephant1214.modularhud.api.part.Color
import me.elephant1214.modularhud.api.part.Position
import me.elephant1214.modularhud.api.part.Size
import me.elephant1214.modularhud.api.part.Translation
import me.elephant1214.modularhud.module.ModuleHandler
import me.elephant1214.modularhud.utils.drawBox
import net.minecraft.client.MinecraftClient
import net.minecraft.client.gui.DrawContext
import net.minecraft.item.ItemStack
import net.minecraft.registry.Registries
import net.minecraft.text.Text
import net.minecraft.util.Identifier

@Suppress("TooManyFunctions")
class ComponentHandler(private val ctx: DrawContext, private val module: ModuleHandler) {
    private val client = MinecraftClient.getInstance()

    /**
     * Total amount of this item from the player's inventory.
     * If there are none, the return value will always be 0.
     */
    fun getTotalCount(item: String): Int {
        TODO("Not yet implemented")
    }

    /**
     * @return The durability of the first matching item in the player's inventory, or 0
     * if there isn't one.
     */
    fun getDurabilityForFirstItem(item: String): Int {
        TODO("Not yet implemented")
    }

    /**
     * @return The durability of the item stack in `slot`, or 0 if the stack is empty or
     * doesn't have a durability.
     */
    fun getHotBarItemDurability(slot: Int): Int =
        this.client.player!!.inventory.main[slot].maxDamage - this.client.player!!.inventory.main[slot].damage

    fun getInventoryItemCount(slot: Int): Int = this.client.player!!.inventory.main[slot].count

    /**
     * @return The durability of the item stack in the player's offhand, or 0 if the
     * stack is empty or doesn't have a durability.
     */
    fun getOffHandItemDurability(): Int =
        this.client.player!!.inventory.offHand[0].maxDamage - this.client.player!!.inventory.offHand[0].damage

    /**
     * @return The item count of the stack in the player's offhand, or 0 if the stack is
     * empty or can't stack.
     */
    fun getOffHandItemCount(): Int = this.client.player!!.inventory.offHand[0].count

    /**
     * @return The durability of the armor in armor slot `slot`, or 0 if the stack is
     * empty or doesn't have a durability.
     */
    fun getArmorDurability(slot: Int): Int =
        this.client.player!!.inventory.armor[slot].maxDamage - this.client.player!!.inventory.armor[slot].damage

    /**
     * @return The number of items in an armor slot.
     */
    fun getArmorCount(slot: Int): Int = this.client.player!!.inventory.armor[slot].count

    /**
     * @return A translated and string from a [Translation] with placeholders filled.
     */
    fun translateKey(translation: Translation): String {
        return Text.translatable(translation.key, translation.replacements).toString()
    }

    fun drawText(content: String, color: Color, pos: Position, scale: Double) {
        ctx.matrices.push()
        ctx.matrices.translate(0F, 0F, 232F)
        ctx.drawText(
            client.textRenderer, content, module.x + pos.x, module.y + pos.y, color.color, true
        )
        ctx.matrices.pop()
    }

    fun drawRectangle(size: Size, color: Color, pos: Position, scale: Double) {
        ctx.drawBox(
            module.x + pos.x, module.y + pos.y, size.width, size.height, color.color, false
        )
    }

    fun drawUrlImage(url: String, pos: Position, scale: Double) {
        TODO("Not yet implemented")
    }

    fun drawResource(identifier: String, pos: Position, scale: Double) {
        TODO("Not yet implemented")
    }

    fun drawItemWithCount(item: String, pos: Position, scale: Double) {
        TODO("Not yet implemented")
    }

    fun drawInvItemWithDurability(item: String, pos: Position, scale: Double) {
        TODO("Not yet implemented")
    }

    fun drawHotBarItem(slot: Int, pos: Position, scale: Double) {
        val stack = client.player!!.inventory.getStack(slot)
        drawStack(stack, pos, scale)
    }

    fun drawOffhandItem(pos: Position, scale: Double) {
        val stack = client.player!!.inventory.offHand[0]
        drawStack(stack, pos, scale)
    }

    fun drawArmorItem(armorSlot: Int, pos: Position, scale: Double) {
        val stack = client.player!!.inventory.armor[armorSlot]
        drawStack(stack, pos, scale)
    }

    @Suppress("USELESS_ELVIS")
    fun drawItem(item: String, pos: Position, scale: Double) {
        val id = Identifier(item)
        // The compiler thinks this elvis is useless, for some reason
        val found = Registries.ITEM.get(id) ?: error("$id is an invalid item identifier")
        val stack = ItemStack(found)
        drawStack(stack, pos, scale)
    }

    private fun drawStack(stack: ItemStack, position: Position, scale: Double) {
        ctx.matrices.push()
        ctx.matrices.translate(0F, 0F, 60F)
        if (scale != 1.0) {
            ctx.matrices.scale(scale.toFloat(), scale.toFloat(), 0F)
        }
        ctx.drawItem(
            stack, module.x + position.x, module.y + position.y
        )
        ctx.drawItemInSlot(
            client.textRenderer, stack, module.x + position.x, module.y + position.y
        )
        ctx.matrices.pop()
    }
}
