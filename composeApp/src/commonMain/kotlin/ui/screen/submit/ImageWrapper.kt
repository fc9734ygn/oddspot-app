package ui.screen.submit

data class ImageWrapper(val data: ByteArray) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null) return false
        if (this::class != other::class) return false

        other as ImageWrapper

        return data.contentEquals(other.data)
    }

    override fun hashCode(): Int = data.contentHashCode()
}

