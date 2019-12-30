package com.apoichet.santa.validator

import com.apoichet.santa.Santa
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

internal class NotUnknownSantaReceiverTest {
    private val sut = NotUnknownSantaReceiver()

    @Test
    fun `should reject when santa receiver is unknown` (){
        //Given
        val santaList  = listOf(
            givenFakeSanta("Toto"),
            givenFakeSanta("Tim", "Tam"),
            givenFakeSanta("Tom"),
            givenFakeSanta("Alan"))
        //When
        assertThrows<SantaArgumentException> {
            sut.validate(santaList)
        }
    }

    @Test
    fun `should validate when santa receiver is empty` (){
        //Given
        val santaList  = listOf(
            givenFakeSanta("Toto"),
            givenFakeSanta("Tim", ""),
            givenFakeSanta("Tom"),
            givenFakeSanta("Alan"))
        //When
        sut.validate(santaList)

    }


    private fun givenFakeSanta(name: String): Santa {
        return Santa(name, name, name)
    }

    private fun givenFakeSanta(name: String, receiver: String): Santa {
        return Santa(name, name, name, receiver = "$receiver $receiver")
    }

}