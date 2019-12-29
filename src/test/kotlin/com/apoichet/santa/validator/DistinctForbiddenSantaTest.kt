package com.apoichet.santa.validator

import com.apoichet.santa.Santa
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

internal class DistinctForbiddenSantaTest {

    private val sut = DistinctForbiddenSanta()

    @Test
    fun `should validate when duplicate santa in different forbidden gifts`() {
        //Given
        val santaList  = listOf(
            givenFakeSanta("Toto", listOf("Tom Tom", "Alan Alan")),
            givenFakeSanta("Tim", listOf("Tom Tom")),
            givenFakeSanta("Tom"),
            givenFakeSanta("Alan"))
        //When
        sut.validate(santaList)
    }

    @Test
    fun `should reject when duplicate santa in same forbidden gifts`() {
        //Given
        val santaList  = listOf(
            givenFakeSanta("Toto", listOf("Tom Tom", "Alan Alan", "Tom Tom")),
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

    private fun givenFakeSanta(name: String, forbiddenGifts: List<String>): Santa {
        return Santa(name, name, name, forbiddenGifts)
    }

}