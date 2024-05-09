package ui.screen.policy

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.mikepenz.markdown.compose.Markdown
import com.mikepenz.markdown.model.DefaultMarkdownColors
import com.mikepenz.markdown.model.DefaultMarkdownTypography
import oddspot_app.composeapp.generated.resources.Res
import oddspot_app.composeapp.generated.resources.ic_arrow_back
import oddspot_app.composeapp.generated.resources.privacy_policy_title
import oddspot_app.composeapp.generated.resources.terms_of_service_title
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import ui.util.Colors
import ui.util.body
import ui.util.bullet
import ui.util.code
import ui.util.h1
import ui.util.h2
import ui.util.h3
import ui.util.h4
import ui.util.h5
import ui.util.h6
import ui.util.list
import ui.util.ordered
import ui.util.paragraph
import ui.util.quote

class PolicyScreen : Screen {

    @OptIn(ExperimentalResourceApi::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        Column(
            modifier = Modifier
                .background(color = Colors.background)
                .padding(bottom = 64.dp)
                .padding(horizontal = 24.dp)
                .verticalScroll(rememberScrollState())
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 32.dp),
                horizontalArrangement = Arrangement.Start,
            ) {
                Icon(
                    painter = painterResource(Res.drawable.ic_arrow_back),
                    contentDescription = null,
                    tint = Colors.darkGrey,
                    modifier = Modifier.clickable { navigator.pop() }
                )
            }
            Text(
                style = h3(),
                color = Colors.white,
                text = stringResource(Res.string.privacy_policy_title)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Markdown(
                content = getPrivacyPolicyText(),
                colors = DefaultMarkdownColors(
                    text = Colors.white,
                    codeText = Colors.white,
                    linkText = Colors.white,
                    codeBackground = Colors.background,
                    inlineCodeBackground = Colors.background,
                    dividerColor = Colors.white
                ),
                typography = DefaultMarkdownTypography(
                    h1 = h1(),
                    h2 = h2(),
                    h3 = h3(),
                    h4 = h4(),
                    h5 = h5(),
                    h6 = h6(),
                    text = body(),
                    code = code(),
                    quote = quote(),
                    paragraph = paragraph(),
                    ordered = ordered(),
                    bullet = bullet(),
                    list = list()
                )
            )
            Spacer(modifier = Modifier.height(32.dp))
            Text(
                style = h3(),
                color = Colors.white,
                text = stringResource(Res.string.terms_of_service_title)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Markdown(
                content = getTermsAndConditionsText(),
                colors = DefaultMarkdownColors(
                    text = Colors.white,
                    codeText = Colors.white,
                    linkText = Colors.white,
                    codeBackground = Colors.background,
                    inlineCodeBackground = Colors.background,
                    dividerColor = Colors.white
                ),
                typography = DefaultMarkdownTypography(
                    h1 = h1(),
                    h2 = h2(),
                    h3 = h3(),
                    h4 = h4(),
                    h5 = h5(),
                    h6 = h6(),
                    text = body(),
                    code = code(),
                    quote = quote(),
                    paragraph = paragraph(),
                    ordered = ordered(),
                    bullet = bullet(),
                    list = list()
                )
            )
        }
    }
}

@Suppress("LongMethod", "MaxLineLength")
private fun getPrivacyPolicyText() =
        "This privacy policy is applicable to the Oddspot app (hereinafter referred to as \"Application\") for mobile devices, which was developed by MB Homato (hereinafter referred to as \"Service Provider\") as a a Commercial service. This service is provided \"AS IS\".\n" +
        "\n" +
        "**What information does the Application obtain and how is it used?**  \n" +
        "**User Provided Information**\n" +
        "\n" +
        "The Application acquires the information you supply when you download and register the Application. Registration with the Service Provider is not mandatory. However, bear in mind that you might not be able to utilize some of the features offered by the Application unless you register with them.\n" +
        "\n" +
        "The Service Provider may also use the information you provided them to contact you from time to time to provide you with important information, required notices and marketing promotions.\n" +
        "\n" +
        "**Automatically Collected Information**\n" +
        "\n" +
        "In addition, the Application may collect certain information automatically, including, but not limited to, the type of mobile device you use, your mobile devices unique device ID, the IP address of your mobile device, your mobile operating system, the type of mobile Internet browsers you use, and information about the way you use the Application.\n" +
        "\n" +
        "**Does the Application collect precise real time location information of the device?**\n" +
        "\n" +
        "This Application does not gather precise information about the location of your mobile device.\n" +
        "\n" +
        "This Application collects your device's location, which helps the Service Provider determine your approximate geographical location and make use of in below ways:\n" +
        "\n" +
        "*   Geolocation Services: The Service Provider utilizes location data to provide features such as personalized content, relevant recommendations, and location-based services.\n" +
        "*   Analytics and Improvements: Aggregated and anonymized location data helps the Service Provider to analyze user behavior, identify trends, and improve the overall performance and functionality of the Application.\n" +
        "*   Third-Party Services: Periodically, the Service Provider may transmit anonymized location data to external services. These services assist them in enhancing the Application and optimizing their offerings.\n" +
        "\n" +
        "**Do third parties see and/or have access to information obtained by the Application?**\n" +
        "\n" +
        "Only aggregated, anonymized data is periodically transmitted to external services to aid the Service Provider in improving the Application and their service. The Service Provider may share your information with third parties in the ways that are described in this privacy statement.\n" +
        "\n" +
        "Please note that the Application utilizes third-party services that have their own Privacy Policy about handling data. Below are the links to the Privacy Policy of the third-party service providers used by the Application:\n" +
        "\n" +
        "*   [Google Play Services](https://www.google.com/policies/privacy/)\n" +
        "*   [Google Analytics for Firebase](https://firebase.google.com/support/privacy)\n" +
        "*   [Firebase Crashlytics](https://firebase.google.com/support/privacy/)\n" +
        "\n" +
        "The Service Provider may disclose User Provided and Automatically Collected Information:\n" +
        "\n" +
        "*   as required by law, such as to comply with a subpoena, or similar legal process;\n" +
        "*   when they believe in good faith that disclosure is necessary to protect their rights, protect your safety or the safety of others, investigate fraud, or respond to a government request;\n" +
        "*   with their trusted services providers who work on their behalf, do not have an independent use of the information we disclose to them, and have agreed to adhere to the rules set forth in this privacy statement.\n" +
        "\n" +
        "**What are my opt-out rights?**\n" +
        "\n" +
        "You can halt all collection of information by the Application easily by uninstalling the Application. You may use the standard uninstall processes as may be available as part of your mobile device or via the mobile application marketplace or network.\n" +
        "\n" +
        "**Data Retention Policy, Managing Your Information**\n" +
        "\n" +
        "The Service Provider will retain User Provided data for as long as you use the Application and for a reasonable time thereafter. The Service Provider will retain Automatically Collected information for up to 24 months and thereafter may store it in aggregate. If you'd like the Service Provider to delete User Provided Data that you have provided via the Application, please contact them at info@oddspot.eu and we will respond in a reasonable time. Please note that some or all of the User Provided Data may be required in order for the Application to function properly.\n" +
        "\n" +
        "**Children**\n" +
        "\n" +
        "The Service Provider does not use the Application to knowingly solicit data from or market to children under the age of 13.\n" +
        "\n" +
        "The Application does not address anyone under the age of 13\\. The Service Provider does not knowingly collect personally identifiable information from children under 13 years of age. In the case the Service Provider discover that a child under 13 has provided personal information, the Service Provider will immediately delete this from their servers. If you are a parent or guardian and you are aware that your child has provided us with personal information, please contact the Service Provider (info@oddspot.eu) so that they will be able to take the necessary actions.\n" +
        "\n" +
        "**Security**\n" +
        "\n" +
        "The Service Provider are concerned about safeguarding the confidentiality of your information. The Service Provider provide physical, electronic, and procedural safeguards to protect information we process and maintain. For example, we limit access to this information to authorized employees and contractors who need to know that information in order to operate, develop or improve their Application. Please be aware that, although we endeavor provide reasonable security for information we process and maintain, no security system can prevent all potential security breaches.\n" +
        "\n" +
        "**Changes**\n" +
        "\n" +
        "This Privacy Policy may be updated from time to time for any reason. The Service Provider will notify you of any changes to the Privacy Policy by updating this page with the new Privacy Policy. You are advised to consult this Privacy Policy regularly for any changes, as continued use is deemed approval of all changes.\n" +
        "\n" +
        "This privacy policy is effective as of 2024-03-22\n" +
        "\n" +
        "**Your Consent**\n" +
        "\n" +
        "By using the Application, you are giving your consent to the Service Provider processing of your information as set forth in this Privacy Policy now and as amended by us. \"Processing,” means using cookies on a computer/hand held device or using or touching information in any way, including, but not limited to, collecting, storing, deleting, using, combining and disclosing information.\n" +
        "\n" +
        "**Contact us**\n" +
        "\n" +
        "If you have any questions regarding privacy while using the Application, or have questions about the practices, please contact the Service Provider via email at info@oddspot.eu.\n" +
        "\n" +
        "* * *\n"

@Suppress("LongMethod", "MaxLineLength")
private fun getTermsAndConditionsText() =
        "These terms and conditions applies to the Oddspot app (hereby referred to as \"Application\") for mobile devices that was created by MB Homato (hereby referred to as \"Service Provider\") as a Commercial service.\n" +
        "\n" +
        "Upon downloading or utilizing the Application, you are automatically agreeing to the following terms. It is strongly advised that you thoroughly read and understand these terms prior to using the Application. Unauthorized copying, modification of the Application, any part of the Application, or our trademarks is strictly prohibited. Any attempts to extract the source code of the Application, translate the Application into other languages, or create derivative versions are not permitted. All trademarks, copyrights, database rights, and other intellectual property rights related to the Application remain the property of the Service Provider.\n" +
        "\n" +
        "The Service Provider is dedicated to ensuring that the Application is as beneficial and efficient as possible. As such, they reserve the right to modify the Application or charge for their services at any time and for any reason. The Service Provider assures you that any charges for the Application or its services will be clearly communicated to you.\n" +
        "\n" +
        "The Application stores and processes personal data that you have provided to the Service Provider in order to provide the Service. It is your responsibility to maintain the security of your phone and access to the Application. The Service Provider strongly advise against jailbreaking or rooting your phone, which involves removing software restrictions and limitations imposed by the official operating system of your device. Such actions could expose your phone to malware, viruses, malicious programs, compromise your phone's security features, and may result in the Application not functioning correctly or at all.\n" +
        "\n" +
        "Please note that the Application utilizes third-party services that have their own Terms and Conditions. Below are the links to the Terms and Conditions of the third-party service providers used by the Application:\n" +
        "\n" +
        "*   [Google Play Services](https://policies.google.com/terms)\n" +
        "*   [Google Analytics for Firebase](https://www.google.com/analytics/terms/)\n" +
        "*   [Firebase Crashlytics](https://firebase.google.com/terms/crashlytics)\n" +
        "\n" +
        "Please be aware that the Service Provider does not assume responsibility for certain aspects. Some functions of the Application require an active internet connection, which can be Wi-Fi or provided by your mobile network provider. The Service Provider cannot be held responsible if the Application does not function at full capacity due to lack of access to Wi-Fi or if you have exhausted your data allowance.\n" +
        "\n" +
        "If you are using the application outside of a Wi-Fi area, please be aware that your mobile network provider's agreement terms still apply. Consequently, you may incur charges from your mobile provider for data usage during the connection to the application, or other third-party charges. By using the application, you accept responsibility for any such charges, including roaming data charges if you use the application outside of your home territory (i.e., region or country) without disabling data roaming. If you are not the bill payer for the device on which you are using the application, they assume that you have obtained permission from the bill payer.\n" +
        "\n" +
        "Similarly, the Service Provider cannot always assume responsibility for your usage of the application. For instance, it is your responsibility to ensure that your device remains charged. If your device runs out of battery and you are unable to access the Service, the Service Provider cannot be held responsible.\n" +
        "\n" +
        "In terms of the Service Provider's responsibility for your use of the application, it is important to note that while they strive to ensure that it is updated and accurate at all times, they do rely on third parties to provide information to them so that they can make it available to you. The Service Provider accepts no liability for any loss, direct or indirect, that you experience as a result of relying entirely on this functionality of the application.\n" +
        "\n" +
        "The Service Provider may wish to update the application at some point. The application is currently available as per the requirements for the operating system (and for any additional systems they decide to extend the availability of the application to) may change, and you will need to download the updates if you want to continue using the application. The Service Provider does not guarantee that it will always update the application so that it is relevant to you and/or compatible with the particular operating system version installed on your device. However, you agree to always accept updates to the application when offered to you. The Service Provider may also wish to cease providing the application and may terminate its use at any time without providing termination notice to you. Unless they inform you otherwise, upon any termination, (a) the rights and licenses granted to you in these terms will end; (b) you must cease using the application, and (if necessary) delete it from your device.\n" +
        "\n" +
        "**Changes to These Terms and Conditions**\n" +
        "\n" +
        "The Service Provider may periodically update their Terms and Conditions. Therefore, you are advised to review this page regularly for any changes. The Service Provider will notify you of any changes by posting the new Terms and Conditions on this page.\n" +
        "\n" +
        "These terms and conditions are effective as of 2024-03-22\n" +
        "\n" +
        "**Contact Us**\n" +
        "\n" +
        "If you have any questions or suggestions about the Terms and Conditions, please do not hesitate to contact the Service Provider at info@oddspot.eu.\n" +
        "\n" +
        "* * *\n"