package com.kokoro.ai.model

enum class PersonalityMode(val title: String, val badge: String, val tag: String) {
    TSUNDERE("Tsundere", "傲娇", "Picante"),
    DULCE("Dulce & Cariñosa", "甘えん坊", "Afectuosa"),
    PROFESIONAL("Profesional", "秘書", "Eficiente")
}

data class VoiceOption(
    val id: String,
    val name: String,
    val toneDescription: String,
    val isVip: Boolean,
    val sampleText: String
)

data class PersonalitySettings(
    val affectLevel: Float = 0.5f,
    val voicePitch: Float = 1.0f,
    val speechSpeed: Float = 1.0f,
    val selectedVoiceId: String = "hana"
) {
    val currentMode: PersonalityMode
        get() = when {
            affectLevel < 0.35f -> PersonalityMode.TSUNDERE
            affectLevel <= 0.75f -> PersonalityMode.DULCE
            else -> PersonalityMode.PROFESIONAL
        }

    val sampleDialogue: String
        get() = when (currentMode) {
            PersonalityMode.TSUNDERE ->
                "¡B-baka! No es que me preocupe por ti o algo así... pero toma un poco de agua y descansa un momento, ¿sí?"
            PersonalityMode.DULCE ->
                "¡Te extrañé mucho Matias! Siempre estoy aquí para apoyarte y cuidarte. ¡Haces un gran trabajo hoy! ♡"
            PersonalityMode.PROFESIONAL ->
                "Buenas tardes Matías. Tu agenda se encuentra optimizada y las prioridades de trabajo están listas. ¿Deseas iniciar la sesión?"
        }
}

object VoiceRepository {
    val voices = listOf(
        VoiceOption(
            id = "hana",
            name = "Hana (Suave - Gratis)",
            toneDescription = "Tono dulce, cálido y reconfortante waifu",
            isVip = false,
            sampleText = "¡Hola Matias! Estoy aquí para acompañarte y cuidarte hoy."
        ),
        VoiceOption(
            id = "aoi",
            name = "Aoi (Neutra - Gratis)",
            toneDescription = "Voz equilibrada, serena, óptima para lectura",
            isVip = false,
            sampleText = "Tu agenda y recordatorios están listos para la sesión."
        ),
        VoiceOption(
            id = "luz",
            name = "Luz (Alegre - Gratis)",
            toneDescription = "Entonación entusiasta, brillante y vivaz",
            isVip = false,
            sampleText = "¡Vamos con toda la energía hoy, eres el mejor!"
        ),
        VoiceOption(
            id = "elena",
            name = "Elena (Serena - Gratis)",
            toneDescription = "Voz pausada, madura y relajante para estudiar",
            isVip = false,
            sampleText = "Respira hondo y concéntrate, lo estás haciendo excelente."
        ),
        VoiceOption(
            id = "yuki",
            name = "Yuki (Enérgica - VIP)",
            toneDescription = "Animada, alegre con matices kawaii japoneses",
            isVip = true,
            sampleText = "¡Yay! ¡Kokoro Plus te da energía infinita!"
        ),
        VoiceOption(
            id = "rei",
            name = "Rei (Madura - VIP)",
            toneDescription = "Voz profunda, elegante y de timbre sedoso",
            isVip = true,
            sampleText = "Permíteme encargarme de todos los detalles por ti."
        )
    )
}
