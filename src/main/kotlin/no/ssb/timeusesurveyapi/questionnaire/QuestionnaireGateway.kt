package no.ssb.timeusesurveyapi.questionnaire

import no.ssb.timeusesurveyapi.common.RequestType.GET
import no.ssb.timeusesurveyapi.common.RequestType.POST
import no.ssb.timeusesurveyapi.common.RequestWrapper
import no.ssb.timeusesurveyapi.common.RequestWrapperWithPayload
import no.ssb.timeusesurveyapi.common.SessionToken
import no.ssb.timeusesurveyapi.common.WebClientService
import org.springframework.stereotype.Service
import java.util.*

@Service
class QuestionnaireGateway(
    private val webClientService: WebClientService
) {

    internal fun getQuestionnaireByQuestionnaireType(
        respondentId: UUID,
        questionnaireType: QuestionnaireType,
        sessionToken: SessionToken
    ) = webClientService.makeRequest(
        RequestWrapper(
            GET,
            getQuestionnairePath(respondentId, questionnaireType),
            sessionToken
        )
    )

    internal fun postQuestionnaire(
        respondentId: UUID,
        questionnaireType: QuestionnaireType,
        payload: String,
        sessionToken: SessionToken
    ) = webClientService.makeRequestWithPayload(
        RequestWrapperWithPayload(
            requestType = POST,
            path = postQuestionnairePath(respondentId, questionnaireType),
            payload = payload,
            sessionToken = sessionToken
        )
    )
}

internal fun getQuestionnairePath(respondentId: UUID, questionnaireType: QuestionnaireType) = "/v1/respondents/$respondentId/questionnaire/$questionnaireType"
internal fun postQuestionnairePath(respondentId: UUID, questionnaireType: QuestionnaireType) = "/v1/respondents/$respondentId/questionnaire/$questionnaireType"