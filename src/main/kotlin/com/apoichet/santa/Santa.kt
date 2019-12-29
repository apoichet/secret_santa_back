package com.apoichet.santa

class Santa(
    private val lastName: String,
    private val firstName: String,
    val mail: String,
    val forbiddenGifts: List<String> = emptyList(),
    val receiver: String = "") {


    fun getName(): String {
        return String().plus(lastName)
            .plus(" ")
            .plus(firstName)
    }


}