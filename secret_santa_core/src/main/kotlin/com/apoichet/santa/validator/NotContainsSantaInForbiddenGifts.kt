package com.apoichet.santa.validator

import com.apoichet.santa.Santa

class NotContainsSantaInForbiddenGifts(codeError: Int = 0, msgError: String = "") : SantaValidator(codeError, msgError) {
    override fun test(t: List<Santa>): Boolean {
        return t.none { it.forbiddenGifts.contains(it.getName()) }
    }
}