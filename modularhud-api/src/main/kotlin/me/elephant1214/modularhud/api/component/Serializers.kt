package me.elephant1214.modularhud.api.component

import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.encoding.CompositeDecoder
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import me.elephant1214.modularhud.api.component.ItemComponent.ItemType
import me.elephant1214.modularhud.api.component.property.Color
import me.elephant1214.modularhud.api.parts.Position
import me.elephant1214.modularhud.api.parts.Size
import kotlin.properties.Delegates.notNull

object ImageComponentSerializer : KSerializer<ImageComponent> {
    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("image") {
        element<String>("url")
        element<Position>("pos")
        element<Double>("scale")
    }

    override fun serialize(encoder: Encoder, value: ImageComponent) {
        val composite = encoder.beginStructure(descriptor)
        composite.encodeStringElement(descriptor, 0, value.url)
        composite.encodeSerializableElement(descriptor, 1, Position.serializer(), value.position)
        composite.encodeDoubleElement(descriptor, 2, value.scale)
        composite.endStructure(descriptor)
    }

    override fun deserialize(decoder: Decoder): ImageComponent {
        lateinit var url: String
        lateinit var position: Position
        var scale by notNull<Double>()

        val composite = decoder.beginStructure(descriptor)
        while (true) {
            when (val index = composite.decodeElementIndex(descriptor)) {
                CompositeDecoder.DECODE_DONE -> break
                0 -> url = composite.decodeStringElement(descriptor, index)
                1 -> position = composite.decodeSerializableElement(descriptor, index, Position.serializer())
                2 -> scale = composite.decodeDoubleElement(descriptor, index)
                else -> throw SerializationException("Unknown index $index")
            }
        }

        composite.endStructure(descriptor)
        return ImageComponent(url, position, scale)
    }
}

object ItemComponentSerializer : KSerializer<ItemComponent> {
    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("item") {
        element<ItemType>("itemType")
        element<Position>("pos")
        element<String>("var")
        element<Double>("scale")
    }

    override fun serialize(encoder: Encoder, value: ItemComponent) {
        val composite = encoder.beginStructure(descriptor)
        composite.encodeSerializableElement(descriptor, 0, ItemType.serializer(), value.type)
        composite.encodeSerializableElement(descriptor, 1, Position.serializer(), value.position)
        composite.encodeStringElement(descriptor, 2, value.variable)
        composite.encodeDoubleElement(descriptor, 3, value.scale)
        composite.endStructure(descriptor)
    }

    override fun deserialize(decoder: Decoder): ItemComponent {
        lateinit var type: ItemType
        lateinit var position: Position
        lateinit var variable: String
        var scale by notNull<Double>()

        val composite = decoder.beginStructure(descriptor)
        while (true) {
            when (val index = composite.decodeElementIndex(descriptor)) {
                CompositeDecoder.DECODE_DONE -> break
                0 -> type = composite.decodeSerializableElement(descriptor, index, ItemType.serializer())
                1 -> position = composite.decodeSerializableElement(descriptor, index, Position.serializer())
                2 -> variable = composite.decodeStringElement(descriptor, index)
                3 -> scale = composite.decodeDoubleElement(descriptor, index)
                else -> throw SerializationException("Unknown index $index")
            }
        }

        composite.endStructure(descriptor)
        return ItemComponent(type, variable, position, scale)
    }
}

object RectangleComponentSerializer : KSerializer<RectangleComponent> {
    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("rectangle") {
        element<Size>("size")
        element<Color>("color")
        element<Position>("pos")
        element<Double>("scale")
    }

    override fun serialize(encoder: Encoder, value: RectangleComponent) {
        val composite = encoder.beginStructure(descriptor)
        composite.encodeSerializableElement(descriptor, 0, Size.serializer(), value.size)
        composite.encodeSerializableElement(descriptor, 1, Color.serializer(), value.color)
        composite.encodeSerializableElement(descriptor, 2, Position.serializer(), value.position)
        composite.encodeDoubleElement(descriptor, 3, value.scale)
        composite.endStructure(descriptor)
    }

    override fun deserialize(decoder: Decoder): RectangleComponent {
        lateinit var size: Size
        lateinit var color: Color
        lateinit var position: Position
        var scale by notNull<Double>()

        val composite = decoder.beginStructure(descriptor)
        while (true) {
            when (val index = composite.decodeElementIndex(descriptor)) {
                CompositeDecoder.DECODE_DONE -> break
                0 -> size = composite.decodeSerializableElement(descriptor, index, Size.serializer())
                1 -> color = composite.decodeSerializableElement(descriptor, index, Color.serializer())
                2 -> position = composite.decodeSerializableElement(descriptor, index, Position.serializer())
                3 -> scale = composite.decodeDoubleElement(descriptor, index)
                else -> throw SerializationException("Unknown index $index")
            }
        }

        composite.endStructure(descriptor)
        return RectangleComponent(size, color, position, scale)
    }
}

object ResourceComponentSerializer : KSerializer<ResourceComponent> {
    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("resource") {
        element<String>("id")
        element<Position>("pos")
        element<Double>("scale")
    }

    override fun serialize(encoder: Encoder, value: ResourceComponent) {
        val composite = encoder.beginStructure(descriptor)
        composite.encodeStringElement(descriptor, 0, value.identifier)
        composite.encodeSerializableElement(descriptor, 1, Position.serializer(), value.position)
        composite.encodeDoubleElement(descriptor, 2, value.scale)
        composite.endStructure(descriptor)
    }

    override fun deserialize(decoder: Decoder): ResourceComponent {
        lateinit var identifier: String
        lateinit var position: Position
        var scale by notNull<Double>()

        val composite = decoder.beginStructure(descriptor)
        while (true) {
            when (val index = composite.decodeElementIndex(descriptor)) {
                CompositeDecoder.DECODE_DONE -> break
                0 -> identifier = composite.decodeStringElement(descriptor, index)
                1 -> position = composite.decodeSerializableElement(descriptor, index, Position.serializer())
                3 -> scale = composite.decodeDoubleElement(descriptor, index)
                else -> throw SerializationException("Unknown index $index")
            }
        }

        composite.endStructure(descriptor)
        return ResourceComponent(identifier, position, scale)
    }
}

object TextComponentSerializer : KSerializer<TextComponent> {
    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("text") {
        element<String>("content")
        element<Color>("color")
        element<Position>("pos")
        element<List<String>>("vars")
        element<Double>("scale")
    }

    override fun serialize(encoder: Encoder, value: TextComponent) {
        val composite = encoder.beginStructure(descriptor)
        composite.encodeStringElement(descriptor, 0, value.content)
        composite.encodeSerializableElement(descriptor, 1, Color.serializer(), value.color)
        composite.encodeSerializableElement(descriptor, 2, Position.serializer(), value.position)
        composite.encodeSerializableElement(descriptor, 3, ListSerializer(String.serializer()), value.variables)
        composite.encodeDoubleElement(descriptor, 4, value.scale)
        composite.endStructure(descriptor)
    }

    override fun deserialize(decoder: Decoder): TextComponent {
        lateinit var content: String
        lateinit var color: Color
        lateinit var position: Position
        lateinit var variables: List<String>
        var scale by notNull<Double>()

        val composite = decoder.beginStructure(descriptor)
        while (true) {
            when (val index = composite.decodeElementIndex(descriptor)) {
                CompositeDecoder.DECODE_DONE -> break
                0 -> content = composite.decodeStringElement(descriptor, index)
                1 -> color = composite.decodeSerializableElement(descriptor, index, Color.serializer())
                2 -> position = composite.decodeSerializableElement(descriptor, index, Position.serializer())
                3 -> variables = composite.decodeSerializableElement(descriptor, index, ListSerializer(String.serializer()))
                4 -> scale = composite.decodeDoubleElement(descriptor, index)
                else -> throw SerializationException("Unknown index $index")
            }
        }

        composite.endStructure(descriptor)
        return TextComponent(content, color, variables, position, scale)
    }
}