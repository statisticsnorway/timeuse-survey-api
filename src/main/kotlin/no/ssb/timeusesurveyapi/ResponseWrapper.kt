package no.ssb.timeusesurveyapi

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

data class ResponseWrapper (
    val status: HttpStatus,
    val payload: String?
) {
    internal fun asResponseEntity() = ResponseEntity<String>(payload, status)
}