package no.ssb.timeusesurveyapi.timeuserespondent

import java.util.*

internal fun getTimeuseRespondentByIdPath(respondentId: UUID) = "/v1/respondents/$respondentId"
internal fun putTimeuseRespondentByIdPath(respondentId: UUID) = "/v1/respondents/$respondentId"
internal fun putTimeuseRespondentStatusByIdPath(respondentId: UUID, status: Status, value: String) = "/v1/respondents/$respondentId/$status/$value"