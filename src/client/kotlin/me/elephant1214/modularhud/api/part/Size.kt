package me.elephant1214.modularhud.api.part

import kotlinx.serialization.Serializable

/**
 * A two-dimensional size specification
 */
@Serializable
data class Size(
    val width: Int,
    val height: Int
)
