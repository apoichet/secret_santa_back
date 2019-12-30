package com.apoichet.santa.validator

import com.apoichet.santa.Santa

class EnoughSanta(codeError: Int = 0, msgError: String = "") : SantaValidator(codeError, msgError) {

    private val santaEnoughSize = 4
    
    override fun test(t: List<Santa>): Boolean {
        return t.size >= santaEnoughSize
    }
}