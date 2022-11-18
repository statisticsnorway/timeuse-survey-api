package no.ssb.timeusesurveyapi.householdmember

import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/v1/respondent/{respondent-id}/household-members")
class HouseholdMemberController(
    private val gateway: HouseholdMembersGateway
) {
    private val logger = LoggerFactory.getLogger(this::class.java)

    @GetMapping
    internal fun getHouseholdMembers(
        @PathVariable(value = "respondent-id") respondentId: UUID
    ): ResponseEntity<String> {
        logger.info("Getting household members for respondentId='$respondentId'")
        return gateway.getHouseholdMembers(respondentId).asResponseEntity()
    }

    @PostMapping
    internal fun postHouseholdMembers(
        @PathVariable(value = "respondent-id") respondentId: UUID,
        @RequestBody payload: String
    ): ResponseEntity<String> {
        logger.info("Posting household members for respondentId='$respondentId'")
        return gateway.postHouseholdMembers(respondentId, payload).asResponseEntity()
    }

    @PutMapping("/{household-member-id}")
    internal fun putHouseholdMembersById(
        @PathVariable(value = "respondent-id") respondentId: UUID,
        @PathVariable(value = "household-member-id") householdMemberId: String,
        @RequestBody payload: String
    ): ResponseEntity<String> {
        logger.info("Put household members id='$householdMemberId' for respondentId='$respondentId'")
        return gateway.putHouseholdMembersById(respondentId, householdMemberId, payload).asResponseEntity()
    }

    @DeleteMapping("/{household-member-id}")
    internal fun deleteHouseholdMembersById(
        @PathVariable(value = "respondent-id") respondentId: UUID,
        @PathVariable(value = "household-member-id") householdMemberId: String,
    ): ResponseEntity<String> {
        logger.info("Delete household members id='$householdMemberId' for respondentId='$respondentId'")
        return gateway.deleteHouseholdMembersById(respondentId, householdMemberId).asResponseEntity()
    }
}