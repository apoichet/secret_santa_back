package com.apoichet.santa.api.rest

import com.apoichet.santa.Santa
import com.apoichet.santa.SecretSantaBuilder
import com.apoichet.santa.validator.SantaArgumentException
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.post
import org.slf4j.Logger
import java.lang.Exception


fun Route.buildResult(logger: Logger, builder: SecretSantaBuilder?) {

    post("build") {
        val santaList = call.receive<Array<Santa>>().toList()
        try {
            builder!!.buildFrom(santaList)?.let {
                call.respond(HttpStatusCode.OK, it)
            } ?: call.respond(HttpStatusCode.NoContent)
        }
        catch (e: Exception) {
            when(e) {
                is SantaArgumentException -> {
                    logger.error("Client Error Code: " + e.codeError + " Message: " + e.msgError)
                    call.respond(HttpStatusCode.BadRequest,
                        SecretSantaArgumentError(e.codeError, e.msgError))
                }
                else -> {
                    logger.error("Server Error: " + e.message)
                    call.respond(HttpStatusCode.InternalServerError)
                }
            }
        }
    }
}

class SecretSantaArgumentError(val codeError: Int, val msgError: String)

