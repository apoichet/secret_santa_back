package com.apoichet.santa.api.rest

import com.apoichet.santa.JsonMapper
import com.apoichet.santa.SecretSantaBuilder
import com.apoichet.santa.SecretSantaResult
import com.apoichet.santa.mainWithDependencies
import com.apoichet.santa.validator.SantaArgumentException
import com.fasterxml.jackson.core.type.TypeReference
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.server.testing.TestApplicationEngine
import io.ktor.server.testing.handleRequest
import io.ktor.server.testing.setBody
import io.ktor.server.testing.withTestApplication
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
internal class SecretSantaApiKtTest {

    @RelaxedMockK
    private lateinit var mockSecretSantaBuilder: SecretSantaBuilder
    private val mapper = JsonMapper.defaultMapper

    @Test
    fun `should respond 400 when illegal argument exception`() = testApp {
        val santaList = this.javaClass.getResource("/santa_list.json").readText(Charsets.UTF_8)
        every { mockSecretSantaBuilder.buildFrom(any()) } throws SantaArgumentException(codeError = 1, msgError = "argument error")
        handleRequest ( HttpMethod.Post, "/secretsanta/build" ) {
            addHeader("Accept", "application/json")
            addHeader("Content-Type", "application/json")
            setBody(santaList)
        }.apply {
            assertEquals(HttpStatusCode.BadRequest, this.response.status())
            val response = mapper.readValue(this.response.content, SecretSantaArgumentError::class.java)
            assertEquals(response.codeError, 1)
            assertEquals(response.msgError, "argument error")
        }
    }

    @Test
    fun `should respond 204 when no solution found`() = testApp {
        val santaList = this.javaClass.getResource("/santa_list.json").readText(Charsets.UTF_8)
        every { mockSecretSantaBuilder.buildFrom(any()) } returns null
        handleRequest ( HttpMethod.Post, "/secretsanta/build" ) {
            addHeader("Accept", "application/json")
            addHeader("Content-Type", "application/json")
            setBody(santaList)
        }.apply {
            assertEquals(HttpStatusCode.NoContent, this.response.status())
        }
    }

    @Test
    fun `should respond 200 when solution found`() = testApp {
        val santaList = this.javaClass.getResource("/santa_list.json").readText(Charsets.UTF_8)
        val json = this.javaClass.getResource("/secret_santa_result.json").readText(Charsets.UTF_8)
        val result: List<SecretSantaResult> = mapper.readValue(json, object : TypeReference<List<SecretSantaResult>>() {})
        every { mockSecretSantaBuilder.buildFrom(any()) } returns result
        handleRequest ( HttpMethod.Post, "/secretsanta/build" ) {
            addHeader("Accept", "application/json")
            addHeader("Content-Type", "application/json")
            setBody(santaList)
        }.apply {
            assertEquals(HttpStatusCode.OK, this.response.status())
        }
    }

    private fun testApp(callback: TestApplicationEngine.() -> Unit) {
        withTestApplication({ mainWithDependencies(builder = mockSecretSantaBuilder) }) { callback() }
    }


}