package com.stagehand.api.errors

open class StagehandException(message: String? = null, cause: Throwable? = null) :
    RuntimeException(message, cause)
