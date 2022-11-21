package no.ssb.timeusesurveyapi.questionnaire

import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/v1/respondent/{respondent-id}/questionnaire")
class QuestionnaireController(
    private val gateway: QuestionnaireGateway
) {

    private val logger = LoggerFactory.getLogger(this::class.java)

    @GetMapping("/{questionnaire-type}")
    internal fun getQuestionnaireByQuestionnaireType(
        @PathVariable(value = "respondent-id") respondentId: UUID,
        @PathVariable("questionnaire-type") questionnaireType: QuestionnaireType
    ): ResponseEntity<String> {
        logger.info("Collecting questionnaire with type='$questionnaireType' for respondentId='$respondentId'")
        return gateway.getQuestionnaireByQuestionnaireType(respondentId, questionnaireType).asResponseEntity()
    }


    @PostMapping("/{questionnaire-type}")
    internal fun postQuestionnaire(
        @PathVariable(value = "respondent-id") respondentId: UUID,
        @PathVariable("questionnaire-type") questionnaireType: QuestionnaireType,
        @RequestBody payload: String
    ): ResponseEntity<String> {
        logger.info("Posting questionnaire with type='$questionnaireType' for respondentId='$respondentId'")
        return gateway.postQuestionnaire(respondentId, questionnaireType, payload).asResponseEntity()
    }
}