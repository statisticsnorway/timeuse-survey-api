package no.ssb.timeusesurveyapi.companion

import java.util.*

internal fun getCompanionPath(respondentId: UUID) = "/v1/hangingwith/$respondentId"
internal fun getCompanionByIdPath(respondentId: UUID, id: String) = "/v1/hangingwith/$respondentId/$id"
internal fun deleteCompanionPath(respondentId: UUID) = "/v1/hangingwith/$respondentId"