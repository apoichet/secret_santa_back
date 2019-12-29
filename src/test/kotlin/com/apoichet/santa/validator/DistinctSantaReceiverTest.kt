package com.apoichet.santa.validator

import com.apoichet.santa.Santa
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

internal class DistinctSantaReceiverTest {

    private val sut = DistinctSantaReceiver()

    @Test
    fun `should validate when santa receiver is distinct`() {
        //Given
        val santaList = listOf(
            givenFakeSanta("Toto","Tim"),
            givenFakeSanta("Tim"),
            givenFakeSanta("Tom","Toto"),
            givenFakeSanta("Toto"))
        //When
        sut.validate(santaList)
    }

    @Test
    fun `should validate when santa receiver is empty`() {
        //Given
        val santaList = listOf(
            givenFakeSanta("Toto",""),
            givenFakeSanta("Tim"),
            givenFakeSanta("Tom",""),
            givenFakeSanta("Toto"))
        //When
        sut.validate(santaList)
    }

    @Test
    fun `should reject when santa have same receiver`() {
        //Given
        val santaList = listOf(
            givenFakeSanta("Toto","Tim"),
            givenFakeSanta("Tim"),
            givenFakeSanta("Tom","Tim"),
            givenFakeSanta("Toto"))
        //When
        assertThrows<SantaArgumentException> {
            sut.validate(santaList)
        }

    }

    private fun givenFakeSanta(name: String): Santa {
        return Santa(name, name, name)
    }

    private fun givenFakeSanta(name: String, receiver: String): Santa {
        return Santa(name, name, name, receiver = receiver)
    }


}