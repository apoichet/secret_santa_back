package com.apoichet.santa.validator

import com.apoichet.santa.Santa
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

internal class NotEqualSantaAndReceiverTest {
    private val sut = NotEqualSantaAndReceiver()

    @Test
    fun `should reject when equal santa and receiver` (){
        //Given
        val santaList  = listOf(
            givenFakeSanta("Toto"),
            givenFakeSanta("Tim", "Tim"),
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

    private fun givenFakeSanta(name: String, receiver: String): Santa {
        return Santa(name, name, name, receiver = "$receiver $receiver")
    }

}