package com.apoichet.santa.validator

import com.apoichet.santa.Santa
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

internal class ForbiddenSantaNotContainsReceiverSantaTest {

    private val sut = ForbiddenSantaNotContainsReceiverSanta()

    @Test
    fun `should reject when forbidden santa has receiver santa` (){
        val santaList  = listOf(
            givenFakeSanta("Toto", listOf("Tim Tim", "Alan Alan"), "Tim Tim"),
            givenFakeSanta("Tim"),
            givenFakeSanta("Alan"))
        //When
        assertThrows<SantaArgumentException> {
            sut.validate(santaList)
        }
    }

    @Test
    fun `should validate when empty forbidden santa and empty receiver santa` (){
        val santaList  = listOf(
            givenFakeSanta("Toto", listOf("", "Alan Alan"), ""),
            givenFakeSanta("Tim"),
            givenFakeSanta("Alan"))
        //When
        sut.validate(santaList)

    }

    private fun givenFakeSanta(name: String): Santa {
        return Santa(name, name, name)
    }

    private fun givenFakeSanta(name: String, forbidenGifts: List<String>, nameReceiver: String): Santa {
        return Santa(name, name, name, forbidenGifts, nameReceiver)
    }



}