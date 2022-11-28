package no.ssb.timeusesurveyapi.common

enum class RequestType { GET, POST, DELETE, PUT, PATCH }

data class RequestWrapperWithPayload (
    val requestType: RequestType,
    val path: String,
    val payload: String,
    val sessionToken: SessionToken
)

data class RequestWrapper (
    val requestType: RequestType,
    val path: String,
    val sessionToken: SessionToken
)