package com.kokoro.ai.model

import androidx.compose.ui.graphics.Color
import com.kokoro.ai.R

data class Outfit(
    val id: String,                  // Can be local key or backend UUID
    val backendUuid: String,         // Backend UUID for /api/Wardrobe/equip/{id}
    val name: String,
    val category: String,
    val description: String,
    val isVip: Boolean,
    val isUnlocked: Boolean = !isVip,
    val accentColor: Color,
    val emoji: String,
    val fullBodyImageRes: Int? = null
)

object OutfitRepository {
    val sampleOutfits = listOf(
        Outfit(
            id = "school_uniform",
            backendUuid = "33333333-3333-3333-3333-333333333331",
            name = "Uniforme Escolar Seifuku",
            category = "Clásico",
            description = "El clásico uniforme marinero japonés de preparatoria.",
            isVip = false,
            isUnlocked = true,
            accentColor = Color(0xFF8B5CF6),
            emoji = "🎒",
            fullBodyImageRes = R.drawable.kokoro_outfit_school
        ),
        Outfit(
            id = "casual_hoodie",
            backendUuid = "33333333-3333-3333-3333-333333333332",
            name = "Ropa Casual Suéter Lo-Fi",
            category = "Casual",
            description = "Buzo oversize lavanda pastel con shorts y calcetines a rayas, ideal para estudiar.",
            isVip = false,
            isUnlocked = true,
            accentColor = Color(0xFFA855F7),
            emoji = "☕",
            fullBodyImageRes = R.drawable.kokoro_outfit_hoodie
        ),
        Outfit(
            id = "cat_pajamas",
            backendUuid = "33333333-3333-3333-3333-333333333333",
            name = "Pijama Kigurumi Gatito",
            category = "Pijama",
            description = "Enterizo afelpado rosa con capucha de orejitas de gato y detalles rosas.",
            isVip = false,
            isUnlocked = true,
            accentColor = Color(0xFFF472B6),
            emoji = "🐾",
            fullBodyImageRes = R.drawable.kokoro_outfit_pajama
        ),
        Outfit(
            id = "sporty_fitness",
            backendUuid = "33333333-3333-3333-3333-333333333334",
            name = "Deportivo Fitness Runner",
            category = "Deporte",
            description = "Conjunto deportivo transpirable para sesiones de running.",
            isVip = false,
            isUnlocked = true,
            accentColor = Color(0xFF10B981),
            emoji = "👟",
            fullBodyImageRes = R.drawable.kokoro_outfit_sporty
        ),
        Outfit(
            id = "traditional_kimono",
            backendUuid = "33333333-3333-3333-3333-333333333335",
            name = "Kimono Ceremonial de Seda",
            category = "Tradicional",
            description = "Kimono ceremonial tejido en seda con faja obi bordada en oro.",
            isVip = false,
            isUnlocked = true,
            accentColor = Color(0xFFF59E0B),
            emoji = "👘",
            fullBodyImageRes = R.drawable.kokoro_outfit_kimono
        ),
        Outfit(
            id = "elegant_maid",
            backendUuid = "33333333-3333-3333-3333-333333333336",
            name = "Maid Clásica Victoriana",
            category = "Especial VIP",
            description = "Vestido de maid clásico con delantal de encaje fino.",
            isVip = true,
            isUnlocked = false,
            accentColor = Color(0xFF6366F1),
            emoji = "🎀",
            fullBodyImageRes = R.drawable.kokoro_outfit_maid
        ),
        Outfit(
            id = "summer_yukata",
            backendUuid = "33333333-3333-3333-3333-333333333337",
            name = "Yukata Festivo de Verano",
            category = "Tradicional VIP",
            description = "Yukata ligera de flores de sakura para festivales nocturnos.",
            isVip = true,
            isUnlocked = false,
            accentColor = Color(0xFFEC4899),
            emoji = "🌸",
            fullBodyImageRes = R.drawable.kokoro_outfit_yukata
        ),
        Outfit(
            id = "cyberpunk_cosplay",
            backendUuid = "33333333-3333-3333-3333-333333333338",
            name = "Cyberpunk Neon Idol",
            category = "Futurista VIP",
            description = "Chaqueta holográfica con neones rosa y visera tecnológica.",
            isVip = true,
            isUnlocked = false,
            accentColor = Color(0xFF06B6D4),
            emoji = "⚡",
            fullBodyImageRes = R.drawable.kokoro_outfit_cyberpunk
        )
    )

    fun findByUuidOrId(identifier: String): Outfit? {
        return sampleOutfits.firstOrNull { it.backendUuid.equals(identifier, ignoreCase = true) || it.id.equals(identifier, ignoreCase = true) }
    }
}
