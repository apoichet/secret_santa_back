package com.apoichet.santa

import com.apoichet.santa.validator.*

class SecretSantaBuilder(private val validators: List<SantaValidator> = listOf(
    EnoughSanta(1, "Not enough Santa"),
    DistinctSanta(2, "Not single Santa"),
    NotContainsSantaInForbiddenGifts(3, "Same santa in forbidden gifts"),
    NotTooManyForbiddenGifts(4, "Too many forbidden gifts"),
    NotUnknownForbiddenGifts(5, "Unknown santa in forbidden gifts"),
    DistinctForbiddenSanta(6, "Duplicate santa in same forbidden gifts"),
    DistinctSantaReceiver(7, "Duplicate santa in receivers"),
    NotEqualSantaAndReceiver(8, "Santa and receiver have same name"),
    NotUnknownSantaReceiver(9, "Unknown santa receiver")
)) {

    fun buildFrom(santaList: List<Santa>): List<SecretSantaResult>? {
        validators.forEach { it.validate(santaList) }
        val notYetReceiveGift: List<String> = santaList.map { it.getName() } - santaList.map { it.receiver }
        val santaWithoutReceivers: List<Santa> = santaList.filter { it.receiver.isBlank() }
        buildResult(santaWithoutReceivers, notYetReceiveGift)?.let {
            return buildSantaWithReceiverList(santaList) + it
        }
        return null
    }

    private fun buildSantaWithReceiverList(santaList: List<Santa>): List<SecretSantaResult> {
        return santaList
            .filter { it.receiver.isNotBlank() }
            .map { SecretSantaResult(
                giver = it.getName(),
                emailGiver = it.email,
                receiver = it.receiver) }
    }

    private fun buildResult(santaList: List<Santa>, notYetReceiveGift: List<String>): List<SecretSantaResult>? {

        if (santaList.isEmpty()) return listOf()
        val source = santaList.first()

        val filtered = notYetReceiveGift
            .filter { it != source.getName() }
            .filter { it !in source.forbiddenGifts }
            .shuffled()

        if (filtered.isEmpty()) return null

        filtered.forEach {
            val result = buildResult(santaList - source, notYetReceiveGift - it)
            if(result != null) {
                return result + SecretSantaResult(
                    giver = source.getName(),
                    emailGiver = source.email,
                    receiver = it)
            }
        }
        return null
    }

}