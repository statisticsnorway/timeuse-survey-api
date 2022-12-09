package no.ssb.timeusesurveyapi.common

import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity

data class ResponseWrapper (
    val status: HttpStatusCode,
    val payload: String?
) {
    internal fun asResponseEntity() = ResponseEntity<String>(payload, status)
}