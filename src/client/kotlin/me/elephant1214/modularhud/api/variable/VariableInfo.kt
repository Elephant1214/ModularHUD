package me.elephant1214.modularhud.api.variable

data class VariableInfo<T>(val value: T, val type: Class<out T>)
