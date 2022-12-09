package no.ssb.timeusesurveyapi.householdmember

import jakarta.servlet.http.HttpServletRequest
import no.ssb.timeusesurveyapi.utils.getSessionToken
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/v1/respondent/{respondent-id}/household-member")
class HouseholdMemberController(
    private val gateway: HouseholdMembersGateway
) {
    private val logger = LoggerFactory.getLogger(this::class.java)

    @GetMapping
    internal fun getHouseholdMembers(
        @PathVariable(value = "respondent-id") respondentId: UUID,
        request: HttpServletRequest
    ): ResponseEntity<String> {
        logger.info("Getting household members for respondentId='$respondentId'")
        return gateway.getHouseholdMembers(respondentId, request.getSessionToken()).asResponseEntity()
    }

    @PostMapping
    internal fun postHouseholdMembers(
        @PathVariable(value = "respondent-id") respondentId: UUID,
        @RequestBody payload: String,
        request: HttpServletRequest
    ): ResponseEntity<String> {
        logger.info("Posting household members for respondentId='$respondentId'")
        return gateway.postHouseholdMembers(respondentId, payload, request.getSessionToken()).asResponseEntity()
    }

    @PutMapping("/{household-member-id}")
    internal fun updateHouseholdMembersById(
        @PathVariable(value = "respondent-id") respondentId: UUID,
        @PathVariable(value = "household-member-id") householdMemberId: String,
        @RequestBody payload: String,
        request: HttpServletRequest
    ): ResponseEntity<String> {
        logger.info("Updating household members id='$householdMemberId' for respondentId='$respondentId'")
        return gateway.updateHouseholdMembersById(respondentId, householdMemberId, payload, request.getSessionToken()).asResponseEntity()
    }

    @DeleteMapping("/{household-member-id}")
    internal fun deleteHouseholdMembersById(
        @PathVariable(value = "respondent-id") respondentId: UUID,
        @PathVariable(value = "household-member-id") householdMemberId: String,
        request: HttpServletRequest
    ): ResponseEntity<String> {
        logger.info("Delete household members id='$householdMemberId' for respondentId='$respondentId'")
        return gateway.deleteHouseholdMembersById(respondentId, householdMemberId, request.getSessionToken()).asResponseEntity()
    }
}