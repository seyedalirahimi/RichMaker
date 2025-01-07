package com.ali.richmaker.common

import javax.inject.Qualifier
import kotlin.annotation.AnnotationRetention.RUNTIME


@Qualifier
@Retention(RUNTIME)
annotation class Dispatcher(val richerMakerDispatchers: RichMakerDispatchers)

enum class RichMakerDispatchers {
    Default,
    IO,
}
