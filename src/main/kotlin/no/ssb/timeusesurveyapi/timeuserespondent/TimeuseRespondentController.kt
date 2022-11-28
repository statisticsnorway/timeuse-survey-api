package no.ssb.timeusesurveyapi.timeuserespondent

import no.ssb.timeusesurveyapi.utils.getSessionToken
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.servlet.http.HttpServletRequest

@RestController
@RequestMapping("/v1/respondent")
class TimeuseRespondentController(
    private val gateway: TimeuseRespondentGateway
) {
    private val logger = LoggerFactory.getLogger(this::class.java)

    @GetMapping("/{respondent-id}")
    internal fun getTimseuseRespondentById(
        @PathVariable(value = "respondent-id") respondentId: UUID,
        request: HttpServletRequest
    ): ResponseEntity<String> {
        logger.info("Getting timeuse respondent with respondentId='respondentId'")
        return gateway.getTimseuseRespondentById(respondentId, request.getSessionToken()).asResponseEntity()
    }

    @PutMapping("/{respondent-id}")
    internal fun updateTimseuseRespondentById(
        @PathVariable(value = "respondent-id") respondentId: UUID,
        @RequestBody payload: String,
        request: HttpServletRequest
    ): ResponseEntity<String> {
        logger.info("Updating timeuse respondent with respondentId='respondentId'")
        return gateway.updateTimseuseRespondentById(respondentId, payload, request.getSessionToken()).asResponseEntity()
    }

    @PutMapping("/{respondent-id}/{status}/{value}")
    internal fun updateTimseuseRespondentStatusById(
        @PathVariable(value = "respondent-id") respondentId: UUID,
        @PathVariable(value = "status") status: Status,
        @PathVariable(value = "value") value: String,
        @RequestBody payload: String,
        request: HttpServletRequest
    ): ResponseEntity<String> {
        logger.info("Updating timeuse respondent status with respondentId='respondentId'")
        return gateway.updateTimseuseRespondentStatusById(respondentId, status, value, payload, request.getSessionToken()).asResponseEntity()
    }
}