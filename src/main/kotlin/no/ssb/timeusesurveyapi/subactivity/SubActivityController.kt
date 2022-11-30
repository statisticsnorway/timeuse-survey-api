package no.ssb.timeusesurveyapi.subactivity

import no.ssb.timeusesurveyapi.utils.getSessionToken
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.servlet.http.HttpServletRequest

@RestController
@RequestMapping("/v1/respondent/{respondent-id}/sub-activity")
class SubActivityController(
    private val gateway: SubActivityGateway
) {
    private val logger = LoggerFactory.getLogger(this::class.java)

    @PostMapping
    internal fun postSubActivity(
        @PathVariable(value = "respondent-id") respondentId: UUID,
        @RequestBody payload: String,
        request: HttpServletRequest
    ): ResponseEntity<String> {
        logger.info("Posting sub activity for respondentId='$respondentId'")
        return gateway.postSubActivity(respondentId, payload, request.getSessionToken()).asResponseEntity()
    }
}