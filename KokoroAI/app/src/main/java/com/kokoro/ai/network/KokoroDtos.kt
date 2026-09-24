package com.kokoro.ai.network

import com.google.gson.annotations.SerializedName

// ================= CHAT =================
data class ChatRequestDto(
    @SerializedName("message") val message: String
)

data class ChatResponseDto(
    @SerializedName("reply") val reply: String?,
    @SerializedName("personalityMode") val personalityMode: String?,
    @SerializedName("avatarExpression") val avatarExpression: String?,
    @SerializedName("detectedIntent") val detectedIntent: String?,
    @SerializedName("suggestedAction") val suggestedAction: String?,
    @SerializedName("isAiGenerated") val isAiGenerated: Boolean = false,
    @SerializedName("timestamp") val timestamp: String?
)

// ================= DASHBOARD =================
data class DashboardResponseDto(
    @SerializedName("userName") val userName: String?,
    @SerializedName("isVip") val isVip: Boolean = false,
    @SerializedName("avatar") val avatar: AvatarHeroDto?,
    @SerializedName("proactiveDialogue") val proactiveDialogue: ProactiveDialogueDto?,
    @SerializedName("spotifyWidget") val spotifyWidget: SpotifyWidgetDto?,
    @SerializedName("quickActions") val quickActions: List<QuickActionPillDto>?
)

data class AvatarHeroDto(
    @SerializedName("name") val name: String?,
    @SerializedName("equippedOutfitName") val equippedOutfitName: String?,
    @SerializedName("avatarImageUrl") val avatarImageUrl: String?,
    @SerializedName("currentExpression") val currentExpression: String?
)

data class ProactiveDialogueDto(
    @SerializedName("message") val message: String?,
    @SerializedName("subtext") val subtext: String?,
    @SerializedName("suggestedAction") val suggestedAction: String?,
    @SerializedName("suggestedActionLabel") val suggestedActionLabel: String?,
    @SerializedName("timestamp") val timestamp: String?
)

data class SpotifyWidgetDto(
    @SerializedName("trackTitle") val trackTitle: String?,
    @SerializedName("artist") val artist: String?,
    @SerializedName("albumCoverUrl") val albumCoverUrl: String?,
    @SerializedName("isPlaying") val isPlaying: Boolean = false,
    @SerializedName("currentProgressSeconds") val currentProgressSeconds: Int = 0,
    @SerializedName("totalDurationSeconds") val totalDurationSeconds: Int = 0
)

data class QuickActionPillDto(
    @SerializedName("id") val id: String?,
    @SerializedName("label") val label: String?,
    @SerializedName("icon") val icon: String?,
    @SerializedName("category") val category: String?
)

data class QuickActionRequestDto(
    @SerializedName("actionId") val actionId: String
)

data class QuickActionResultDto(
    @SerializedName("success") val success: Boolean = false,
    @SerializedName("message") val message: String?
)

// ================= WARDROBE & VIP =================
data class WardrobeResponseDto(
    @SerializedName("vipBanner") val vipBanner: VipBannerDto?,
    @SerializedName("outfits") val outfits: List<OutfitItemDto>?
)

data class VipBannerDto(
    @SerializedName("isVipActive") val isVipActive: Boolean = false,
    @SerializedName("title") val title: String?,
    @SerializedName("subtitle") val subtitle: String?,
    @SerializedName("ctaButtonText") val ctaButtonText: String?,
    @SerializedName("daysRemaining") val daysRemaining: Int?
)

data class OutfitItemDto(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String?,
    @SerializedName("description") val description: String?,
    @SerializedName("category") val category: String?,
    @SerializedName("imageUrl") val imageUrl: String?,
    @SerializedName("isVipOnly") val isVipOnly: Boolean = false,
    @SerializedName("isUnlocked") val isUnlocked: Boolean = false,
    @SerializedName("isEquipped") val isEquipped: Boolean = false
)

data class EquipOutfitResponseDto(
    @SerializedName("success") val success: Boolean = false,
    @SerializedName("message") val message: String?,
    @SerializedName("equippedOutfit") val equippedOutfit: OutfitItemDto?
)

data class SubscribeVipResponseDto(
    @SerializedName("success") val success: Boolean = false,
    @SerializedName("message") val message: String?,
    @SerializedName("expiresAt") val expiresAt: String?
)

// ================= PERSONALITY =================
data class PersonalitySettingsDto(
    @SerializedName("affectLevel") val affectLevel: Float = 0.5f,
    @SerializedName("affectLabel") val affectLabel: String?,
    @SerializedName("voiceTone") val voiceTone: String?,
    @SerializedName("voicePitch") val voicePitch: Float = 1.0f,
    @SerializedName("availableVoiceTones") val availableVoiceTones: List<String>?
)

data class UpdatePersonalityRequestDto(
    @SerializedName("affectLevel") val affectLevel: Float,
    @SerializedName("voiceTone") val voiceTone: String,
    @SerializedName("voicePitch") val voicePitch: Float
)
