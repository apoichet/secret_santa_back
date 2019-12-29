package com.apoichet.santa.validator

import com.apoichet.santa.Santa

class DistinctForbiddenSanta(codeError: Int = 0, msgError: String = "") : SantaValidator(codeError, msgError) {
    override fun test(t: List<Santa>): Boolean {
        return t.none {
            santa -> santa.forbiddenGifts.groupBy { it }.any { it.value.size > 1 }
        }
    }
}