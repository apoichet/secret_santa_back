package com.apoichet.santa.validator

import com.apoichet.santa.Santa

class NotUnknownSantaReceiver(codeError: Int = 0, msgError: String = "") : SantaValidator(codeError, msgError) {

    override fun test(t: List<Santa>): Boolean {
        return t.map { santa ->  santa.getName() }
            .containsAll(t.filter { it.receiver.isNotBlank() }.map { it.receiver }.distinct())
    }

}