package com.example.model

import androidx.compose.ui.graphics.Color

data class CountingItemType(
    val id: String,
    val emoji: String,
    val englishNameSingular: String,
    val englishNamePlural: String,
    val banglaName: String,
    val primaryColor: Color,
    val backgroundColor: Color
)

object CountingThemes {
    val items = listOf(
        CountingItemType(
            id = "apple",
            emoji = "🍎",
            englishNameSingular = "apple",
            englishNamePlural = "apples",
            banglaName = "আপেল",
            primaryColor = Color(0xFFE53935),
            backgroundColor = Color(0xFFFFEBEE)
        ),
        CountingItemType(
            id = "star",
            emoji = "⭐",
            englishNameSingular = "star",
            englishNamePlural = "stars",
            banglaName = "তারা",
            primaryColor = Color(0xFFFFA000),
            backgroundColor = Color(0xFFFFF8E1)
        ),
        CountingItemType(
            id = "balloon",
            emoji = "🎈",
            englishNameSingular = "balloon",
            englishNamePlural = "balloons",
            banglaName = "বেলুন",
            primaryColor = Color(0xFFE91E63),
            backgroundColor = Color(0xFFFCE4EC)
        ),
        CountingItemType(
            id = "duck",
            emoji = "🦆",
            englishNameSingular = "duck",
            englishNamePlural = "ducks",
            banglaName = "হাঁস",
            primaryColor = Color(0xFFFBC02D),
            backgroundColor = Color(0xFFFFFDE7)
        ),
        CountingItemType(
            id = "kitten",
            emoji = "🐱",
            englishNameSingular = "kitten",
            englishNamePlural = "kittens",
            banglaName = "বিড়ালছানা",
            primaryColor = Color(0xFFFF9800),
            backgroundColor = Color(0xFFFFF3E0)
        ),
        CountingItemType(
            id = "car",
            emoji = "🚗",
            englishNameSingular = "toy car",
            englishNamePlural = "toy cars",
            banglaName = "খেলনা গাড়ি",
            primaryColor = Color(0xFF1E88E5),
            backgroundColor = Color(0xFFE3F2FD)
        ),
        CountingItemType(
            id = "strawberry",
            emoji = "🍓",
            englishNameSingular = "strawberry",
            englishNamePlural = "strawberries",
            banglaName = "স্ট্রবেরি",
            primaryColor = Color(0xFFD81B60),
            backgroundColor = Color(0xFFFCE4EC)
        ),
        CountingItemType(
            id = "cupcake",
            emoji = "🧁",
            englishNameSingular = "cupcake",
            englishNamePlural = "cupcakes",
            banglaName = "কাপকেক",
            primaryColor = Color(0xFF8E24AA),
            backgroundColor = Color(0xFFF3E5F5)
        ),
        CountingItemType(
            id = "butterfly",
            emoji = "🦋",
            englishNameSingular = "butterfly",
            englishNamePlural = "butterflies",
            banglaName = "প্রজাপতি",
            primaryColor = Color(0xFF00ACC1),
            backgroundColor = Color(0xFFE0F7FA)
        ),
        CountingItemType(
            id = "flower",
            emoji = "🌸",
            englishNameSingular = "flower",
            englishNamePlural = "flowers",
            banglaName = "ফুল",
            primaryColor = Color(0xFFEC407A),
            backgroundColor = Color(0xFFFCE4EC)
        )
    )

    fun random(): CountingItemType = items.random()

    fun getById(id: String): CountingItemType = items.find { it.id == id } ?: items.first()
}
