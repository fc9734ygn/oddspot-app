package util

class Event<T>(private val value: T) {
    private var handled = false

    fun get(): T? {
        if (!handled) {
            handled = true
            return value
        }
        return null
    }
}