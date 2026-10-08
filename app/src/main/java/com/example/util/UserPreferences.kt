package com.example.util

import android.content.Context
import android.content.SharedPreferences
import com.example.model.Language

class UserPreferences(context: Context) {
    private val prefs: SharedPreferences =
        context.getSharedPreferences("numberoo_prefs", Context.MODE_PRIVATE)

    companion object {
        private const val KEY_STARS = "stars_collected"
        private const val KEY_SOUND_ENABLED = "sound_enabled"
        private const val KEY_LANGUAGE = "selected_language"
        private const val KEY_PROBLEMS_SOLVED = "problems_solved"
    }

    var starsCollected: Int
        get() = prefs.getInt(KEY_STARS, 0)
        set(value) = prefs.edit().putInt(KEY_STARS, value).apply()

    var isSoundEnabled: Boolean
        get() = prefs.getBoolean(KEY_SOUND_ENABLED, true)
        set(value) = prefs.edit().putBoolean(KEY_SOUND_ENABLED, value).apply()

    var selectedLanguage: Language
        get() {
            val name = prefs.getString(KEY_LANGUAGE, Language.ENGLISH.name)
            return try {
                Language.valueOf(name ?: Language.ENGLISH.name)
            } catch (e: Exception) {
                Language.ENGLISH
            }
        }
        set(value) = prefs.edit().putString(KEY_LANGUAGE, value.name).apply()

    var problemsSolved: Int
        get() = prefs.getInt(KEY_PROBLEMS_SOLVED, 0)
        set(value) = prefs.edit().putInt(KEY_PROBLEMS_SOLVED, value).apply()

    fun addStar(): Int {
        val newStars = starsCollected + 1
        starsCollected = newStars
        problemsSolved = problemsSolved + 1
        return newStars
    }
}
