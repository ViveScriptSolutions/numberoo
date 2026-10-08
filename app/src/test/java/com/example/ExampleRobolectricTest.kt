package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.model.CountingThemes
import com.example.model.Language
import com.example.model.MathProblem
import com.example.util.NumberFormatter
import com.example.util.UserPreferences
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

    @Test
    fun readStringFromContext_returnsNumberoo() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val appName = context.getString(R.string.app_name)
        assertEquals("Numberoo", appName)
    }

    @Test
    fun numberFormatter_formatsBanglaNumeralsCorrectly() {
        assertEquals("০", NumberFormatter.toBanglaNumeral(0))
        assertEquals("১", NumberFormatter.toBanglaNumeral(1))
        assertEquals("৫", NumberFormatter.toBanglaNumeral(5))
        assertEquals("১০", NumberFormatter.toBanglaNumeral(10))
        assertEquals("২০", NumberFormatter.toBanglaNumeral(20))
    }

    @Test
    fun numberFormatter_returnsCorrectWordsInBothLanguages() {
        assertEquals("Five", NumberFormatter.getWord(5, Language.ENGLISH))
        assertEquals("পাঁচ", NumberFormatter.getWord(5, Language.BANGLA))
        assertEquals("Ten", NumberFormatter.getWord(10, Language.ENGLISH))
        assertEquals("দশ", NumberFormatter.getWord(10, Language.BANGLA))
    }

    @Test
    fun userPreferences_addsAndRetrievesStars() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val prefs = UserPreferences(context)
        val initialStars = prefs.starsCollected
        val newStars = prefs.addStar()
        assertEquals(initialStars + 1, newStars)
        assertEquals(initialStars + 1, prefs.starsCollected)
    }

    @Test
    fun mathProblem_calculatesCorrectAdditionAndSubtraction() {
        val item = CountingThemes.items.first()
        val addProblem = MathProblem(operand1 = 3, operand2 = 2, isAddition = true, itemType = item, options = listOf(4, 5, 6))
        assertEquals(5, addProblem.correctAnswer)

        val subProblem = MathProblem(operand1 = 5, operand2 = 3, isAddition = false, itemType = item, options = listOf(1, 2, 3))
        assertEquals(2, subProblem.correctAnswer)
    }
}
