package me.elephant1214.modularhud.component.context

import me.elephant1214.modularhud.api.component.property.Color
import me.elephant1214.modularhud.api.component.context.ComponentContext
import me.elephant1214.modularhud.api.parts.Position
import me.elephant1214.modularhud.api.parts.Size
import me.elephant1214.modularhud.api.parts.Translation
import me.elephant1214.modularhud.module.Module
import me.elephant1214.modularhud.utils.drawBox
import net.minecraft.client.MinecraftClient
import net.minecraft.client.gui.DrawContext
import net.minecraft.item.ItemStack
import net.minecraft.registry.Registries
import net.minecraft.text.Text
import net.minecraft.util.Identifier

class ComponentContextImpl(private val ctx: DrawContext, private val module: Module) : ComponentContext {
    private val client = MinecraftClient.getInstance()

    override fun translateKey(translation: Translation): String {
        return Text.translatable(translation.key, translation.replacements).toString()
    }

    override fun drawText(content: String, color: Color, pos: Position, scale: Double) {
        ctx.matrices.push()
        ctx.matrices.translate(0F, 0F, 232F)
        ctx.drawText(
            client.textRenderer,
            content,
            module.x + pos.x,
            module.y + pos.y,
            color.color,
            true
        )
        ctx.matrices.pop()
    }

    override fun drawRectangle(size: Size, color: Color, pos: Position, scale: Double) {
        ctx.drawBox(
            module.x + pos.x,
            module.y + pos.y,
            size.width,
            size.height,
            color.color,
            false
        )
    }

    override fun drawUrlImage(url: String, pos: Position, scale: Double) {
        TODO("Not yet implemented")
    }

    override fun drawResource(identifier: String, pos: Position, scale: Double) {
        TODO("Not yet implemented")
    }

    override fun drawItemWithCount(item: String, pos: Position, scale: Double) {
        TODO("Not yet implemented")
    }

    override fun drawItemWithDurability(item: String, pos: Position, scale: Double) {
        TODO("Not yet implemented")
    }

    override fun drawHotBarItem(slot: Int, pos: Position, scale: Double) {
        val stack = client.player!!.inventory.getStack(slot)
        drawStack(stack, pos, scale)
    }

    override fun drawOffhandItem(pos: Position, scale: Double) {
        TODO("Not yet implemented")
    }

    override fun drawArmorItem(armorSlot: Int, pos: Position, scale: Double) {
        val stack = client.player!!.inventory.armor[armorSlot]
        drawStack(stack, pos, scale)
    }

    @Suppress("USELESS_ELVIS")
    override fun drawItem(item: String, pos: Position, scale: Double) {
        val id = Identifier(item)
        val found = Registries.ITEM.get(id) ?: error("$id is an invalid")
        val stack = ItemStack(found)
        drawStack(stack, pos, scale)
    }

    private fun drawStack(stack: ItemStack, position: Position, scale: Double) {
        ctx.matrices.push()
        ctx.matrices.translate(0F, 0F, 232F)
        if (scale != 1.0) {
            ctx.matrices.scale(scale.toFloat(), scale.toFloat(), 0F)
        }
        ctx.drawItem(
            stack,
            module.x + position.x,
            module.y + position.y
        )
        ctx.drawItemInSlot(
            client.textRenderer,
            stack,
            module.x + position.x,
            module.y + position.y
        )
        ctx.matrices.pop()
    }
}