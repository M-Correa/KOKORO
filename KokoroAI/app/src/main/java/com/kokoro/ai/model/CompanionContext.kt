package com.kokoro.ai.model

data class CompanionContext(
    val userName: String = "Matias",
    val greeting: String = "Buenas noches Matias, ¿te pido tu ramen favorito en PedidosYa?",
    val subDetail: String = "Noté que llevas 2 horas trabajando. Te traje té verde para concentrarte. ¿También querés que pida tu cena habitual?",
    val currentSongTitle: String = "Lo-Fi Beats",
    val currentArtist: String = "Kokoro AI Playlist",
    val isPlaying: Boolean = true,
    val progress: Float = 0.42f,
    val isStudyModeActive: Boolean = false,
    val studyTimeMinutesRemaining: Int = 25,
    val isVipMember: Boolean = false
)
