package com.apoichet.santa.validator

import com.apoichet.santa.Santa

class NotUnknownForbiddenGifts(codeError: Int = 0, msgError: String = "") : SantaValidator(codeError, msgError) {
    override fun test(t: List<Santa>): Boolean {
        val forbiddenGifts = t.flatMap { it.forbiddenGifts }
            .distinct()
        if (forbiddenGifts.isEmpty()) return true
        return t.map { it.getName() }.containsAll(forbiddenGifts)
    }
}