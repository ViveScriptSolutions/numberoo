package com.example.model

enum class Language(val label: String, val flag: String) {
    ENGLISH("English", "🇬🇧"),
    BANGLA("বাংলা", "🇧🇩")
}

data class NumberWord(
    val number: Int,
    val englishWord: String,
    val banglaWord: String,
    val banglaNumeral: String
)
