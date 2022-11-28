package no.ssb.timeusesurveyapi.mainactivity

import no.ssb.timeusesurveyapi.utils.getSessionTokenValue
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.servlet.http.HttpServletRequest

@RestController
@RequestMapping("/v1/respondent/{respondent-id}/main-activity")
class MainActivityController(
    private val mainActivityGateway: MainActivityGateway
) {
    private val logger = LoggerFactory.getLogger(this::class.java)

    @GetMapping
    internal fun getMainActivities(
        @PathVariable(value = "respondent-id") respondentId: UUID,
        request: HttpServletRequest
    ): ResponseEntity<String> {
        logger.info("Collecting main activities for respondentId='$respondentId'")
        return mainActivityGateway.getMainActivities(respondentId, request.getSessionTokenValue()).asResponseEntity()
    }

    @GetMapping("/{activity-id}")
    internal fun getMainActivityById(
        @PathVariable(value = "respondent-id") respondentId: UUID,
        @PathVariable(value = "activity-id") activityId: String,
        request: HttpServletRequest
    ): ResponseEntity<String> {
        logger.info("Collecting main activity with id='$activityId' for respondentId='$respondentId'")
        return mainActivityGateway.getMainActivityById(respondentId, activityId, request.getSessionTokenValue()).asResponseEntity()
    }

    @GetMapping("/group-by-day")
    internal fun getMainActivitiesGroupByDay(
        @PathVariable(value = "respondent-id") respondentId: UUID,
        request: HttpServletRequest
    ): ResponseEntity<String> {
        logger.info("Collecting main activities grouped by day for respondentId='$respondentId'")
        return mainActivityGateway.getMainActivitiesGroupByDay(respondentId, request.getSessionTokenValue()).asResponseEntity()
    }

    @PostMapping
    internal fun postMainActivity(
        @PathVariable(value = "respondent-id") respondentId: UUID,
        @RequestBody payload: String,
        request: HttpServletRequest
    ): ResponseEntity<String> {
        logger.info("Posting main activity for respondentId='$respondentId'")
        return mainActivityGateway.postMainActivity(payload, request.getSessionTokenValue()).asResponseEntity()
    }

    @PostMapping("/activities")
    internal fun postMainActivities(
        @PathVariable(value = "respondent-id") respondentId: UUID,
        @RequestBody payload: String,
        request: HttpServletRequest
    ): ResponseEntity<String> {
        logger.info("Posting main activities for respondentId='$respondentId'")
        return mainActivityGateway.postMainActivities(respondentId, payload, request.getSessionTokenValue()).asResponseEntity()
    }

    @DeleteMapping
    internal fun deleteMainActivity(
        @PathVariable(value = "respondent-id") respondentId: UUID,
        request: HttpServletRequest
    ): ResponseEntity<String> {
        logger.info("Deleting main activity for respondentId='$respondentId'")
        return mainActivityGateway.deleteMainActivity(respondentId, request.getSessionTokenValue()).asResponseEntity()
    }

    @DeleteMapping("/{activity-id}")
    internal fun deleteMainActivityById(
        @PathVariable(value = "respondent-id") respondentId: UUID,
        @PathVariable(value = "activity-id") activityId: String,
        request: HttpServletRequest
    ): ResponseEntity<String> {
        logger.info("Deleting main activity with id='$activityId' for respondentId='$respondentId'")
        return mainActivityGateway.deleteMainActivityById(respondentId, activityId, request.getSessionTokenValue()).asResponseEntity()
    }

    @DeleteMapping("/start-time/{start-time}")
    internal fun deleteMainActivityByStartTime(
        @PathVariable(value = "respondent-id") respondentId: UUID,
        @PathVariable(value = "start-time") startTime: String,
        request: HttpServletRequest
    ): ResponseEntity<String> {
        logger.info("Deleting main activity by startTime='$startTime' for respondentId='$respondentId'")
        return mainActivityGateway.deleteMainActivityByStartTime(respondentId, startTime, request.getSessionTokenValue()).asResponseEntity()
    }

    @PatchMapping("/{activity-id}")
    internal fun updatehMainActivityById(
        @PathVariable(value = "respondent-id") respondentId: UUID,
        @PathVariable(value = "activity-id") activityId: String,
        @RequestBody payload: String,
        request: HttpServletRequest
    ): ResponseEntity<String> {
        logger.info("Updating main activity by activityId='$activityId' for respondentId='$respondentId'")
        return mainActivityGateway.updateMainActivityById(respondentId, activityId, payload, request.getSessionTokenValue()).asResponseEntity()
    }
}