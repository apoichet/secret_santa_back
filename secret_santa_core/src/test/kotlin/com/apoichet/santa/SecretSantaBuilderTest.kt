package com.apoichet.santa

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class SecretSantaBuilderTest {

    @Test
    fun `should have just as many pairs in result? as santa input` (){
        //Given
        val santaList  = listOf(
            givenFakeSanta("Toto"),
            givenFakeSanta("Tim"),
            givenFakeSanta("Tom"),
            givenFakeSanta("Tam"))
        //When
        val result = SecretSantaBuilder().buildFrom(santaList)
        //Then
        assertThat(result?.size).isEqualTo(santaList.size)
    }

    @Test
    fun `should all santa receive gift` (){
        //Given
        val santaList  = listOf(
            givenFakeSanta("Toto"),
            givenFakeSanta("Tim"),
            givenFakeSanta("Tom"),
            givenFakeSanta("Tam"))
        //When
        val result = SecretSantaBuilder().buildFrom(santaList)
        //Then
        assertThat(result?.groupBy { it.receiver }?.keys).containsAll(santaList.map { it.getName() })
    }

    @Test
    fun `should all santa give gift` (){
        //Given
        val santaList  = listOf(
            givenFakeSanta("Toto"),
            givenFakeSanta("Tim"),
            givenFakeSanta("Tom"),
            givenFakeSanta("Tam"))
        //When
        val result = SecretSantaBuilder().buildFrom(santaList)
        //Then
        assertThat(result?.groupBy { it.giver }?.keys).containsAll(santaList.map { it.getName() })
    }

    @Test
    fun `should respect forbidden gifts` (){
        //Given
        val santaList  = listOf(
            givenFakeSanta("Toto", listOf("Tim Tim", "Tom Tom", "Tata Tata", "Titi Titi", "Tutu Tutu")),
            givenFakeSanta("Tim", listOf("Tam Tam", "Toto Toto")),
            givenFakeSanta("Titi", listOf("Tam Tam")),
            givenFakeSanta("Tutu", listOf("Tom Tom", "Titi Titi")),
            givenFakeSanta("Tata"),
            givenFakeSanta("Tom", listOf("Tam Tam", "Tim Tim")),
            givenFakeSanta("Tam", listOf("Tom Tom", "Toto Toto")))
        //When
        val result = SecretSantaBuilder().buildFrom(santaList)
        //Then
        assertThat(result?.map { Pair(it.giver, it.receiver) }).contains(Pair("Toto Toto", "Tam Tam"))
    }

    @Test
    fun `should respect all forbidden gifts` (){
        //Given
        val santaList  = listOf(
            givenFakeSanta("Toto", listOf("Tim Tim", "Tom Tom")),
            givenFakeSanta("Tim", listOf("Tam Tam", "Toto Toto")),
            givenFakeSanta("Tom", listOf("Tam Tam", "Tim Tim")),
            givenFakeSanta("Tam", listOf("Tom Tom", "Toto Toto")))
        //When
        val result = SecretSantaBuilder().buildFrom(santaList)
        //Then
        assertThat(result?.map { Pair(it.giver, it.receiver) }).contains(Pair("Toto Toto", "Tam Tam"))
        assertThat(result?.map { Pair(it.giver, it.receiver) }).contains(Pair("Tim Tim", "Tom Tom"))
        assertThat(result?.map { Pair(it.giver, it.receiver) }).contains(Pair("Tom Tom", "Toto Toto"))
        assertThat(result?.map { Pair(it.giver, it.receiver) }).contains(Pair("Tam Tam", "Tim Tim"))
    }

    @Test
    fun `should respect receiver gift` (){
        //Given
        val santaList  = listOf(
            givenFakeSanta("Toto", "Tim"),
            givenFakeSanta("Tim", "Tom"),
            givenFakeSanta("Tom", "Tam"),
            givenFakeSanta("Tam", "Toto"))
        //When
        val result = SecretSantaBuilder().buildFrom(santaList)
        //Then
        assertThat(result?.map { Pair(it.giver, it.receiver) }).contains(Pair("Toto Toto", "Tim Tim"))
        assertThat(result?.map { Pair(it.giver, it.receiver) }).contains(Pair("Tim Tim", "Tom Tom"))
        assertThat(result?.map { Pair(it.giver, it.receiver) }).contains(Pair("Tom Tom", "Tam Tam"))
        assertThat(result?.map { Pair(it.giver, it.receiver) }).contains(Pair("Tam Tam", "Toto Toto"))
    }

    @Test
    fun `should respect receiver gift and forbidden gifts` (){
        //Given
        val santaList  = listOf(
            givenFakeSanta("Toto", "Tim"),
            givenFakeSanta("Tim", listOf("Tam Tam", "Toto Toto")),
            givenFakeSanta("Tom", "Tam"),
            givenFakeSanta("Tam", listOf("Tim Tim", "Tom Tom")))
        //When
        val result = SecretSantaBuilder().buildFrom(santaList)
        //Then
        assertThat(result?.map { Pair(it.giver, it.receiver) }).contains(Pair("Toto Toto", "Tim Tim"))
        assertThat(result?.map { Pair(it.giver, it.receiver) }).contains(Pair("Tim Tim", "Tom Tom"))
        assertThat(result?.map { Pair(it.giver, it.receiver) }).contains(Pair("Tom Tom", "Tam Tam"))
        assertThat(result?.map { Pair(it.giver, it.receiver) }).contains(Pair("Tam Tam", "Toto Toto"))
    }

    @Test
    fun `should return null when no solution found` (){
        //Given
        val santaList  = listOf(
            givenFakeSanta("Toto", "Tim"),
            givenFakeSanta("Tim", listOf("Tam Tam", "Toto Toto")),
            givenFakeSanta("Tom", "Tam"),
            givenFakeSanta("Tam", listOf("Tim Tim", "Toto Toto")))
        //When
        val result = SecretSantaBuilder().buildFrom(santaList)
        //Then
        assertThat(result).isNull()
    }

    private fun givenFakeSanta(name: String): Santa {
        return Santa(name, name, name)
    }


    private fun givenFakeSanta(name: String, forbidenGifts: List<String>): Santa {
        return Santa(name, name, name, forbidenGifts)
    }

    private fun givenFakeSanta(name: String, receiver: String): Santa {
        return Santa(name, name, name, receiver = "$receiver $receiver")
    }

}