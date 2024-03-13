package domain.use_case.user

import domain.holder.UserHolder
import getPlatform
import org.koin.core.annotation.Factory

@Factory
class GetFeedbackEmailBodyUseCase(
    private val userHolder: UserHolder
) {
    operator fun invoke() : String {
        return "UserId: ${userHolder.user?.userId}\n" +
               "Platform: ${getPlatform().name}\n" +
                "\n" +
                "Please provide feedback here:\n" +
                "".trimIndent()
    }
}