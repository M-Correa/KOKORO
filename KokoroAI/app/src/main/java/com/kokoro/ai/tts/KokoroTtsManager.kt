package com.kokoro.ai.tts

import android.content.Context
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import android.speech.tts.Voice
import android.util.Log
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.Locale

class KokoroTtsManager(private val context: Context) : TextToSpeech.OnInitListener {

    private val TAG = "KokoroTtsManager"
    private var tts: TextToSpeech? = null
    private var isInitialized = false

    private val _isSpeaking = MutableStateFlow(false)
    val isSpeaking: StateFlow<Boolean> = _isSpeaking.asStateFlow()

    private var femaleVoices = mutableListOf<Voice>()

    init {
        // Initialize Google TTS engine explicitly if available
        tts = TextToSpeech(context, this, "com.google.android.tts")
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            // Priority: Neutral/Latin American Spanish (soft, cute tone without Iberian Spanish accent)
            val preferredLocales = listOf(
                Locale("es", "MX"),
                Locale("es", "US"),
                Locale("es", "419"),
                Locale("es")
            )

            var selectedLocale = Locale("es", "MX")
            for (loc in preferredLocales) {
                val res = tts?.setLanguage(loc)
                if (res != TextToSpeech.LANG_MISSING_DATA && res != TextToSpeech.LANG_NOT_SUPPORTED) {
                    selectedLocale = loc
                    break
                }
            }

            // Find high-quality female voices available in Google TTS
            try {
                val availableVoices = tts?.voices ?: emptySet()
                femaleVoices = availableVoices.filter { voice ->
                    val isSpanish = voice.locale.language.equals("es", ignoreCase = true)
                    val isFemaleOrHighQuality = voice.name.contains("female", ignoreCase = true) ||
                            voice.name.contains("#female", ignoreCase = true) ||
                            voice.name.contains("es-") ||
                            voice.quality >= Voice.QUALITY_HIGH
                    isSpanish && isFemaleOrHighQuality && !voice.isNetworkConnectionRequired
                }.sortedByDescending { it.quality }.toMutableList()

                // If high-quality voices found, set the top female one
                if (femaleVoices.isNotEmpty()) {
                    tts?.voice = femaleVoices.first()
                    Log.d(TAG, "Selected anime-optimized voice: ${femaleVoices.first().name}")
                }
            } catch (e: Exception) {
                Log.w(TAG, "Error filtering custom voices: ${e.message}")
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
        } else {
            // Fallback initialization with default engine if Google TTS package is absent
            tts = TextToSpeech(context) { st ->
                if (st == TextToSpeech.SUCCESS) {
                    tts?.language = Locale("es", "MX")
                    isInitialized = true
                }
            }
        }
    }

    fun speak(text: String, pitch: Float = 1.0f, speed: Float = 1.0f, voiceId: String = "hana") {
        if (!isInitialized || tts == null) return

        // Calibrated anime tone multipliers: higher pitch, energetic, sweet cadence
        val (basePitch, baseSpeed) = when (voiceId) {
            // Hana: Super sweet anime waifu (high cute pitch, gentle speed)
            "hana" -> Pair(1.36f, 1.02f)
            // Aoi: Calm moe / smart student waifu (warm high tone)
            "aoi" -> Pair(1.26f, 1.00f)
            // Luz: Energetic Genki girl waifu (bright, lively, cheerful)
            "luz" -> Pair(1.44f, 1.08f)
            // Elena: Soft onee-san / mature gentle waifu (soft melody)
            "elena" -> Pair(1.18f, 0.96f)
            // Yuki (VIP): Ultra kawaii anime idol
            "yuki" -> Pair(1.48f, 1.05f)
            // Rei (VIP): Elegant tsundere waifu
            "rei" -> Pair(1.22f, 1.02f)
            else -> Pair(1.30f, 1.02f)
        }

        val finalPitch = (pitch * basePitch).coerceIn(0.8f, 2.0f)
        val finalSpeed = (speed * baseSpeed).coerceIn(0.7f, 1.6f)

        tts?.setPitch(finalPitch)
        tts?.setSpeechRate(finalSpeed)

        // Select voice variant if multiple female voices available
        if (femaleVoices.isNotEmpty()) {
            val voiceIndex = when (voiceId) {
                "luz", "yuki" -> if (femaleVoices.size > 1) 1 else 0
                "elena", "rei" -> if (femaleVoices.size > 2) 2 else 0
                else -> 0
            }
            tts?.voice = femaleVoices.getOrNull(voiceIndex) ?: femaleVoices.first()
        }

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
