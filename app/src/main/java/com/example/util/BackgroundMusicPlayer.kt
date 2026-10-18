package com.example.util

import android.media.AudioAttributes
import android.media.AudioFormat
import android.media.AudioTrack
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlin.math.sin

/**
 * Generates and loops a soft, pleasant, mild music-box lullaby for toddlers.
 * Uses native AudioTrack synthesis: 100% offline, zero external audio asset required,
 * low CPU and memory footprint.
 */
class BackgroundMusicPlayer {

    private var audioTrack: AudioTrack? = null
    private var playbackJob: Job? = null
    private val scope = CoroutineScope(Dispatchers.Default)

    @Volatile
    var isPlaying: Boolean = false
        private set

    companion object {
        private const val SAMPLE_RATE = 22050

        // Gentle, soothing nursery melody frequencies (Hz) - Marimba / Music Box tone
        // C4, E4, G4, A4, G4, E4, D4, C4, G3, C4
        private val MELODY_NOTES = listOf(
            261.63, 329.63, 392.00, 440.00,
            392.00, 329.63, 293.66, 261.63,
            392.00, 440.00, 523.25, 440.00,
            392.00, 329.63, 293.66, 261.63
        )
        // Duration of each note in seconds (relaxed, gentle tempo ~90 bpm)
        private const val NOTE_DURATION = 0.55
    }

    init {
        try {
            val minBufferSize = AudioTrack.getMinBufferSize(
                SAMPLE_RATE,
                AudioFormat.CHANNEL_OUT_MONO,
                AudioFormat.ENCODING_PCM_16BIT
            )
            audioTrack = AudioTrack.Builder()
                .setAudioAttributes(
                    AudioAttributes.Builder()
                        .setUsage(AudioAttributes.USAGE_GAME)
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .build()
                )
                .setAudioFormat(
                    AudioFormat.Builder()
                        .setEncoding(AudioFormat.ENCODING_PCM_16BIT)
                        .setSampleRate(SAMPLE_RATE)
                        .setChannelMask(AudioFormat.CHANNEL_OUT_MONO)
                        .build()
                )
                .setBufferSizeInBytes(minBufferSize * 2)
                .setTransferMode(AudioTrack.MODE_STREAM)
                .build()
        } catch (e: Exception) {
            // AudioTrack initialization fallback
        }
    }

    /**
     * Synthesizes a soft, warm music box note with exponential decay (zero click/pop).
     */
    private fun generateNoteSample(freq: Double, durationSec: Double): ShortArray {
        val totalSamples = (SAMPLE_RATE * durationSec).toInt()
        val buffer = ShortArray(totalSamples)
        val angularFreq = 2.0 * Math.PI * freq / SAMPLE_RATE

        for (i in 0 until totalSamples) {
            val t = i.toDouble() / totalSamples
            // Gentle music box envelope: quick attack (0.05), soft exponential bell decay
            val envelope = if (t < 0.05) {
                t / 0.05
            } else {
                Math.exp(-3.5 * (t - 0.05))
            }
            // Fundamental sine with soft warm second harmonic (like a soft wooden marimba)
            val sample = (sin(angularFreq * i) * 0.75 + sin(angularFreq * 2.0 * i) * 0.25)
            // Mild volume for background accompaniment so it never overwhelms speech
            val amplitude = (sample * envelope * 0.14 * Short.MAX_VALUE).toInt()
            buffer[i] = amplitude.coerceIn(Short.MIN_VALUE.toInt(), Short.MAX_VALUE.toInt()).toShort()
        }
        return buffer
    }

    fun start() {
        if (isPlaying || audioTrack == null) return
        isPlaying = true

        try {
            audioTrack?.play()
        } catch (e: Exception) {
            return
        }

        playbackJob = scope.launch {
            // Pre-synthesize the melody loop to ensure zero latency during playback
            val preGeneratedNotes = MELODY_NOTES.map { freq ->
                generateNoteSample(freq, NOTE_DURATION)
            }

            while (isActive && isPlaying) {
                for (noteBuffer in preGeneratedNotes) {
                    if (!isActive || !isPlaying) break
                    audioTrack?.write(noteBuffer, 0, noteBuffer.size)
                }
                // Gentle 0.6s pause between melody loops
                val pauseSamples = ShortArray((SAMPLE_RATE * 0.6).toInt())
                if (isActive && isPlaying) {
                    audioTrack?.write(pauseSamples, 0, pauseSamples.size)
                }
            }
        }
    }

    fun pause() {
        isPlaying = false
        playbackJob?.cancel()
        playbackJob = null
        try {
            audioTrack?.pause()
            audioTrack?.flush()
        } catch (e: Exception) {
            // Ignore
        }
    }

    fun release() {
        pause()
        try {
            audioTrack?.stop()
            audioTrack?.release()
            audioTrack = null
        } catch (e: Exception) {
            // Ignore
        }
    }
}
