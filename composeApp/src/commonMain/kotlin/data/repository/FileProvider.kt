package data.repository

import com.github.michaelbull.result.Result
import org.koin.core.annotation.Singleton

@Singleton
class FileProvider(
//    val context: Context,
) {

    fun readBytesFromUri(
        uri: String
    ): Result<ByteArray, Throwable> = TODO()
//        runCatching {
//            context.contentResolver.openInputStream(uri.toUri())?.use { inputStream ->
//                inputStream.readBytes()
//            } ?: throw Exception("Could not read bytes from uri")
//        }

    fun getMimeType(uri: String): Result<String, Throwable> = TODO()
//        runCatching {
//            val androidUri = uri.toUri()
//            return@runCatching if (androidUri.scheme == ContentResolver.SCHEME_CONTENT) {
//                context.contentResolver.getType(androidUri)!!
//            } else {
//                val fileExtension = MimeTypeMap.getFileExtensionFromUrl(androidUri.toString())
//                MimeTypeMap
//                    .getSingleton()
//                    .getMimeTypeFromExtension(fileExtension.lowercase(Locale.getDefault()))!!
//            }
//        }
}