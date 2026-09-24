package com.kokoro.ai.ui.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.kokoro.ai.audio.KokoroAudioManager
import com.kokoro.ai.model.*
import com.kokoro.ai.network.*
import com.kokoro.ai.tts.KokoroTtsManager
import com.kokoro.ai.ui.components.getOutfitVisualDetail
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class UiSnackbarMessage(
    val message: String,
    val actionLabel: String? = null
)

class KokoroViewModel(application: Application) : AndroidViewModel(application) {

    private val TAG = "KokoroViewModel"

    private val audioManager = KokoroAudioManager(application.applicationContext)
    private val ttsManager = KokoroTtsManager(application.applicationContext)
    private val apiService = NetworkClient.apiService

    private val _outfits = MutableStateFlow(OutfitRepository.sampleOutfits)
    val outfits: StateFlow<List<Outfit>> = _outfits.asStateFlow()

    private val _equippedOutfit = MutableStateFlow(OutfitRepository.sampleOutfits.first())
    val equippedOutfit: StateFlow<Outfit> = _equippedOutfit.asStateFlow()

    private val _isVipActive = MutableStateFlow(false)
    val isVipActive: StateFlow<Boolean> = _isVipActive.asStateFlow()

    private val _personalitySettings = MutableStateFlow(PersonalitySettings())
    val personalitySettings: StateFlow<PersonalitySettings> = _personalitySettings.asStateFlow()

    private val _companionContext = MutableStateFlow(CompanionContext())
    val companionContext: StateFlow<CompanionContext> = _companionContext.asStateFlow()

    private val _snackbarEvent = MutableStateFlow<UiSnackbarMessage?>(null)
    val snackbarEvent: StateFlow<UiSnackbarMessage?> = _snackbarEvent.asStateFlow()

    private val _showVipDialog = MutableStateFlow(false)
    val showVipDialog: StateFlow<Boolean> = _showVipDialog.asStateFlow()

    private val _showFullBodyModal = MutableStateFlow(false)
    val showFullBodyModal: StateFlow<Boolean> = _showFullBodyModal.asStateFlow()

    // Chat with AI state
    private val _isChatLoading = MutableStateFlow(false)
    val isChatLoading: StateFlow<Boolean> = _isChatLoading.asStateFlow()

    private val _chatInputText = MutableStateFlow("")
    val chatInputText: StateFlow<String> = _chatInputText.asStateFlow()

    val isSpeaking: StateFlow<Boolean> = ttsManager.isSpeaking

    init {
        // Sync real audio player state with companionContext
        viewModelScope.launch {
            audioManager.isPlaying.collect { isPlaying ->
                _companionContext.update { it.copy(isPlaying = isPlaying) }
            }
        }

        viewModelScope.launch {
            audioManager.progress.collect { prog ->
                _companionContext.update { it.copy(progress = prog) }
            }
        }

        // Fetch initial data from backend API
        loadBackendData()
    }

    fun loadBackendData() {
        viewModelScope.launch {
            try {
                // 1. Fetch Dashboard
                val dashRes = apiService.getDashboard()
                if (dashRes.isSuccessful && dashRes.body() != null) {
                    val dash = dashRes.body()!!
                    if (dash.isVip) {
                        applyVipStatus(true)
                    }
                    dash.proactiveDialogue?.let { diag ->
                        _companionContext.update { ctx ->
                            ctx.copy(
                                greeting = diag.message ?: ctx.greeting,
                                subDetail = diag.subtext ?: ctx.subDetail
                            )
                        }
                    }
                }

                // 2. Fetch Wardrobe
                val wardRes = apiService.getWardrobe()
                if (wardRes.isSuccessful && wardRes.body() != null) {
                    val wardrobe = wardRes.body()!!
                    val isVip = wardrobe.vipBanner?.isVipActive == true
                    if (isVip) {
                        applyVipStatus(true)
                    }

                    wardrobe.outfits?.let { backendOutfits ->
                        val updatedList = _outfits.value.map { localOutfit ->
                            val match = backendOutfits.firstOrNull { it.id.equals(localOutfit.backendUuid, ignoreCase = true) }
                            if (match != null) {
                                val unlocked = match.isUnlocked || _isVipActive.value
                                val updated = localOutfit.copy(isUnlocked = unlocked)
                                if (match.isEquipped) {
                                    _equippedOutfit.value = updated
                                }
                                updated
                            } else {
                                localOutfit
                            }
                        }
                        _outfits.value = updatedList
                    }
                }

                // 3. Fetch Personality
                val persRes = apiService.getPersonality()
                if (persRes.isSuccessful && persRes.body() != null) {
                    val pers = persRes.body()!!
                    _personalitySettings.update {
                        it.copy(
                            affectLevel = pers.affectLevel,
                            voicePitch = pers.voicePitch
                        )
                    }
                }
            } catch (e: Exception) {
                Log.w(TAG, "Offline or backend unavailable: ${e.message}")
            }
        }
    }

    private fun applyVipStatus(vipActive: Boolean) {
        _isVipActive.value = vipActive
        if (vipActive) {
            // Unlock all outfits
            _outfits.update { list ->
                list.map { it.copy(isUnlocked = true) }
            }
        }
    }

    // ================= CHAT WITH AI (GEMINI) =================
    fun onChatInputChanged(text: String) {
        _chatInputText.value = text
    }

    fun sendChatMessage(messageToSend: String? = null) {
        val query = (messageToSend ?: _chatInputText.value).trim()
        if (query.isBlank()) return

        _chatInputText.value = ""
        _isChatLoading.value = true

        viewModelScope.launch {
            try {
                val res = apiService.sendChatMessage(ChatRequestDto(message = query))
                if (res.isSuccessful && res.body() != null) {
                    val body = res.body()!!
                    val replyText = body.reply ?: "..."
                    val expression = body.avatarExpression ?: "Cute"

                    _companionContext.update {
                        it.copy(
                            greeting = replyText,
                            subDetail = "Kokoro AI • Modo: ${body.personalityMode ?: "Compañera"}"
                        )
                    }

                    // Read out AI reply with Kokoro's selected voice
                    speakText(replyText)
                } else {
                    _snackbarEvent.value = UiSnackbarMessage("Kokoro no pudo responder: ${res.code()}")
                }
            } catch (e: Exception) {
                Log.e(TAG, "Chat failed: ${e.message}")
                _snackbarEvent.value = UiSnackbarMessage("Error de conexión con Kokoro AI.")
            } finally {
                _isChatLoading.value = false
            }
        }
    }

    // ================= WARDROBE & VIP =================
    fun openFullBodyModal() {
        _showFullBodyModal.value = true
    }

    fun closeFullBodyModal() {
        _showFullBodyModal.value = false
    }

    fun selectOutfit(outfit: Outfit) {
        if (outfit.isVip && !_isVipActive.value && !outfit.isUnlocked) {
            _showVipDialog.value = true
            return
        }

        _equippedOutfit.value = outfit
        val detail = getOutfitVisualDetail(outfit.id)
        _snackbarEvent.value = UiSnackbarMessage("¡Kokoro ahora viste '${outfit.name}'!")
        speakText(detail.quoteOnEquip)

        // Sync with backend API
        viewModelScope.launch {
            try {
                apiService.equipOutfit(outfit.backendUuid)
            } catch (e: Exception) {
                Log.w(TAG, "Backend equip sync skipped: ${e.message}")
            }
        }
    }

    fun dismissVipDialog() {
        _showVipDialog.value = false
    }

    fun activateVipTrial() {
        viewModelScope.launch {
            try {
                val res = apiService.subscribeVipTrial()
                if (res.isSuccessful && res.body()?.success == true) {
                    applyVipStatus(true)
                    _showVipDialog.value = false
                    _snackbarEvent.value = UiSnackbarMessage("✨ ¡Kokoro Plus VIP Activado! Prueba gratis de 7 días iniciada.")
                    speakText("¡Felicidades Matias! Ahora tienes acceso VIP ilimitado a todos mis atuendos y voces.")
                } else {
                    // Fallback local unlock
                    applyVipStatus(true)
                    _showVipDialog.value = false
                    _snackbarEvent.value = UiSnackbarMessage("✨ ¡Kokoro Plus VIP Activado localmente!")
                    speakText("¡Felicidades Matias! Acceso VIP activado.")
                }
            } catch (e: Exception) {
                Log.e(TAG, "VIP subscribe API failed: ${e.message}")
                // Fallback unlock so the user isn't blocked
                applyVipStatus(true)
                _showVipDialog.value = false
                _snackbarEvent.value = UiSnackbarMessage("✨ ¡Kokoro Plus VIP Activado!")
                speakText("¡Felicidades Matias! Acceso VIP activado.")
            }
        }
    }

    // ================= PERSONALITY =================
    fun updateAffectLevel(level: Float) {
        _personalitySettings.update { it.copy(affectLevel = level) }
        updateProactiveGreeting()
        syncPersonalityWithBackend()
    }

    fun updateVoice(voiceId: String) {
        val voice = VoiceRepository.voices.find { it.id == voiceId }
        if (voice?.isVip == true && !_isVipActive.value) {
            _showVipDialog.value = true
        } else {
            _personalitySettings.update { it.copy(selectedVoiceId = voiceId) }
            _snackbarEvent.value = UiSnackbarMessage("Voz seleccionada: ${voice?.name}")
            voice?.let { speakText(it.sampleText) }
            syncPersonalityWithBackend()
        }
    }

    fun updatePitch(pitch: Float) {
        _personalitySettings.update { it.copy(voicePitch = pitch) }
        syncPersonalityWithBackend()
    }

    fun updateSpeed(speed: Float) {
        _personalitySettings.update { it.copy(speechSpeed = speed) }
    }

    private fun syncPersonalityWithBackend() {
        viewModelScope.launch {
            try {
                val s = _personalitySettings.value
                val toneName = VoiceRepository.voices.find { it.id == s.selectedVoiceId }?.name ?: "Hana"
                apiService.updatePersonality(
                    UpdatePersonalityRequestDto(
                        affectLevel = s.affectLevel,
                        voiceTone = toneName,
                        voicePitch = s.voicePitch
                    )
                )
            } catch (e: Exception) {
                Log.w(TAG, "Sync personality skipped: ${e.message}")
            }
        }
    }

    private fun updateProactiveGreeting() {
        val mode = _personalitySettings.value.currentMode
        _companionContext.update { ctx ->
            when (mode) {
                PersonalityMode.TSUNDERE -> ctx.copy(
                    greeting = "¡Buenas noches Matias! ...No es que me importe, pero ya es hora de cenar.",
                    subDetail = "¿Quieres tu ramen de PedidosYa o piensas seguir trabajando sin comer? ¡B-baka!"
                )
                PersonalityMode.DULCE -> ctx.copy(
                    greeting = "Buenas noches Matias, ¿te pido tu ramen favorito en PedidosYa?",
                    subDetail = "Noté que llevas 2 horas trabajando. Te traje té verde para concentrarte. ¿También querés que pida tu cena habitual?"
                )
                PersonalityMode.PROFESIONAL -> ctx.copy(
                    greeting = "Buenas noches Matías. Reporte nocturno: 2 horas continuas de productividad registradas.",
                    subDetail = "Sugerencia de descanso y reposición calórica: Solicitud habitual de ramen en PedidosYa lista para despachar."
                )
            }
        }
    }

    fun speakCurrentGreeting() {
        val text = "${_companionContext.value.greeting} ${_companionContext.value.subDetail}"
        speakText(text)
    }

    fun speakCurrentReaction() {
        speakText(_personalitySettings.value.sampleDialogue)
    }

    fun speakText(text: String) {
        val s = _personalitySettings.value
        ttsManager.speak(text, s.voicePitch, s.speechSpeed, s.selectedVoiceId)
    }

    fun stopSpeaking() {
        ttsManager.stop()
    }

    // Audio Playback Controls (Real Lo-Fi Player)
    fun toggleSpotifyPlayPause() {
        audioManager.toggle()
    }

    fun setSpotifyProgress(progress: Float) {
        audioManager.seekTo(progress)
    }

    fun nextTrack() {
        audioManager.seekTo(0f)
        audioManager.play()
        _companionContext.update { it.copy(currentSongTitle = "Midnight Sakura Lofi", isPlaying = true) }
        _snackbarEvent.value = UiSnackbarMessage("Reproduciendo: Midnight Sakura Lofi")
    }

    fun prevTrack() {
        audioManager.seekTo(0f)
        audioManager.play()
        _companionContext.update { it.copy(currentSongTitle = "Tokyo Rain Lo-Fi", isPlaying = true) }
        _snackbarEvent.value = UiSnackbarMessage("Reproduciendo: Tokyo Rain Lo-Fi")
    }

    fun toggleStudyMode() {
        val newState = !_companionContext.value.isStudyModeActive
        _companionContext.update { it.copy(isStudyModeActive = newState) }
        if (newState) {
            audioManager.play()
            _snackbarEvent.value = UiSnackbarMessage("📚 Study Mode activado: Lo-Fi iniciado y temporizador de 25 min.")
            speakText("Modo estudio activado. Pongamos algo de música Lo-Fi relajante. ¡A concentrarse!")
        } else {
            _snackbarEvent.value = UiSnackbarMessage("Modo estudio finalizado. ¡Excelente progreso!")
            speakText("Sesión de estudio terminada. ¡Hiciste un gran trabajo!")
        }

        viewModelScope.launch {
            try {
                apiService.executeQuickAction(QuickActionRequestDto(actionId = "study_mode"))
            } catch (_: Exception) {}
        }
    }

    fun orderRamenFood() {
        _snackbarEvent.value = UiSnackbarMessage("🍜 Confirmado: Pedido de Ramen enviado a PedidosYa!")
        speakText("¡Entendido! Ya pedí tu ramen favorito con huevo y naruto en PedidosYa. ¡Llegará en breve!")

        viewModelScope.launch {
            try {
                apiService.executeQuickAction(QuickActionRequestDto(actionId = "order_food"))
            } catch (_: Exception) {}
        }
    }

    fun clearSnackbar() {
        _snackbarEvent.value = null
    }

    override fun onCleared() {
        super.onCleared()
        audioManager.release()
        ttsManager.shutdown()
    }
}
