package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.model.CountingThemes
import com.example.model.FindNumberQuestion
import com.example.model.Language
import com.example.model.LearningMode
import com.example.util.NumberFormatter
import com.example.util.UserPreferences
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
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
        assertEquals("Twenty", NumberFormatter.getWord(20, Language.ENGLISH))
        assertEquals("বিশ", NumberFormatter.getWord(20, Language.BANGLA))
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
    fun countingThemes_has20ItemsForToddlers() {
        assertEquals(20, CountingThemes.items.size)
        val kangaroo = CountingThemes.getItemForNumber(1)
        assertEquals("kangaroo", kangaroo.id)
        assertEquals("🦘", kangaroo.emoji)

        val star = CountingThemes.getItemForNumber(20)
        assertEquals("star", star.id)
        assertEquals("⭐", star.emoji)
    }

    @Test
    fun learningModes_containExpectedToddlerModes() {
        val modes = LearningMode.values()
        assertEquals(3, modes.size)
        assertTrue(modes.contains(LearningMode.NUMBER_LOOP))
        assertTrue(modes.contains(LearningMode.COUNT_ANIMALS))
        assertTrue(modes.contains(LearningMode.FIND_NUMBER))
    }
}
