package me.elephant1214.modularhud.api.part

data class Translation(
    val key: String,
    val replacements: Array<Any>
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Translation

        if (key != other.key) return false
        if (!replacements.contentEquals(other.replacements)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = key.hashCode()
        result = 31 * result + replacements.contentHashCode()
        return result
    }
}
