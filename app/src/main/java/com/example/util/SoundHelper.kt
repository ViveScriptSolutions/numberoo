package com.example.util

import android.content.Context
import android.media.AudioManager
import android.media.ToneGenerator
import android.os.Build
import android.speech.tts.TextToSpeech
import com.example.model.Language
import java.util.Locale

class SoundHelper(private val context: Context) : TextToSpeech.OnInitListener {

    private var toneGenerator: ToneGenerator? = null
    private var tts: TextToSpeech? = null
    private var isTtsReady = false
    var isSoundEnabled: Boolean = true

    init {
        try {
            toneGenerator = ToneGenerator(AudioManager.STREAM_MUSIC, 75)
        } catch (e: Exception) {
            // Audio track fallback
        }
        try {
            tts = TextToSpeech(context.applicationContext, this)
        } catch (e: Exception) {
            // TTS fallback
        }
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            isTtsReady = true
            tts?.setPitch(1.25f) // Cheerful, warm, child-friendly pitch
            tts?.setSpeechRate(0.85f) // Clear pacing for 3-6 year olds
        }
    }

    fun playPop() {
        if (!isSoundEnabled) return
        try {
            toneGenerator?.startTone(ToneGenerator.TONE_PROP_BEEP, 60)
        } catch (e: Exception) {
            // Ignore
        }
    }

    fun playSuccess() {
        if (!isSoundEnabled) return
        try {
            toneGenerator?.startTone(ToneGenerator.TONE_PROP_ACK, 220)
        } catch (e: Exception) {
            // Ignore
        }
    }

    fun playError() {
        if (!isSoundEnabled) return
        try {
            toneGenerator?.startTone(ToneGenerator.TONE_PROP_NACK, 140)
        } catch (e: Exception) {
            // Ignore
        }
    }

    fun playFanfare() {
        if (!isSoundEnabled) return
        try {
            toneGenerator?.startTone(ToneGenerator.TONE_CDMA_ALERT_NETWORK_LITE, 300)
        } catch (e: Exception) {
            // Ignore
        }
    }

    fun speak(text: String, language: Language) {
        if (!isSoundEnabled || !isTtsReady || tts == null) return
        try {
            if (language == Language.BANGLA) {
                val banglaLocale = Locale.forLanguageTag("bn-BD")
                val result = tts?.setLanguage(banglaLocale)
                if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                    tts?.language = Locale.US
                }
            } else {
                tts?.language = Locale.US
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null, "NUMBEROO_TTS")
            } else {
                @Suppress("DEPRECATION")
                tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null)
            }
        } catch (e: Exception) {
            // Fallback
        }
    }

    fun speakNumberWithAnimal(
        number: Int,
        numberWord: String,
        animalNameSingular: String,
        animalNamePlural: String,
        banglaName: String,
        language: Language
    ) {
        val speech = if (language == Language.BANGLA) {
            "$numberWord! $banglaName!"
        } else {
            val animal = if (number == 1) animalNameSingular else animalNamePlural
            "$numberWord! $animal!"
        }
        speak(speech, language)
    }

    fun release() {
        try {
            toneGenerator?.release()
            toneGenerator = null
        } catch (e: Exception) {
            // Ignore
        }
        try {
            tts?.stop()
            tts?.shutdown()
            tts = null
        } catch (e: Exception) {
            // Ignore
        }
    }
}
