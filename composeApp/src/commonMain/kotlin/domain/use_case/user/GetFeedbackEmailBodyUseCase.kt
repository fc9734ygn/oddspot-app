package domain.use_case.user

import domain.holder.UserHolder
import getPlatform
import org.koin.core.annotation.Factory

@Factory
class GetFeedbackEmailBodyUseCase(
    private val userHolder: UserHolder
) {
    operator fun invoke(): String {
        return "Hi there 👋,\n\n" +
                "Are you enjoying our app? 😊\n" +
                "\n" +
                "\n" +
                "Do you wish something to be implemented to enhance your experience? 🚀\n" +
                "\n" +
                "\n" +
                "How much would you be willing to pay for using the app per month? 💰\n" +
                "\n" +
                "\n" +
                "Has something annoyed you or was unclear? 🤔💢\n" +
                "\n" +
                "\n" +
                "Anything else? 🌈\n" +
                "\n" +
                "\n" +
                "UserId: ${userHolder.user?.userId} 🆔\n" +
                "Platform: ${getPlatform().name} 📱\n" +
                "".trimIndent()
    }
}