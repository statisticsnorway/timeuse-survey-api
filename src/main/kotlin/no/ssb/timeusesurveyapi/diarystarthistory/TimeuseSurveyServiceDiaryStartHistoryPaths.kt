package no.ssb.timeusesurveyapi.diarystarthistory

import java.util.*

internal fun getDiaryStartHistoriesPath(respondentId: UUID) = "/v1/diary-start-histories?respondentId=$respondentId"
internal fun postDiaryStartHistoriesPath() = "/v1/diary-start-histories"
internal fun deleteDiaryStartHistoriesPath(respondentId: UUID) = "/v1/diary-start-histories/respondent/$respondentId"
