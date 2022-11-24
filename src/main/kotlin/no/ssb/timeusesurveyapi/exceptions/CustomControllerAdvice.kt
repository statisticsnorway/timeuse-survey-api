package no.ssb.timeusesurveyapi.exceptions

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class CustomControllerAdvice {

    @ExceptionHandler(value = [MissingSessionTokenCookieException::class])
    internal fun handleMissingSessionTokenCookieException(): ResponseEntity<String> {
        return ResponseEntity("Missing sessionToken cookie", HttpStatus.FORBIDDEN)
    }
}

class MissingSessionTokenCookieException : Exception()