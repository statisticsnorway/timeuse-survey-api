package no.ssb.timeusesurveyapi.domain.companion

import jakarta.servlet.http.HttpServletRequest
import no.ssb.timeusesurveyapi.utils.getSessionToken
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/v1/respondent/{respondent-id}/companion")
class CompanionController(
    private val gateway: CompanionGateway
) {
    private val logger = LoggerFactory.getLogger(this::class.java)

    @GetMapping
    internal fun getCompanions(
        @PathVariable(value = "respondent-id") respondentId: UUID,
        request: HttpServletRequest
    ): ResponseEntity<String> {
        logger.info("Getting companions for respondentId='$respondentId'")
        return gateway.getCompanions(respondentId, request.getSessionToken()).asResponseEntity()
    }

    @GetMapping("/{id}")
    internal fun getCompanionById(
        @PathVariable(value = "respondent-id") respondentId: UUID,
        @PathVariable(value = "id") id: String,
        request: HttpServletRequest
    ): ResponseEntity<String> {
        logger.info("Getting companion with id='$id' for respondentId='$respondentId'")
        return gateway.getCompanionById(respondentId, id,  request.getSessionToken()).asResponseEntity()
    }

    @DeleteMapping
    internal fun deleteCompanion(
        @PathVariable(value = "respondent-id") respondentId: UUID,
        request: HttpServletRequest
    ): ResponseEntity<String> {
        logger.info("Deleting companions for respondentId='$respondentId'")
        return gateway.deleteCompanions(respondentId, request.getSessionToken()).asResponseEntity()
    }
}