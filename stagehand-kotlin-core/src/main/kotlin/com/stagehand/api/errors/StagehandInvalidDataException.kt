package com.stagehand.api.errors

class StagehandInvalidDataException(message: String? = null, cause: Throwable? = null) :
    StagehandException(message, cause)
