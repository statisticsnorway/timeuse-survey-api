package no.ssb.timeusesurveyapi.corse

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class CorsConfig : WebMvcConfigurer {
    override fun addCorsMappings(registry: CorsRegistry) {
        registry
            .addMapping("/**")
            .allowedOrigins("https://tid.staging.ssb.no")
            .allowCredentials(true)
            .allowedMethods("GET", "PUT", "POST", "DELETE", "OPTIONS", "PATCH")
            .exposedHeaders("Access-Control-Allow-Origin")
            .allowedHeaders("sessionToken", "Content-Type")
    }
}
