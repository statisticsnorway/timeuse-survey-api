package no.ssb.timeusesurveyapi.questionnaire

import no.ssb.timeusesurveyapi.RequestType
import no.ssb.timeusesurveyapi.WebClientService
import org.springframework.stereotype.Service
import java.util.*

@Service
class QuestionnaireGateway(
    private val webClientService: WebClientService
) {

    internal fun getQuestionnaireByQuestionnaireType(
        respondentId: UUID,
        questionnaireType: QuestionnaireType,
        sessionTokenValue: String
    ) = webClientService.makeRequest(
        requestType = RequestType.GET,
        path = getQuestionnairePath(respondentId, questionnaireType),
        sessionTokenValue = sessionTokenValue
    )

    internal fun postQuestionnaire(
        respondentId: UUID,
        questionnaireType: QuestionnaireType,
        payload: String,
        sessionTokenValue: String
    ) = webClientService.makeRequestWithPayload(
        requestType = RequestType.POST,
        path = postQuestionnairePath(respondentId, questionnaireType),
        payload = payload,
        sessionTokenValue = sessionTokenValue
    )

}