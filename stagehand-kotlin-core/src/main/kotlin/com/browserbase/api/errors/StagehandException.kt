package com.browserbase.api.errors

open class StagehandException(message: String? = null, cause: Throwable? = null) :
    RuntimeException(message, cause)
