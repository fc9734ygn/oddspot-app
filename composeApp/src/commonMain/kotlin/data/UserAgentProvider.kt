package data

import getPlatform

fun getUserAgent(): String {
    val platform = getPlatform()
    return "oddspot-app(${platform.name})"
}
