package com.apoichet.santa.validator

import com.apoichet.santa.Santa
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

internal class DistinctSantaTest {

    private val sut = DistinctSanta()

    @Test
    fun `should reject when we have santa with same name` (){
        //Given
        val santaList = listOf(
            givenFakeSanta("Toto","toto@mail.com"),
            givenFakeSanta("Tim", "tim@mail.com"),
            givenFakeSanta("Tom","tom@mail.com"),
            givenFakeSanta("Toto", "toto2@mail.com"))
        //When
        assertThrows<SantaArgumentException> {
            sut.validate(santaList)
        }
    }

    @Test
    fun `should reject when we have santa with same mail` (){
        //Given
        val santaList  = listOf(
            givenFakeSanta("Toto","toto@mail.com"),
            givenFakeSanta("Tim", "tim@mail.com"),
            givenFakeSanta("Tom","tom@mail.com"),
            givenFakeSanta("Alan", "toto@mail.com"))
        //When
        assertThrows<SantaArgumentException> {
            sut.validate(santaList)
        }
    }

    private fun givenFakeSanta(name: String, mail: String): Santa {
        return Santa(name, name, mail)
    }



}