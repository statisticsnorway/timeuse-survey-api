package no.ssb.timeusesurveyapi.mainactivity

import no.ssb.timeusesurveyapi.ResponseWrapper
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono
import java.util.*

@Service
class MainActivityGateway(
    @Value("\${timeuse-survey-service-ingress}")
    private val baseUrl: String
) {
    private val client: WebClient = WebClient.create()
    private val logger = LoggerFactory.getLogger(this::class.java)

    internal fun getMainActivities(respondentId: UUID): ResponseWrapper {
        return makeGetRequest(getMainActivitiesPath(respondentId))
    }

    internal fun getMainActivityById(respondentId: UUID, activityId: String): ResponseWrapper {
        return makeGetRequest(getMainActivityByIdPath(respondentId, activityId))
    }

    internal fun getMainActivitiesGroupByDay(respondentId: UUID): ResponseWrapper {
        return makeGetRequest(getMainActivitiesGroupByDayPath(respondentId))
    }

    internal fun postMainActivity(payload: String): ResponseWrapper {
        return makePostRequest(postMainActivityPath(), payload)
    }

    internal fun postMainActivities(respondentId: UUID, payload: String): ResponseWrapper {
        return makePostRequest(postMainActivitiesPath(respondentId), payload)
    }

    internal fun deleteMainActivity(respondentId: UUID): ResponseWrapper {
        return makeDeleteRequest(deleteMainActivityPath(respondentId))
    }

    internal fun deleteMainActivityById(respondentId: UUID, activityId: String): ResponseWrapper {
        return makeDeleteRequest(deleteMainActivityByIdPath(respondentId, activityId))
    }

    internal fun deleteMainActivityByStartTime(respondentId: UUID, startTime: String): ResponseWrapper {
        return makeDeleteRequest(deleteMainActivityByStartTimePath(respondentId, startTime))
    }

    private fun makePostRequest(path: String, payload: String): ResponseWrapper {
        try {
            val respons = client.post()
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
            logger.error("Exception for post request to external service. Exception message = '${e.message}'")
            throw e
        }
    }

    private fun makeGetRequest(path: String): ResponseWrapper {
        try {
            val respons = client.get()
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
            logger.error("Exception for get request to external service. Exception message = '${e.message}'")
            throw e
        }
    }

    private fun makeDeleteRequest(path: String): ResponseWrapper {
        try {
            val respons = client.delete()
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
            logger.error("Exception for get request to external service. Exception message = '${e.message}'")
            throw e
        }
    }

    internal fun patchMainActivityById(respondentId: UUID, activityId: String, payload: String): ResponseWrapper {
        try {
            val respons = client.patch()
                .uri(baseUrl + patchMainActivityByIdPath(respondentId, activityId))
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
            logger.error("Exception for get request to external service. Exception message = '${e.message}'")
            throw e
        }
    }
}