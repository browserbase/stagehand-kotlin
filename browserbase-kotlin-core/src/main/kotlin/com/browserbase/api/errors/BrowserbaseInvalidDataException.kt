package com.browserbase.api.errors

class BrowserbaseInvalidDataException(message: String? = null, cause: Throwable? = null) :
    BrowserbaseException(message, cause)
