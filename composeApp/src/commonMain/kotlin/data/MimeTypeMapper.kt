package data

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import com.github.michaelbull.result.runCatching

@Suppress("MagicNumber")
object MimeTypeMapper {

    fun detectImageFormat(byteArray: ByteArray): Result<String, Throwable> = runCatching {
        val signatures = mapOf(
            "image/jpeg" to listOf(0xFF, 0xD8, 0xFF),
            "image/png" to listOf(0x89, 0x50, 0x4E, 0x47, 0x0D, 0x0A, 0x1A, 0x0A),
            "image/gif" to listOf(0x47, 0x49, 0x46, 0x38),
            "image/webp" to listOf(0x52, 0x49, 0x46, 0x46)
        )

        signatures.forEach { (format, signature) ->
            val bytesToCheck = byteArray.take(signature.size).map { it.toUByte().toInt() }
            if (signature == bytesToCheck) {
                return Ok(format)
            }
        }

        return Err(Throwable("Unknown image format"))
    }

    private fun List<Byte>.toIntArray(): IntArray = this.map { it.toInt() }.toIntArray()
}