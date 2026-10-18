package com.example.model

import androidx.compose.ui.graphics.Color

data class CountingItemType(
    val id: String,
    val emoji: String,
    val soundEffectType: String,
    val englishNameSingular: String,
    val englishNamePlural: String,
    val banglaName: String,
    val primaryColor: Color,
    val backgroundColor: Color
)

object CountingThemes {
    val items = listOf(
        CountingItemType(
            id = "kangaroo",
            emoji = "🦘",
            soundEffectType = "hop",
            englishNameSingular = "kangaroo",
            englishNamePlural = "kangaroos",
            banglaName = "ক্যাঙ্গারু",
            primaryColor = Color(0xFFF57C00),
            backgroundColor = Color(0xFFFFF3E0)
        ),
        CountingItemType(
            id = "kitten",
            emoji = "🐱",
            soundEffectType = "meow",
            englishNameSingular = "kitten",
            englishNamePlural = "kittens",
            banglaName = "বিড়ালছানা",
            primaryColor = Color(0xFFFF9800),
            backgroundColor = Color(0xFFFFF8E1)
        ),
        CountingItemType(
            id = "puppy",
            emoji = "🐶",
            soundEffectType = "bark",
            englishNameSingular = "puppy",
            englishNamePlural = "puppies",
            banglaName = "কুকুরছানা",
            primaryColor = Color(0xFF8D6E63),
            backgroundColor = Color(0xFFEFEBE9)
        ),
        CountingItemType(
            id = "frog",
            emoji = "🐸",
            soundEffectType = "croak",
            englishNameSingular = "frog",
            englishNamePlural = "frogs",
            banglaName = "ব্যাঙ",
            primaryColor = Color(0xFF43A047),
            backgroundColor = Color(0xFFE8F5E9)
        ),
        CountingItemType(
            id = "duck",
            emoji = "🦆",
            soundEffectType = "quack",
            englishNameSingular = "duck",
            englishNamePlural = "ducks",
            banglaName = "হাঁস",
            primaryColor = Color(0xFFFBC02D),
            backgroundColor = Color(0xFFFFFDE7)
        ),
        CountingItemType(
            id = "bunny",
            emoji = "🐰",
            soundEffectType = "hop",
            englishNameSingular = "bunny",
            englishNamePlural = "bunnies",
            banglaName = "খরগোশ",
            primaryColor = Color(0xFFEC407A),
            backgroundColor = Color(0xFFFCE4EC)
        ),
        CountingItemType(
            id = "butterfly",
            emoji = "🦋",
            soundEffectType = "flutter",
            englishNameSingular = "butterfly",
            englishNamePlural = "butterflies",
            banglaName = "প্রজাপতি",
            primaryColor = Color(0xFF00ACC1),
            backgroundColor = Color(0xFFE0F7FA)
        ),
        CountingItemType(
            id = "bird",
            emoji = "🐦",
            soundEffectType = "chirp",
            englishNameSingular = "bird",
            englishNamePlural = "birds",
            banglaName = "পাখি",
            primaryColor = Color(0xFF039BE5),
            backgroundColor = Color(0xFFE1F5FE)
        ),
        CountingItemType(
            id = "fish",
            emoji = "🐠",
            soundEffectType = "splash",
            englishNameSingular = "fish",
            englishNamePlural = "fish",
            banglaName = "মাছ",
            primaryColor = Color(0xFFFF7043),
            backgroundColor = Color(0xFFFBE9E7)
        ),
        CountingItemType(
            id = "monkey",
            emoji = "🐵",
            soundEffectType = "cheer",
            englishNameSingular = "monkey",
            englishNamePlural = "monkeys",
            banglaName = "বানর",
            primaryColor = Color(0xFF795548),
            backgroundColor = Color(0xFFD7CCC8)
        ),
        CountingItemType(
            id = "bear",
            emoji = "🐻",
            soundEffectType = "growl",
            englishNameSingular = "bear",
            englishNamePlural = "bears",
            banglaName = "ভাল্লুক",
            primaryColor = Color(0xFF6D4C41),
            backgroundColor = Color(0xFFD7CCC8)
        ),
        CountingItemType(
            id = "elephant",
            emoji = "🐘",
            soundEffectType = "trumpet",
            englishNameSingular = "elephant",
            englishNamePlural = "elephants",
            banglaName = "হাতি",
            primaryColor = Color(0xFF546E7A),
            backgroundColor = Color(0xFFECEFF1)
        ),
        CountingItemType(
            id = "lion",
            emoji = "🦁",
            soundEffectType = "roar",
            englishNameSingular = "lion",
            englishNamePlural = "lions",
            banglaName = "সিংহ",
            primaryColor = Color(0xFFFB8C00),
            backgroundColor = Color(0xFFFFF3E0)
        ),
        CountingItemType(
            id = "turtle",
            emoji = "🐢",
            soundEffectType = "step",
            englishNameSingular = "turtle",
            englishNamePlural = "turtles",
            banglaName = "কচ্ছপ",
            primaryColor = Color(0xFF2E7D32),
            backgroundColor = Color(0xFFE8F5E9)
        ),
        CountingItemType(
            id = "bee",
            emoji = "🐝",
            soundEffectType = "buzz",
            englishNameSingular = "bee",
            englishNamePlural = "bees",
            banglaName = "মৌমাছি",
            primaryColor = Color(0xFFF57F17),
            backgroundColor = Color(0xFFFFF9C4)
        ),
        CountingItemType(
            id = "strawberry",
            emoji = "🍓",
            soundEffectType = "pop",
            englishNameSingular = "strawberry",
            englishNamePlural = "strawberries",
            banglaName = "স্ট্রবেরি",
            primaryColor = Color(0xFFD81B60),
            backgroundColor = Color(0xFFFCE4EC)
        ),
        CountingItemType(
            id = "balloon",
            emoji = "🎈",
            soundEffectType = "pop",
            englishNameSingular = "balloon",
            englishNamePlural = "balloons",
            banglaName = "বেলুন",
            primaryColor = Color(0xFFE91E63),
            backgroundColor = Color(0xFFFCE4EC)
        ),
        CountingItemType(
            id = "apple",
            emoji = "🍎",
            soundEffectType = "crunch",
            englishNameSingular = "apple",
            englishNamePlural = "apples",
            banglaName = "আপেল",
            primaryColor = Color(0xFFE53935),
            backgroundColor = Color(0xFFFFEBEE)
        ),
        CountingItemType(
            id = "car",
            emoji = "🚗",
            soundEffectType = "beep",
            englishNameSingular = "toy car",
            englishNamePlural = "toy cars",
            banglaName = "খেলনা গাড়ি",
            primaryColor = Color(0xFF1E88E5),
            backgroundColor = Color(0xFFE3F2FD)
        ),
        CountingItemType(
            id = "star",
            emoji = "⭐",
            soundEffectType = "twinkle",
            englishNameSingular = "star",
            englishNamePlural = "stars",
            banglaName = "তারা",
            primaryColor = Color(0xFFFFA000),
            backgroundColor = Color(0xFFFFF8E1)
        )
    )

    fun random(): CountingItemType = items.random()

    fun getItemForNumber(number: Int): CountingItemType {
        val index = (number - 1).coerceIn(0, items.size - 1)
        return items[index]
    }
}
