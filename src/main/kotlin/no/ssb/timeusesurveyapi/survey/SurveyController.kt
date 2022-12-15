package no.ssb.timeusesurveyapi.survey

import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/survey")
class SurveyController(
    private val gateway: SurveyGateway,
) {
    private val logger = LoggerFactory.getLogger(this::class.java)

    @GetMapping
    internal fun getSurvey(
        @RequestParam(required = false) abbr: String?
    ): ResponseEntity<String> {
        logger.info("Getting survey")
        return gateway.getSurvey(abbr).asResponseEntity()
    }
}