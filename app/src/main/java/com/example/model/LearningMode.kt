package com.example.model

enum class LearningMode(
    val englishTitle: String,
    val banglaTitle: String,
    val iconEmoji: String
) {
    LEARN("Learn", "সংখ্যা শিখি", "🔤"),
    COUNT("Count", "গণনা করি", "🔢"),
    ADD("Add (+)", "যোগ করি", "➕"),
    SUBTRACT("Subtract (−)", "বিয়োগ করি", "➖")
}

data class MathProblem(
    val operand1: Int,
    val operand2: Int,
    val isAddition: Boolean,
    val itemType: CountingItemType,
    val options: List<Int>
) {
    val correctAnswer: Int
        get() = if (isAddition) operand1 + operand2 else operand1 - operand2
}
