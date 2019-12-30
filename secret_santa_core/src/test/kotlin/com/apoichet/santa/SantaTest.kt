package com.apoichet.santa

import org.junit.jupiter.api.Test
import org.assertj.core.api.Assertions.assertThat

internal class SantaTest {

    @Test
    fun `should build santa id with last name and first name`() {
        //Given
        val santa = Santa("Poichet", "Alexandre", "a.poichet@gmail.com")
        //When
        val id = santa.getName()
        //Then
        assertThat(id).isEqualTo("Poichet Alexandre")
    }

}