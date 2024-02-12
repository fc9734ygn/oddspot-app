package di

import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module

/*
    Separating the scanned module in to multiple
    as Koin is unable to scan the whole directory
 */

@Module
@ComponentScan("ui")
class UiModule

@Module
@ComponentScan("domain")
class DomainModule

@Module
@ComponentScan("data")
class DataModule