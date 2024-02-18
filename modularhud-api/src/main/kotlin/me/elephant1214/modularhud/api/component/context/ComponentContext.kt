package me.elephant1214.modularhud.api.component.context

import me.elephant1214.modularhud.api.component.property.Color
import me.elephant1214.modularhud.api.parts.Position
import me.elephant1214.modularhud.api.parts.Size
import me.elephant1214.modularhud.api.parts.Translation

interface ComponentContext {
    fun translateKey(translation: Translation): String

    fun drawText(content: String, color: Color, pos: Position, scale: Double)

    fun drawRectangle(size: Size, color: Color, pos: Position, scale: Double)

    fun drawUrlImage(url: String, pos: Position, scale: Double)

    fun drawResource(identifier: String, pos: Position, scale: Double)

    fun drawItemWithCount(item: String, pos: Position, scale: Double)

    fun drawItemWithDurability(item: String, pos: Position, scale: Double)

    fun drawHotBarItem(slot: Int, pos: Position, scale: Double)

    fun drawOffhandItem(pos: Position, scale: Double)

    fun drawArmorItem(armorSlot: Int, pos: Position, scale: Double)

    fun drawItem(item: String, pos: Position, scale: Double)
}