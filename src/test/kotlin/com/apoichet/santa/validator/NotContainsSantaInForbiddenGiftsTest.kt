package com.apoichet.santa.validator

import com.apoichet.santa.Santa
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

internal class NotContainsSantaInForbiddenGiftsTest {
    private val sut = NotContainsSantaInForbiddenGifts()

    @Test
    fun `should reject when santa forbidden gifts contains same santa` (){
        //Given
        val santaList  = listOf(
            givenFakeSanta("Toto", listOf("Toto Toto")),
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