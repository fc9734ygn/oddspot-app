
import android.content.ActivityNotFoundException
import android.content.Intent
import android.widget.Toast

actual fun sendEmailAction(
    to: String,
    subject: String,
    body: String
) {
    appContext.run {
        try {
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "vnd.android.cursor.item/email" // or "message/rfc822"
            intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(to))
            intent.putExtra(Intent.EXTRA_SUBJECT, subject)
            intent.putExtra(Intent.EXTRA_TEXT, body)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        } catch (e: ActivityNotFoundException) { // if no email app is available
            Toast.makeText(this, "Cannot find email app", Toast.LENGTH_SHORT)
                .show()
        } catch (t: Throwable) {
            Toast.makeText(this, "Oops! Something went wrong.", Toast.LENGTH_SHORT)
                .show()
        }
    }
}