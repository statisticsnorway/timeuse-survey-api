package no.ssb.timeusesurveyapi.questionnaire

import no.ssb.timeusesurveyapi.RequestType
import no.ssb.timeusesurveyapi.ResponseWrapper
import no.ssb.timeusesurveyapi.WebClientService
import org.springframework.stereotype.Service
import java.util.*

@Service
class QuestionnaireGateway(
    private val webClientService: WebClientService
) {

    internal fun getQuestionnaireByQuestionnaireType(respondentId: UUID, questionnaireType: QuestionnaireType): ResponseWrapper {
        return webClientService.makeRequest(RequestType.GET, getQuestionnairePath(respondentId, questionnaireType))
    }

    internal fun postQuestionnaire(respondentId: UUID, questionnaireType: QuestionnaireType, payload: String): ResponseWrapper {
        return webClientService.makeRequestWithPayload(RequestType.POST, postQuestionnairePath(respondentId, questionnaireType), payload)
    }

}