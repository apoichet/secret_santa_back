package com.apoichet.santa.validator

import com.apoichet.santa.Santa

class DistinctSanta(codeError: Int = 0, msgError: String = "") : SantaValidator(codeError, msgError) {
    override fun test(t: List<Santa>): Boolean {
        return singleSantaMail(t) && singleSantaName(t)
    }

    private fun singleSantaName(santaList: List<Santa>): Boolean {
        return santaList.groupBy{it.getName()}
            .none{ it.value.size > 1}
    }

    private fun singleSantaMail(santaList: List<Santa>): Boolean {
        return santaList.groupBy{it.email}
            .none{ it.value.size > 1}
    }
}