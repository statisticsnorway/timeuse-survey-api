package no.ssb.timeusesurveyapi.survey

import no.ssb.timeusesurveyapi.common.RequestType.GET
import no.ssb.timeusesurveyapi.common.RequestWrapper
import no.ssb.timeusesurveyapi.common.WebClientService
import org.springframework.stereotype.Service

@Service
class SurveyGateway(
    private val service: WebClientService,
) {
    internal fun getSurvey(abbr: String?) = service.makeRequest(RequestWrapper(GET, getSurveyPath(abbr)))
}

internal fun getSurveyPath(abbr: String?) = "/v1/surveys?abbr=$abbr"

