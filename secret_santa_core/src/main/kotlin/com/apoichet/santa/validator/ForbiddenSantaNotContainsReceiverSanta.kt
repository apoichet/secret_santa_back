package com.apoichet.santa.validator

import com.apoichet.santa.Santa

class ForbiddenSantaNotContainsReceiverSanta(codeError: Int = 0, msgError: String = "") : SantaValidator(codeError, msgError) {
    override fun test(t: List<Santa>): Boolean {
        return t.none{ santa -> santa.forbiddenGifts.filter { it.isNotBlank() }.contains(santa.receiver) }
    }
}