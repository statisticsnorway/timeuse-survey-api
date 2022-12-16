package no.ssb.timeusesurveyapi.cors

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class CorsConfig(
    @Value("\${cors.allowedOriginsPatterns}")
    val allowedOriginsPatterns: List<String>,
    @Value("\${cors.allowedOrigins}")
    val allowedOrigins: String,
) : WebMvcConfigurer {

    override fun addCorsMappings(registry: CorsRegistry) {
        allowedOriginsPatterns.forEach {pattern ->
            registry
                .addMapping(pattern)
                .allowedOrigins(allowedOrigins)
                .allowCredentials(true)
                .allowedMethods("GET", "PUT", "POST", "DELETE", "OPTIONS", "PATCH")
                .exposedHeaders("Access-Control-Allow-Origin")
                .allowedHeaders("sessionToken", "Content-Type")
        }
    }
}
