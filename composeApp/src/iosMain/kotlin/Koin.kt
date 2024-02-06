import di.SharedModule
import di.networkModule
import org.koin.core.context.startKoin
import org.koin.dsl.module
import org.koin.ksp.generated.module

fun initKoin() {
    startKoin {
        modules(
            SharedModule().module,
            networkModule,
            module {
                single { DriverFactory() }
                single { createDatabase(get()) }
            }
        )
    }
}