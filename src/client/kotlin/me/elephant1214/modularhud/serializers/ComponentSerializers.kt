@file:Suppress("DuplicatedCode", "WildcardImport")

package me.elephant1214.modularhud.serializers

import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.encoding.CompositeDecoder
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import me.elephant1214.modularhud.api.component.*
import me.elephant1214.modularhud.api.component.condition.ConditionComposite
import me.elephant1214.modularhud.api.part.Color
import me.elephant1214.modularhud.api.part.ItemLocation
import me.elephant1214.modularhud.api.part.ItemLocation.*
import me.elephant1214.modularhud.api.part.Position
import me.elephant1214.modularhud.api.part.Size
import me.elephant1214.modularhud.variable.VariableManager

object ImageComponentSerializer : KSerializer<ImageComponent> {
    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("image") {
        element<String>("url")
        element<Position>("pos")
        element<Double>("scale")
        element<ConditionComposite?>("condition")
    }

    override fun serialize(encoder: Encoder, value: ImageComponent) {
        val composite = encoder.beginStructure(descriptor)
        composite.encodeStringElement(descriptor, 0, value.url)
        composite.encodeSerializableElement(descriptor, 1, Position.serializer(), value.position)
        composite.encodeDoubleElement(descriptor, 2, value.scale)
        composite.encodeNullableSerializableElement(descriptor, 3, ConditionComposite.serializer(), value.condition)
        composite.endStructure(descriptor)
    }

    override fun deserialize(decoder: Decoder): ImageComponent {
        lateinit var url: String
        var position = Position()
        var scale = 1.0
        var condition: ConditionComposite? = null

        val composite = decoder.beginStructure(descriptor)
        loop@ while (true) {
            when (val index = composite.decodeElementIndex(descriptor)) {
                CompositeDecoder.DECODE_DONE -> break
                0 -> url = composite.decodeStringElement(descriptor, index)
                1 -> position = composite.decodeSerializableElement(descriptor, index, Position.serializer())
                2 -> scale = composite.decodeDoubleElement(descriptor, index)
                3 -> condition =
                    composite.decodeNullableSerializableElement(descriptor, index, ConditionComposite.serializer())

                else -> throw SerializationException("Unknown index $index")
            }
        }

        composite.endStructure(descriptor)
        return ImageComponent(url, position, scale, condition)
    }
}

object ItemComponentSerializer : KSerializer<ItemComponent> {
    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("item") {
        element<ItemLocation>("loc")
        element<String?>("var")
        element<Position>("pos")
        element<Double>("scale")
        element<ConditionComposite?>("condition")
    }

    override fun serialize(encoder: Encoder, value: ItemComponent) {
        val composite = encoder.beginStructure(descriptor)
        composite.encodeSerializableElement(descriptor, 0, ItemLocation.serializer(), value.location)
        composite.encodeNullableSerializableElement(descriptor, 1, String.serializer(), value.variable)
        composite.encodeSerializableElement(descriptor, 2, Position.serializer(), value.position)
        composite.encodeDoubleElement(descriptor, 3, value.scale)
        composite.encodeNullableSerializableElement(descriptor, 4, ConditionComposite.serializer(), value.condition)
        composite.endStructure(descriptor)
    }

    override fun deserialize(decoder: Decoder): ItemComponent {
        lateinit var location: ItemLocation
        var variable: String? = null
        var position = Position()
        var scale = 1.0
        var condition: ConditionComposite? = null

        val composite = decoder.beginStructure(descriptor)
        loop@ while (true) {
            when (val index = composite.decodeElementIndex(descriptor)) {
                CompositeDecoder.DECODE_DONE -> break
                0 -> location = composite.decodeSerializableElement(descriptor, index, ItemLocation.serializer())
                1 -> {
                    variable = composite.decodeNullableSerializableElement(descriptor, index, String.serializer())
                    if (variable == null) continue

                    if (location == INVENTORY || location == DURABILITY || location == FAKE) {
                        if (!VariableManager.cacheItem(variable)) {
                            error("Unable to find an item named \"$variable\"")
                        }
                    }
                }

                2 -> position = composite.decodeSerializableElement(descriptor, index, Position.serializer())
                3 -> scale = composite.decodeDoubleElement(descriptor, index)
                4 -> condition =
                    composite.decodeNullableSerializableElement(descriptor, index, ConditionComposite.serializer())

                else -> throw SerializationException("Unknown index $index")
            }
        }

        composite.endStructure(descriptor)
        return ItemComponent(location, variable, position, scale, condition)
    }
}

object RectangleComponentSerializer : KSerializer<RectangleComponent> {
    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("rectangle") {
        element<Size>("size")
        element<Color>("color")
        element<Position>("pos")
        element<Double>("scale")
        element<ConditionComposite?>("condition")
    }

    override fun serialize(encoder: Encoder, value: RectangleComponent) {
        val composite = encoder.beginStructure(descriptor)
        composite.encodeSerializableElement(descriptor, 0, Size.serializer(), value.size)
        composite.encodeSerializableElement(descriptor, 1, Color.serializer(), value.color)
        composite.encodeSerializableElement(descriptor, 2, Position.serializer(), value.position)
        composite.encodeDoubleElement(descriptor, 3, value.scale)
        composite.encodeNullableSerializableElement(descriptor, 4, ConditionComposite.serializer(), value.condition)
        composite.endStructure(descriptor)
    }

    override fun deserialize(decoder: Decoder): RectangleComponent {
        lateinit var size: Size
        lateinit var color: Color
        var position = Position()
        var scale = 1.0
        var condition: ConditionComposite? = null

        val composite = decoder.beginStructure(descriptor)
        loop@ while (true) {
            when (val index = composite.decodeElementIndex(descriptor)) {
                CompositeDecoder.DECODE_DONE -> break
                0 -> size = composite.decodeSerializableElement(descriptor, index, Size.serializer())
                1 -> color = composite.decodeSerializableElement(descriptor, index, Color.serializer())
                2 -> position = composite.decodeSerializableElement(descriptor, index, Position.serializer())
                3 -> scale = composite.decodeDoubleElement(descriptor, index)
                4 -> condition =
                    composite.decodeNullableSerializableElement(descriptor, index, ConditionComposite.serializer())

                else -> throw SerializationException("Unknown index $index")
            }
        }

        composite.endStructure(descriptor)
        return RectangleComponent(size, color, position, scale, condition)
    }
}

object ResourceComponentSerializer : KSerializer<ResourceComponent> {
    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("resource") {
        element<String>("id")
        element<Position>("pos")
        element<Double>("scale")
        element<ConditionComposite?>("condition")
    }

    override fun serialize(encoder: Encoder, value: ResourceComponent) {
        val composite = encoder.beginStructure(descriptor)
        composite.encodeStringElement(descriptor, 0, value.identifier)
        composite.encodeSerializableElement(descriptor, 1, Position.serializer(), value.position)
        composite.encodeDoubleElement(descriptor, 2, value.scale)
        composite.encodeNullableSerializableElement(descriptor, 3, ConditionComposite.serializer(), value.condition)
        composite.endStructure(descriptor)
    }

    override fun deserialize(decoder: Decoder): ResourceComponent {
        lateinit var identifier: String
        var position = Position()
        var scale = 1.0
        var condition: ConditionComposite? = null

        val composite = decoder.beginStructure(descriptor)
        loop@ while (true) {
            when (val index = composite.decodeElementIndex(descriptor)) {
                CompositeDecoder.DECODE_DONE -> break
                0 -> identifier = composite.decodeStringElement(descriptor, index)
                1 -> position = composite.decodeSerializableElement(descriptor, index, Position.serializer())
                2 -> scale = composite.decodeDoubleElement(descriptor, index)
                3 -> condition =
                    composite.decodeNullableSerializableElement(descriptor, index, ConditionComposite.serializer())

                else -> throw SerializationException("Unknown index $index")
            }
        }

        composite.endStructure(descriptor)
        return ResourceComponent(identifier, position, scale, condition)
    }
}

object TextComponentSerializer : KSerializer<TextComponent> {
    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("text") {
        element<String>("content")
        element<Position>("pos")
        element<Double>("scale")
        element<ConditionComposite?>("condition")
    }

    override fun serialize(encoder: Encoder, value: TextComponent) {
        val composite = encoder.beginStructure(descriptor)
        composite.encodeStringElement(descriptor, 0, value.content)
        composite.encodeSerializableElement(descriptor, 1, Position.serializer(), value.position)
        composite.encodeDoubleElement(descriptor, 2, value.scale)
        composite.encodeNullableSerializableElement(descriptor, 3, ConditionComposite.serializer(), value.condition)
        composite.endStructure(descriptor)
    }

    override fun deserialize(decoder: Decoder): TextComponent {
        lateinit var content: String
        var position = Position()
        var scale = 1.0
        var condition: ConditionComposite? = null

        val composite = decoder.beginStructure(descriptor)
        loop@ while (true) {
            when (val index = composite.decodeElementIndex(descriptor)) {
                CompositeDecoder.DECODE_DONE -> break
                0 -> content = composite.decodeStringElement(descriptor, index)
                1 -> position = composite.decodeSerializableElement(descriptor, index, Position.serializer())
                2 -> scale = composite.decodeDoubleElement(descriptor, index)
                3 -> condition =
                    composite.decodeNullableSerializableElement(descriptor, index, ConditionComposite.serializer())

                else -> throw SerializationException("Unknown index $index")
            }
        }

        composite.endStructure(descriptor)
        return TextComponent(content, position, scale, condition)
    }
}
