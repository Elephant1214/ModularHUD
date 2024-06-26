package me.elephant1214.modularhud.api.part

import kotlinx.serialization.Serializable

/**
 * A two-dimensional screen position
 */
@Serializable
data class Position(
    val x: Int = 0,
    val y: Int = 0
)
