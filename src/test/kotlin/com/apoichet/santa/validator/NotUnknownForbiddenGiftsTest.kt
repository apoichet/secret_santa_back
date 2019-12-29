package com.apoichet.santa.validator

import com.apoichet.santa.Santa
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

internal class NotUnknownForbiddenGiftsTest {

    private val sut = NotUnknownForbiddenGifts()

    @Test
    fun `should validate when all santa in forbidden gifts are known` (){
        //Given
        val santaList  = listOf(
            givenFakeSanta("Toto", listOf("Tom Tom")),
            givenFakeSanta("Tim", listOf("Tom Tom")),
            givenFakeSanta("Tom", listOf("Alan Alan")),
            givenFakeSanta("Alan"))
        //When
        sut.validate(santaList)
    }

    @Test
    fun `should reject when unknown santa in forbidden gifts` (){
        //Given
        val santaList  = listOf(
            givenFakeSanta("Toto", listOf("Titi Titi")),
            givenFakeSanta("Tim"),
            givenFakeSanta("Tom"),
            givenFakeSanta("Alan"))
        //When
        assertThrows<SantaArgumentException> {
            sut.validate(santaList)
        }
    }

    private fun givenFakeSanta(name: String): Santa {
        return Santa(name, name, name)
    }

    private fun givenFakeSanta(name: String, forbidenGifts: List<String>): Santa {
        return Santa(name, name, name, forbidenGifts)
    }

    private fun givenFakeSanta(name: String, receiver: String): Santa {
        return Santa(name, name, name, receiver = receiver)
    }


}