package no.ssb.timeusesurveyapi.householdmember

import no.ssb.timeusesurveyapi.ResponseWrapper
import no.ssb.timeusesurveyapi.householdmember.HouseholdMembersGateway.RequestType.*
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono
import java.util.*

@Service
class HouseholdMembersGateway(
    @Value("\${timeuse-survey-service-ingress}")
    private val baseUrl: String
) {
    private val client: WebClient = WebClient.create()
    private val logger = LoggerFactory.getLogger(this::class.java)

    internal fun getHouseholdMembers(respondentId: UUID): ResponseWrapper {
        return makeRequestWithoutPayload(GET, getHouseholdMembersPath(respondentId))
    }

    internal fun postHouseholdMembers(respondentId: UUID, payload: String): ResponseWrapper {
        return makeRequestWithPayload(POST, postHouseholdMembersPath(respondentId), payload = payload)
    }

    internal fun putHouseholdMembersById(respondentId: UUID, householdMemberId: String, payload: String): ResponseWrapper {
        return makeRequestWithPayload(PUT, putHouseholdMembersByIdPath(respondentId, householdMemberId), payload = payload)
    }

    internal fun deleteHouseholdMembersById(respondentId: UUID, householdMemberId: String): ResponseWrapper {
        return makeRequestWithoutPayload(DELETE, deleteHouseholdMembersByIdPath(respondentId, householdMemberId))
    }

    private enum class RequestType {GET, POST, DELETE, PUT }

    private fun makeRequestWithPayload(requestType: RequestType, path: String, payload: String): ResponseWrapper {
        try {
            val requestStart = when(requestType){
                POST -> client.post()
                PUT -> client.put()
                DELETE, GET -> throw Exception("Request with payload must be PUT or POST")
            }

            val respons = requestStart
                .uri(baseUrl + path)
                .bodyValue(payload)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatus::isError) {
                    Mono.empty()
                }
                .toEntity(String::class.java)
                .block()

            return ResponseWrapper(status = respons!!.statusCode, payload = respons.body)
        } catch (e: Exception) {
            logger.error("Exception for $requestType request to external service. Exception message = '${e.message}'")
            throw e
        }
    }

    private fun makeRequestWithoutPayload(requestType: RequestType, path: String): ResponseWrapper {
        try {
            val requestStart = when(requestType){
                POST -> client.post()
                PUT -> client.put()
                DELETE -> client.delete()
                GET -> client.get()
            }

            val respons = requestStart
                .uri(baseUrl + path)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatus::isError) {
                    Mono.empty()
                }
                .toEntity(String::class.java)
                .block()

            return ResponseWrapper(status = respons!!.statusCode, payload = respons.body)
        } catch (e: Exception) {
            logger.error("Exception for $requestType request to external service. Exception message = '${e.message}'")
            throw e
        }
    }
}