package com.example.model

enum class LearningMode(
    val englishTitle: String,
    val banglaTitle: String,
    val iconEmoji: String
) {
    NUMBER_LOOP("Numbers 1–20", "১–২০ শিখি", "🔄"),
    COUNT_ANIMALS("Count 1–10", "১–১০ গুনি", "🔢"),
    FIND_NUMBER("Find & Tap", "খুঁজে বের করো", "🎯")
}

data class FindNumberQuestion(
    val targetNumber: Int,
    val itemType: CountingItemType,
    val options: List<Int>
)
