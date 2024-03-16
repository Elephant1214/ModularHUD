@file:Suppress("DuplicatedCode")

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
import me.elephant1214.modularhud.api.component.condition.ItemCountCondition
import me.elephant1214.modularhud.api.component.condition.ItemDurabilityCondition
import me.elephant1214.modularhud.api.part.ItemLocation
import me.elephant1214.modularhud.api.part.ItemLocation.*
import me.elephant1214.modularhud.variable.VariableManager
import kotlin.properties.Delegates.notNull

object ItemCountConditionSerializer : KSerializer<ItemCountCondition> {
    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("count") {
        element<ItemLocation>("loc")
        element<String?>("var")
        element<Int>("thresh")
    }

    override fun serialize(encoder: Encoder, value: ItemCountCondition) {
        val composite = encoder.beginStructure(descriptor)
        composite.encodeSerializableElement(descriptor, 0, ItemLocation.serializer(), value.location)
        composite.encodeNullableSerializableElement(descriptor, 1, String.serializer(), value.variable)
        composite.encodeIntElement(descriptor, 2, value.threshold)
        composite.endStructure(descriptor)
    }

    override fun deserialize(decoder: Decoder): ItemCountCondition {
        lateinit var location: ItemLocation
        var variable: String? = null
        var threshold by notNull<Int>()

        val composite = decoder.beginStructure(descriptor)
        while (true) {
            when (val index = composite.decodeElementIndex(descriptor)) {
                CompositeDecoder.DECODE_DONE -> break
                0 -> {
                    location = composite.decodeSerializableElement(descriptor, index, ItemLocation.serializer())
                    if (location == INVENTORY_DURABILITY || location == FAKE) {
                        error("INVENTORY_DURABILITY and FAKE may not be used with an item count condition")
                    }
                }

                1 -> {
                    variable = composite.decodeNullableSerializableElement(descriptor, index, String.serializer())
                    if (variable == null && location != OFFHAND) {
                        error("You must provide a value for the variable (`var`) when using $location!")
                    } else if (variable != null) {
                        if (location == INVENTORY && !VariableManager.cacheItem(variable)) {
                            error("Unable to find an item named \"$variable\"")
                        }
                    }
                }

                2 -> threshold = composite.decodeIntElement(descriptor, index)
                else -> throw SerializationException("Unknown index $index")
            }
        }

        composite.endStructure(descriptor)
        return ItemCountCondition(location, variable, threshold)
    }
}

object ItemDurabilityConditionSerializer : KSerializer<ItemDurabilityCondition> {
    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("durability") {
        element<ItemLocation>("loc")
        element<String?>("var")
        element<Int>("thresh")
    }

    override fun serialize(encoder: Encoder, value: ItemDurabilityCondition) {
        val composite = encoder.beginStructure(descriptor)
        composite.encodeSerializableElement(descriptor, 0, ItemLocation.serializer(), value.location)
        composite.encodeNullableSerializableElement(descriptor, 1, String.serializer(), value.variable)
        composite.encodeIntElement(descriptor, 2, value.threshold)
        composite.endStructure(descriptor)
    }

    override fun deserialize(decoder: Decoder): ItemDurabilityCondition {
        lateinit var location: ItemLocation
        var variable: String? = null
        var threshold by notNull<Int>()

        val composite = decoder.beginStructure(descriptor)
        while (true) {
            when (val index = composite.decodeElementIndex(descriptor)) {
                CompositeDecoder.DECODE_DONE -> break
                0 -> {
                    location = composite.decodeSerializableElement(descriptor, index, ItemLocation.serializer())
                    if (location == INVENTORY || location == FAKE) {
                        error("INVENTORY and FAKE may not be used as an item location in an item count condition")
                    }
                }

                1 -> {
                    variable = composite.decodeNullableSerializableElement(descriptor, index, String.serializer())
                    if (variable == null && location != HOT_BAR) {
                        error("You must provide a value for the variable (`var`) when using $location!")
                    } else if (variable != null) {
                        if (location == INVENTORY_DURABILITY && !VariableManager.cacheItem(variable)) {
                            error("Unable to find an item named \"$variable\"")
                        }
                    }
                }

                2 -> threshold = composite.decodeIntElement(descriptor, index)
                else -> throw SerializationException("Unknown index $index")
            }
        }

        composite.endStructure(descriptor)
        return ItemDurabilityCondition(location, variable, threshold)
    }
}
