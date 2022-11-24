package no.ssb.timeusesurveyapi

import no.ssb.timeusesurveyapi.RequestType.*
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

@Service
class WebClientService(
    @Value("\${timeuse-survey-service-ingress}")
    private val timeuseSurveyServiceBaseUrl: String,
    @Value("\${session-token-cookie-name}")
    private val sessionTokenCookieName: String
) {
    private val client: WebClient = WebClient.create()
    private val logger = LoggerFactory.getLogger(this::class.java)

    internal fun makeRequestWithPayload(
        requestType: RequestType,
        path: String,
        payload: String,
        sessionTokenValue: String
    ): ResponseWrapper {
        logger.info("Making request '$requestType' to path '$path' with payload")

        try {
            val requestWithMethod = when(requestType){
                POST -> client.post()
                PUT -> client.put()
                PATCH -> client.patch()
                DELETE, GET -> throw Exception("Request with payload must be PUT, PATCH or POST")
            }

            val respons = requestWithMethod
                .uri(timeuseSurveyServiceBaseUrl + path)
                .cookie(sessionTokenCookieName, sessionTokenValue)
                .contentType(MediaType.APPLICATION_JSON)
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

    internal fun makeRequest(
        requestType: RequestType,
        path: String,
        sessionTokenValue: String
    ): ResponseWrapper {
        logger.info("Making request '$requestType' to path '$path'")

        try {
            val requestWithMethod = when(requestType){
                POST -> client.post()
                PUT -> client.put()
                DELETE -> client.delete()
                GET -> client.get()
                PATCH -> client.patch()
            }

            val respons = requestWithMethod
                .uri(timeuseSurveyServiceBaseUrl + path)
                .accept(MediaType.APPLICATION_JSON)
                .cookie(sessionTokenCookieName, sessionTokenValue)
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

internal enum class RequestType { GET, POST, DELETE, PUT, PATCH }