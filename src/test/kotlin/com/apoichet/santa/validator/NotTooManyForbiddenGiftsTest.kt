package com.apoichet.santa.validator

import com.apoichet.santa.Santa
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

internal class NotTooManyForbiddenGiftsTest {

    private val sut = NotTooManyForbiddenGifts()

    @Test
    fun `should reject with too many forbidden gifts` (){
        //Given
        val santaList  = listOf(
            givenFakeSanta("Toto", listOf("Tom Tom", "Alan Alan", "Tim Tim")),
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



}