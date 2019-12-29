package com.apoichet.santa

class SecretSantaResult(val giver: String,
                        val receiver: String,
                        val mailGiver: String,
                        val wishList: List<String> = listOf(),
                        val spendingBudget: Float = 0F)