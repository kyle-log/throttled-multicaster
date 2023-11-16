package com.cocomo.library.throttle

import org.springframework.util.ErrorHandler

class AlwaysThrowErrorHandler : ErrorHandler {
    override fun handleError(t: Throwable) = throw t
}