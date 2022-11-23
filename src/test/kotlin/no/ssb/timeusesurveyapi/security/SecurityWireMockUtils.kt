package no.ssb.timeusesurveyapi.security

import com.github.tomakehurst.wiremock.client.WireMock

internal fun stubTokenExchange(statusCode: Int = 200, cookie: String = "key=value") {
    WireMock.stubFor(
        WireMock.post(WireMock.urlPathMatching("/v1/token-exchange"))
            .willReturn(
                WireMock.aResponse().withStatus(statusCode).withHeader("Content-Type", "application/json")
                    .withHeader("Set-cookie", cookie)
            )
    )
}