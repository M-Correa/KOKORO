package com.kokoro.ai.tts

import android.content.Context
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.Locale

class KokoroTtsManager(private val context: Context) : TextToSpeech.OnInitListener {

    private var tts: TextToSpeech? = null
    private var isInitialized = false

    private val _isSpeaking = MutableStateFlow(false)
    val isSpeaking: StateFlow<Boolean> = _isSpeaking.asStateFlow()

    init {
        tts = TextToSpeech(context, this)
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = tts?.setLanguage(Locale("es", "ES"))
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                // Fallback to generic Spanish or default locale
                tts?.setLanguage(Locale("es"))
            }

            tts?.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
                override fun onStart(utteranceId: String?) {
                    _isSpeaking.value = true
                }

                override fun onDone(utteranceId: String?) {
                    _isSpeaking.value = false
                }

                @Deprecated("Deprecated in Java")
                override fun onError(utteranceId: String?) {
                    _isSpeaking.value = false
                }
            })
            isInitialized = true
        }
    }

    fun speak(text: String, pitch: Float = 1.0f, speed: Float = 1.0f, voiceId: String = "hana") {
        if (!isInitialized || tts == null) return

        // Voice modulation characteristics
        val basePitchMultiplier = when (voiceId) {
            "hana" -> 1.15f  // Sweet, cute waifu tone
            "aoi" -> 1.0f    // Clear neutral tone
            "luz" -> 1.25f   // Cheerful bright tone
            "elena" -> 0.88f // Calm mature tone
            "yuki" -> 1.28f  // Energetic
            "rei" -> 0.92f   // Deep elegant
            else -> 1.0f
        }

        val baseSpeedMultiplier = when (voiceId) {
            "hana" -> 0.95f
            "aoi" -> 1.0f
            "luz" -> 1.10f
            "elena" -> 0.92f
            else -> 1.0f
        }

        val finalPitch = (pitch * basePitchMultiplier).coerceIn(0.5f, 2.0f)
        val finalSpeed = (speed * baseSpeedMultiplier).coerceIn(0.5f, 2.0f)

        tts?.setPitch(finalPitch)
        tts?.setSpeechRate(finalSpeed)

        val utteranceId = "kokoro_utterance_${System.currentTimeMillis()}"
        tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null, utteranceId)
    }

    fun stop() {
        tts?.stop()
        _isSpeaking.value = false
    }

    fun shutdown() {
        try {
            tts?.stop()
            tts?.shutdown()
            tts = null
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
