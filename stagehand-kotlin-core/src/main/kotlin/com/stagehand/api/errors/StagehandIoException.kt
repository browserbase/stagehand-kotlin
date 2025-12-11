package com.stagehand.api.errors

class StagehandIoException(message: String? = null, cause: Throwable? = null) :
    StagehandException(message, cause)
