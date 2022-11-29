package no.ssb.timeusesurveyapi.diarystarthistory

import no.ssb.timeusesurveyapi.utils.getSessionToken
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.servlet.http.HttpServletRequest

@RestController
@RequestMapping("/v1/respondent/{respondent-id}/diary-start-history")
class DiaryStartHistoryController(
    private val gateway: DiaryStartHistoryGateway
) {
    private val logger = LoggerFactory.getLogger(this::class.java)

    @GetMapping
    internal fun getDiaryStartHistories(
        @PathVariable(value = "respondent-id") respondentId: UUID,
        request: HttpServletRequest
    ): ResponseEntity<String> {
        logger.info("Getting diary start histories for respondentId='$respondentId'")
        return gateway.getDiaryStartHistories(respondentId, request.getSessionToken()).asResponseEntity()
    }

    @PostMapping
    internal fun postDiaryStartHistories(
        @PathVariable(value = "respondent-id") respondentId: UUID,
        @RequestBody payload: String,
        request: HttpServletRequest
    ): ResponseEntity<String> {
        logger.info("Posting diary start history for respondentId='$respondentId'")
        return gateway.postDiaryStartHistory(respondentId, payload, request.getSessionToken()).asResponseEntity()
    }

    @DeleteMapping
    internal fun deleteDiaryStartHistories(
        @PathVariable(value = "respondent-id") respondentId: UUID,
        request: HttpServletRequest
    ): ResponseEntity<String> {
        logger.info("Deleting diary start history for respondentId='$respondentId'")
        return gateway.deleteDiaryStartHistory(respondentId, request.getSessionToken()).asResponseEntity()
    }

}