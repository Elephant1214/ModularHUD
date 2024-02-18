package me.elephant1214.modularhud.api.parts

import kotlinx.serialization.Serializable

/**
 * A two-dimensional screen position
 */
@Serializable
data class Position(val x: Int, val y: Int)
