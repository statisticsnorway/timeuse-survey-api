package no.ssb.timeusesurveyapi.questionnaire

import java.util.*

internal fun getQuestionnairePath(respondentId: UUID, questionnaireType: QuestionnaireType) = "/v1/respondents/$respondentId/questionnaire/$questionnaireType"
internal fun postQuestionnairePath(respondentId: UUID, questionnaireType: QuestionnaireType) = "/v1/respondents/$respondentId/questionnaire/$questionnaireType"