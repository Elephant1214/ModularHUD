package me.elephant1214.modularhud.api.parts

import kotlinx.serialization.Serializable

/**
 * A two-dimensional size specification
 */
@Serializable
data class Size(val width: Int, val height: Int)
