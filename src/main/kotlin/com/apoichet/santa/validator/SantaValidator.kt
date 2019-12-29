package com.apoichet.santa.validator

import com.apoichet.santa.Santa
import java.util.function.Predicate

abstract class SantaValidator(val codeError: Int, val msgError: String) : Predicate<List<Santa>> {

    fun validate(santaList: List<Santa>) {
        if (!test(santaList)) throw SantaArgumentException(codeError, msgError)
    }

}

class SantaArgumentException(val codeError: Int, val msgError: String) : IllegalArgumentException(msgError)