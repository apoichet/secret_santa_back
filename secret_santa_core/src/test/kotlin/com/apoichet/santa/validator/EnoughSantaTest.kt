package com.apoichet.santa.validator

import com.apoichet.santa.Santa
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

internal class EnoughSantaTest {

    private val sut = EnoughSanta()

    @Test
    fun `should reject when santa list empty` (){
        //When
        assertThrows<SantaArgumentException> {
            sut.validate(emptyList())
        }
    }

    @Test
    fun `should reject with only two santaList` (){
        val santaList = listOf(
            givenFakeSanta("Tom"),
            givenFakeSanta("Toto"))
        //When
        assertThrows<SantaArgumentException> {
            sut.validate(santaList)
        }
    }

    @Test
    fun `should reject with only three santaList` (){
        val santaList = listOf(
            givenFakeSanta("Toto"),
            givenFakeSanta("Tom"),
            givenFakeSanta("Titi"))
        //When
        assertThrows<SantaArgumentException> {
            sut.validate(santaList)
        }
    }

    private fun givenFakeSanta(name: String): Santa {
        return Santa(name, name, name)
    }

}