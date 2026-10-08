package com.example.util

import com.example.model.Language
import com.example.model.NumberWord

object NumberFormatter {

    private val banglaDigits = charArrayOf('০', '১', '২', '৩', '৪', '৫', '৬', '৭', '৮', '৯')

    val numberWordsList: List<NumberWord> = listOf(
        NumberWord(0, "Zero", "শূন্য", "০"),
        NumberWord(1, "One", "এক", "১"),
        NumberWord(2, "Two", "দুই", "২"),
        NumberWord(3, "Three", "তিন", "৩"),
        NumberWord(4, "Four", "চার", "৪"),
        NumberWord(5, "Five", "পাঁচ", "৫"),
        NumberWord(6, "Six", "ছয়", "৬"),
        NumberWord(7, "Seven", "সাত", "৭"),
        NumberWord(8, "Eight", "আট", "৮"),
        NumberWord(9, "Nine", "নয়", "৯"),
        NumberWord(10, "Ten", "দশ", "১০"),
        NumberWord(11, "Eleven", "এগারো", "১১"),
        NumberWord(12, "Twelve", "বারো", "১২"),
        NumberWord(13, "Thirteen", "তেরো", "১৩"),
        NumberWord(14, "Fourteen", "চৌদ্দ", "১৪"),
        NumberWord(15, "Fifteen", "পনেরো", "১৫"),
        NumberWord(16, "Sixteen", "ষোলো", "১৬"),
        NumberWord(17, "Seventeen", "সতেরো", "১৭"),
        NumberWord(18, "Eighteen", "আঠারো", "১৮"),
        NumberWord(19, "Nineteen", "উনিশ", "১৯"),
        NumberWord(20, "Twenty", "বিশ", "২০")
    )

    fun formatNumber(number: Int, language: Language): String {
        return if (language == Language.BANGLA) {
            toBanglaNumeral(number)
        } else {
            number.toString()
        }
    }

    fun toBanglaNumeral(number: Int): String {
        val str = number.toString()
        val sb = StringBuilder()
        for (ch in str) {
            if (ch in '0'..'9') {
                sb.append(banglaDigits[ch - '0'])
            } else {
                sb.append(ch)
            }
        }
        return sb.toString()
    }

    fun getWord(number: Int, language: Language): String {
        val entry = numberWordsList.getOrNull(number)
        return when {
            entry != null && language == Language.BANGLA -> entry.banglaWord
            entry != null -> entry.englishWord
            language == Language.BANGLA -> toBanglaNumeral(number)
            else -> number.toString()
        }
    }
}
