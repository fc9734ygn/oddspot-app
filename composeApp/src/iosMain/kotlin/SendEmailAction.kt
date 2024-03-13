
import platform.Foundation.NSURL
import platform.UIKit.UIApplication

actual fun sendEmailAction(
    to: String,
    subject: String,
    body: String
) {
    println("SendEmailAction: to: $to, subject: $subject, body: $body")

    val url = NSURL(string = "mailto:$to?subject=${subject.fixBrowserString()}&body=${body.fixBrowserString()}")
    if(UIApplication.sharedApplication.canOpenURL(url)) {
        UIApplication.sharedApplication.openURL(url)
    }
}

fun String.fixBrowserString(): String {
    //more symbols fixes here: https://mykindred.com/htmlspecialchars.php

    return this.replace(" ", "%20")
        .replace(";","%3B")
        .replace("\n", "%0D%0A")
        .replace(" ",  "+")
        .replace("!", "%21")
        .replace("\"", "%22")
        .replace("\\", "%5C")
        .replace("/", "%2F")
        .replace("‘", "%91")
        .replace(",", "%2C")
        .replace("’", "%92")
        .replace(":", "%3A")
        .replace("?", "%3F")
        .replace("@", "%40")
        .replace("'", "%27")
}
