package com.apoichet.santa.validator

import com.apoichet.santa.Santa

class DistinctSantaReceiver(codeError: Int = 0, msgError: String = "") : SantaValidator(codeError, msgError) {

    override fun test(t: List<Santa>): Boolean {
        return t.filter { it.receiver.isNotBlank() }
            .map { it.receiver }
            .groupBy { it }
            .none{ it.value.size > 1}
    }

}