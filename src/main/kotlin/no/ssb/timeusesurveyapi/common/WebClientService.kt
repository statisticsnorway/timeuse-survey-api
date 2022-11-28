package no.ssb.timeusesurveyapi.common

import no.ssb.timeusesurveyapi.common.RequestType.*
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
    private val timeuseSurveyServiceBaseUrl: String
) {
    private val client: WebClient = WebClient.create()
    private val logger = LoggerFactory.getLogger(this::class.java)

    internal fun makeRequestWithPayload(request: RequestWrapperWithPayload): ResponseWrapper {
        logger.info("Making request '${request.requestType}' to path '${request.path}' with payload")

        try {
            val requestWithMethod = when(request.requestType){
                POST -> client.post()
                PUT -> client.put()
                PATCH -> client.patch()
                DELETE, GET -> throw Exception("Request with payload must be PUT, PATCH or POST")
            }

            val respons = requestWithMethod
                .uri(timeuseSurveyServiceBaseUrl + request.path)
                .cookie(request.sessionToken.name, request.sessionToken.value)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request.payload)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatus::isError) {
                    Mono.empty()
                }
                .toEntity(String::class.java)
                .block()

            return ResponseWrapper(status = respons!!.statusCode, payload = respons.body)
        } catch (e: Exception) {
            logger.error("Exception for $request.requestType request to external service. Exception message = '${e.message}'")
            throw e
        }
    }

    internal fun makeRequest(requestWrapper: RequestWrapper): ResponseWrapper {
        logger.info("Making request '${requestWrapper.requestType}' to path '${requestWrapper.path}'")

        try {
            val requestWithMethod = when(requestWrapper.requestType){
                POST -> client.post()
                PUT -> client.put()
                DELETE -> client.delete()
                GET -> client.get()
                PATCH -> client.patch()
            }

            val respons = requestWithMethod
                .uri(timeuseSurveyServiceBaseUrl + requestWrapper.path)
                .accept(MediaType.APPLICATION_JSON)
                .cookie(requestWrapper.sessionToken.name, requestWrapper.sessionToken.value)
                .retrieve()
                .onStatus(HttpStatus::isError) {
                    Mono.empty()
                }
                .toEntity(String::class.java)
                .block()

            return ResponseWrapper(status = respons!!.statusCode, payload = respons.body)
        } catch (e: Exception) {
            logger.error("Exception for $requestWrapper.requestType request to external service. Exception message = '${e.message}'")
            throw e
        }
    }

}