package no.ssb.timeusesurveyapi.domain.mainactivity

import jakarta.servlet.http.HttpServletRequest
import no.ssb.timeusesurveyapi.utils.getSessionToken
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

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
        return mainActivityGateway.getMainActivities(respondentId, request.getSessionToken()).asResponseEntity()
    }

    @GetMapping("/{activity-id}")
    internal fun getMainActivityById(
        @PathVariable(value = "respondent-id") respondentId: UUID,
        @PathVariable(value = "activity-id") activityId: String,
        request: HttpServletRequest
    ): ResponseEntity<String> {
        logger.info("Collecting main activity with id='$activityId' for respondentId='$respondentId'")
        return mainActivityGateway.getMainActivityById(respondentId, activityId, request.getSessionToken()).asResponseEntity()
    }

    @GetMapping("/group-by-day")
    internal fun getMainActivitiesGroupByDay(
        @PathVariable(value = "respondent-id") respondentId: UUID,
        request: HttpServletRequest
    ): ResponseEntity<String> {
        logger.info("Collecting main activities grouped by day for respondentId='$respondentId'")
        return mainActivityGateway.getMainActivitiesGroupByDay(respondentId, request.getSessionToken()).asResponseEntity()
    }

    @PostMapping
    internal fun postMainActivity(
        @PathVariable(value = "respondent-id") respondentId: UUID,
        @RequestBody payload: String,
        request: HttpServletRequest
    ): ResponseEntity<String> {
        logger.info("Posting main activity for respondentId='$respondentId'")
        return mainActivityGateway.postMainActivity(respondentId, payload, request.getSessionToken()).asResponseEntity()
    }

    @PostMapping("/activities")
    internal fun postMainActivities(
        @PathVariable(value = "respondent-id") respondentId: UUID,
        @RequestBody payload: String,
        request: HttpServletRequest
    ): ResponseEntity<String> {
        logger.info("Posting main activities for respondentId='$respondentId'")
        return mainActivityGateway.postMainActivities(respondentId, payload, request.getSessionToken()).asResponseEntity()
    }

    @DeleteMapping
    internal fun deleteMainActivity(
        @PathVariable(value = "respondent-id") respondentId: UUID,
        request: HttpServletRequest
    ): ResponseEntity<String> {
        logger.info("Deleting main activity for respondentId='$respondentId'")
        return mainActivityGateway.deleteMainActivity(respondentId, request.getSessionToken()).asResponseEntity()
    }

    @DeleteMapping("/{activity-id}")
    internal fun deleteMainActivityById(
        @PathVariable(value = "respondent-id") respondentId: UUID,
        @PathVariable(value = "activity-id") activityId: String,
        request: HttpServletRequest
    ): ResponseEntity<String> {
        logger.info("Deleting main activity with id='$activityId' for respondentId='$respondentId'")
        return mainActivityGateway.deleteMainActivityById(respondentId, activityId, request.getSessionToken()).asResponseEntity()
    }

    @DeleteMapping("/start-time/{start-time}")
    internal fun deleteMainActivityByStartTime(
        @PathVariable(value = "respondent-id") respondentId: UUID,
        @PathVariable(value = "start-time") startTime: String,
        request: HttpServletRequest
    ): ResponseEntity<String> {
        logger.info("Deleting main activity by startTime='$startTime' for respondentId='$respondentId'")
        return mainActivityGateway.deleteMainActivityByStartTime(respondentId, startTime, request.getSessionToken()).asResponseEntity()
    }

    @PatchMapping("/{activity-id}")
    internal fun updatehMainActivityById(
        @PathVariable(value = "respondent-id") respondentId: UUID,
        @PathVariable(value = "activity-id") activityId: String,
        @RequestBody payload: String,
        request: HttpServletRequest
    ): ResponseEntity<String> {
        logger.info("Updating main activity by activityId='$activityId' for respondentId='$respondentId'")
        return mainActivityGateway.updateMainActivityById(respondentId, activityId, payload, request.getSessionToken()).asResponseEntity()
    }
}