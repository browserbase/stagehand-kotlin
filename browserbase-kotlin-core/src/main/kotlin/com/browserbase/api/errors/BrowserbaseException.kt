package com.browserbase.api.errors

open class BrowserbaseException(message: String? = null, cause: Throwable? = null) :
    RuntimeException(message, cause)
