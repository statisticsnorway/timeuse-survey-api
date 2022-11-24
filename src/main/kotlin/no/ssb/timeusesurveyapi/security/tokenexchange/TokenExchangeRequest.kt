package no.ssb.timeusesurveyapi.security.tokenexchange

import java.util.*

data class TokenExchangeRequest(
    val accessToken: String,
    val idToken: String,
    val respondentId: UUID
)