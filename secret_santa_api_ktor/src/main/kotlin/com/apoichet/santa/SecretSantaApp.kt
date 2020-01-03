package com.apoichet.santa

import com.apoichet.santa.api.rest.buildResult
import com.fasterxml.jackson.core.util.DefaultIndenter
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.*
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.jackson.JacksonConverter
import io.ktor.response.respond
import io.ktor.routing.Routing
import io.ktor.routing.route
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.text.SimpleDateFormat

fun Application.mainWithDependencies(
    builder: SecretSantaBuilder? = null
) {
    install(DefaultHeaders)
    install(Compression)
    install(CORS) {
        header(HttpHeaders.AccessControlAllowOrigin)
        anyHost()
    }
    installMapper()

    val logger = LoggerFactory.getLogger("Application")
    manageException(logger)

    install(Routing) {
        route("/secretsanta") {
            buildResult(logger, builder)
        }
    }
}

private fun Application.manageException(logger: Logger) {
    install(StatusPages) {
        exception<Exception> { cause ->
            logger.error(cause.message)
            call.respond(HttpStatusCode.InternalServerError, cause.message ?: "no message exception")
        }
    }
}

private fun Application.installMapper() {
    install(ContentNegotiation) {
        register(ContentType.Application.Json, JacksonConverter(JsonMapper.defaultMapper))
    }
}

fun Application.main() {
    mainWithDependencies(SecretSantaBuilder())
}

fun main() {
    embeddedServer(Netty, 8080, watchPaths = listOf("secret_santa_api_ktor"), module = Application::main).start()
}

object JsonMapper {
    val defaultMapper: ObjectMapper = jacksonObjectMapper()

    init {
        defaultMapper.configure(SerializationFeature.INDENT_OUTPUT, true)
        defaultMapper.setDefaultPrettyPrinter(DefaultPrettyPrinter().apply {
            indentArraysWith(DefaultPrettyPrinter.FixedSpaceIndenter.instance)
            indentObjectsWith(DefaultIndenter("  ", "\n"))
        })

        defaultMapper.dateFormat = SimpleDateFormat("dd/MM/yyyy hh:mm")
        defaultMapper.registerModule(JavaTimeModule())
    }
}

