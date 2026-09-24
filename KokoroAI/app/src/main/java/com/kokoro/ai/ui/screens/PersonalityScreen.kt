package com.kokoro.ai.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kokoro.ai.model.PersonalityMode
import com.kokoro.ai.model.VoiceOption
import com.kokoro.ai.model.VoiceRepository
import com.kokoro.ai.ui.components.GlassCard
import com.kokoro.ai.ui.theme.*
import com.kokoro.ai.ui.viewmodel.KokoroViewModel

@Composable
fun PersonalityScreen(
    viewModel: KokoroViewModel,
    modifier: Modifier = Modifier
) {
    val settings by viewModel.personalitySettings.collectAsState()
    val isVipActive by viewModel.isVipActive.collectAsState()

    var isPreviewPlaying by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(KokoroGradientBackground)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp)
            .padding(top = 16.dp, bottom = 96.dp)
    ) {
        // Header
        PersonalityHeader()

        Spacer(modifier = Modifier.height(14.dp))

        // Section 1: Nivel de Afecto Slider
        GlassCard(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(26.dp),
            backgroundColor = Color(0xF5FFFFFF),
            elevation = 8.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(18.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Nivel de Afecto & Modo",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = KokoroTextPrimary
                    )

                    // Current Mode Badge
                    Box(
                        modifier = Modifier
                            .background(
                                brush = Brush.horizontalGradient(listOf(KokoroViolet, KokoroPink)),
                                shape = RoundedCornerShape(12.dp)
                            )
                            .padding(horizontal = 10.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = "${settings.currentMode.badge} ${settings.currentMode.title}",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Slider
                Slider(
                    value = settings.affectLevel,
                    onValueChange = { viewModel.updateAffectLevel(it) },
                    valueRange = 0.0f..1.0f,
                    colors = SliderDefaults.colors(
                        thumbColor = KokoroPink,
                        activeTrackColor = KokoroViolet,
                        inactiveTrackColor = KokoroViolet.copy(alpha = 0.18f)
                    )
                )

                // Slider labels
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Tsundere\n(0.0)",
                        fontSize = 11.sp,
                        color = if (settings.currentMode == PersonalityMode.TSUNDERE) KokoroPink else KokoroTextHint,
                        fontWeight = if (settings.currentMode == PersonalityMode.TSUNDERE) FontWeight.Bold else FontWeight.Normal,
                        textAlign = TextAlign.Start
                    )
                    Text(
                        text = "Dulce & Waifu\n(0.5)",
                        fontSize = 11.sp,
                        color = if (settings.currentMode == PersonalityMode.DULCE) KokoroViolet else KokoroTextHint,
                        fontWeight = if (settings.currentMode == PersonalityMode.DULCE) FontWeight.Bold else FontWeight.Normal,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = "Profesional\n(1.0)",
                        fontSize = 11.sp,
                        color = if (settings.currentMode == PersonalityMode.PROFESIONAL) KokoroVioletDark else KokoroTextHint,
                        fontWeight = if (settings.currentMode == PersonalityMode.PROFESIONAL) FontWeight.Bold else FontWeight.Normal,
                        textAlign = TextAlign.End
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Live reactive dialogue box
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFFFAF5FF), RoundedCornerShape(16.dp))
                        .border(1.dp, KokoroViolet.copy(alpha = 0.25f), RoundedCornerShape(16.dp))
                        .padding(14.dp)
                ) {
                    Column {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(text = "💬", fontSize = 14.sp)
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "Reacción en tiempo real de Kokoro:",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = KokoroViolet
                                )
                            }

                            IconButton(
                                onClick = { viewModel.speakCurrentReaction() },
                                modifier = Modifier
                                    .size(30.dp)
                                    .background(KokoroPink.copy(alpha = 0.15f), CircleShape)
                            ) {
                                Icon(
                                    imageVector = Icons.Rounded.VolumeUp,
                                    contentDescription = "Escuchar reacción",
                                    tint = KokoroPink,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "\"${settings.sampleDialogue}\"",
                            fontSize = 13.sp,
                            color = KokoroTextPrimary,
                            lineHeight = 18.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Section 2: Selector de Voz Neuronal
        Text(
            text = "Voz de Lectura de Kokoro",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = KokoroTextPrimary
        )

        Spacer(modifier = Modifier.height(10.dp))

        VoiceRepository.voices.forEach { voice ->
            val isSelected = voice.id == settings.selectedVoiceId
            val isLocked = voice.isVip && !isVipActive

            VoiceOptionCard(
                voice = voice,
                isSelected = isSelected,
                isLocked = isLocked,
                onClick = { viewModel.updateVoice(voice.id) },
                onPreviewClick = {
                    viewModel.speakText(voice.sampleText)
                }
            )

            Spacer(modifier = Modifier.height(10.dp))
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Section 3: Moduladores de Audio (Pitch & Speed)
        GlassCard(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            backgroundColor = Color(0xF5FFFFFF),
            elevation = 8.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(18.dp)
            ) {
                Text(
                    text = "Ajustes Finos de Audio",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = KokoroTextPrimary
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Pitch Slider
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Tono de Voz (Pitch)",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium,
                        color = KokoroTextPrimary
                    )
                    Text(
                        text = String.format("%.1fx", settings.voicePitch),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = KokoroViolet
                    )
                }

                Slider(
                    value = settings.voicePitch,
                    onValueChange = { viewModel.updatePitch(it) },
                    valueRange = 0.5f..1.5f,
                    colors = SliderDefaults.colors(
                        thumbColor = KokoroViolet,
                        activeTrackColor = KokoroViolet,
                        inactiveTrackColor = KokoroViolet.copy(alpha = 0.2f)
                    )
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Grave", fontSize = 10.sp, color = KokoroTextHint)
                    Text(text = "Normal", fontSize = 10.sp, color = KokoroTextHint)
                    Text(text = "Agudo", fontSize = 10.sp, color = KokoroTextHint)
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Speech Speed Slider
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Velocidad de Habla (Speed)",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium,
                        color = KokoroTextPrimary
                    )
                    Text(
                        text = String.format("%.2fx", settings.speechSpeed),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = KokoroPink
                    )
                }

                Slider(
                    value = settings.speechSpeed,
                    onValueChange = { viewModel.updateSpeed(it) },
                    valueRange = 0.75f..1.25f,
                    colors = SliderDefaults.colors(
                        thumbColor = KokoroPink,
                        activeTrackColor = KokoroPink,
                        inactiveTrackColor = KokoroPink.copy(alpha = 0.2f)
                    )
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Pausada", fontSize = 10.sp, color = KokoroTextHint)
                    Text(text = "Natural", fontSize = 10.sp, color = KokoroTextHint)
                    Text(text = "Rápida", fontSize = 10.sp, color = KokoroTextHint)
                }
            }
        }
    }
}

@Composable
fun PersonalityHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = "Personalidad & Voz",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = KokoroTextPrimary
            )
            Text(
                text = "Modula el afecto y timbre de Kokoro",
                fontSize = 13.sp,
                color = KokoroTextSecondary
            )
        }

        Box(
            modifier = Modifier
                .size(40.dp)
                .shadow(4.dp, CircleShape)
                .background(Color.White, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "🎙️", fontSize = 18.sp)
        }
    }
}

@Composable
fun VoiceOptionCard(
    voice: VoiceOption,
    isSelected: Boolean,
    isLocked: Boolean,
    onClick: () -> Unit,
    onPreviewClick: () -> Unit
) {
    val borderColor by animateColorAsState(
        targetValue = if (isSelected) KokoroViolet else Color.White,
        label = "voiceBorder"
    )

    GlassCard(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(20.dp),
        backgroundColor = if (isSelected) Color(0xFFFBF7FF) else Color.White,
        borderColor = borderColor,
        elevation = if (isSelected) 6.dp else 2.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                // Radio button circle
                Box(
                    modifier = Modifier
                        .size(22.dp)
                        .background(
                            if (isSelected) KokoroViolet else Color.Transparent,
                            CircleShape
                        )
                        .border(
                            2.dp,
                            if (isSelected) KokoroViolet else KokoroTextHint.copy(alpha = 0.5f),
                            CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    if (isSelected) {
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .background(Color.White, CircleShape)
                        )
                    }
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = voice.name,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = KokoroTextPrimary
                        )

                        if (voice.isVip) {
                            Spacer(modifier = Modifier.width(8.dp))
                            Box(
                                modifier = Modifier
                                    .background(KokoroGold.copy(alpha = 0.15f), RoundedCornerShape(8.dp))
                                    .padding(horizontal = 6.dp, vertical = 2.dp)
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    if (isLocked) {
                                        Icon(
                                            imageVector = Icons.Rounded.Lock,
                                            contentDescription = "Lock",
                                            tint = KokoroGold,
                                            modifier = Modifier.size(10.dp)
                                        )
                                        Spacer(modifier = Modifier.width(2.dp))
                                    }
                                    Text(
                                        text = "VIP",
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = KokoroGold
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(2.dp))

                    Text(
                        text = voice.toneDescription,
                        fontSize = 12.sp,
                        color = KokoroTextSecondary
                    )
                }
            }

            // Preview Speaker button
            IconButton(
                onClick = onPreviewClick,
                modifier = Modifier
                    .size(36.dp)
                    .background(KokoroViolet.copy(alpha = 0.1f), CircleShape)
            ) {
                Icon(
                    imageVector = Icons.Rounded.VolumeUp,
                    contentDescription = "Preview voice",
                    tint = KokoroViolet,
                    modifier = Modifier.size(18.dp)
                )
            }
        }
    }
}
