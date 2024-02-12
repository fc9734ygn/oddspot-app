import di.DataModule
import di.DomainModule
import di.UiModule
import di.networkModule
import org.koin.core.context.startKoin
import org.koin.dsl.module
import org.koin.ksp.generated.module

fun initKoin() {
    startKoin {
        modules(
            UiModule().module,
            DomainModule().module,
            DataModule().module,
            networkModule,
            module {
                single { DriverFactory() }
                single { createDatabase(get()) }
            }
        )
    }
}