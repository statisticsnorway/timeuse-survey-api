package no.ssb.timeusesurveyapi.questionnaire

import no.ssb.timeusesurveyapi.common.*
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
            RequestType.GET,
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
            requestType = RequestType.POST,
            path = postQuestionnairePath(respondentId, questionnaireType),
            payload = payload,
            sessionToken = sessionToken
        )
    )

}