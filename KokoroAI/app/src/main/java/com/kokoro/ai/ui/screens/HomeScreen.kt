package com.kokoro.ai.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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
import com.kokoro.ai.model.CompanionContext
import com.kokoro.ai.model.Outfit
import com.kokoro.ai.ui.components.GlassCard
import com.kokoro.ai.ui.components.KokoroFullBodyModal
import com.kokoro.ai.ui.components.QuickActionPills
import com.kokoro.ai.ui.components.SpotifyWidget
import com.kokoro.ai.ui.components.WaifuAvatar
import com.kokoro.ai.ui.theme.*
import com.kokoro.ai.ui.viewmodel.KokoroViewModel

@Composable
fun HomeScreen(
    viewModel: KokoroViewModel,
    onNavigateToWardrobe: () -> Unit,
    modifier: Modifier = Modifier
) {
    val companionContext by viewModel.companionContext.collectAsState()
    val equippedOutfit by viewModel.equippedOutfit.collectAsState()
    val allOutfits by viewModel.outfits.collectAsState()
    val isVipActive by viewModel.isVipActive.collectAsState()
    val isSpeaking by viewModel.isSpeaking.collectAsState()
    val showFullBodyModal by viewModel.showFullBodyModal.collectAsState()

    var showReplySent by remember { mutableStateOf(false) }

    Box(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(KokoroGradientBackground)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp)
                .padding(top = 16.dp, bottom = 96.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Top Bar: App Title & Status
            HomeHeader(isVipActive = isVipActive)

            Spacer(modifier = Modifier.height(10.dp))

            // Hero Waifu Avatar with breathing micro-animation & click to open full body view
            WaifuAvatar(
                equippedOutfit = equippedOutfit,
                onAvatarClick = {
                    viewModel.openFullBodyModal()
                }
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Floating Glassmorphic Contextual Dialogue Card
            GlassCard(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(26.dp),
                backgroundColor = Color(0xF5FFFFFF),
                borderColor = Color.White,
                elevation = 12.dp
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                ) {
                    // Status tag and Speaker Button
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(8.dp)
                                    .background(KokoroGreen, CircleShape)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "¡Hola Feliz! • Paso para cuidarte",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = KokoroViolet
                            )
                        }

                        // Button to make Kokoro read her message out loud!
                        IconButton(
                            onClick = {
                                if (isSpeaking) viewModel.stopSpeaking()
                                else viewModel.speakCurrentGreeting()
                            },
                            modifier = Modifier
                                .size(34.dp)
                                .shadow(4.dp, CircleShape)
                                .background(
                                    if (isSpeaking) KokoroPink else KokoroViolet.copy(alpha = 0.15f),
                                    CircleShape
                                )
                        ) {
                            Icon(
                                imageVector = if (isSpeaking) Icons.Rounded.VolumeUp else Icons.Rounded.VolumeDown,
                                contentDescription = "Escuchar respuesta",
                                tint = if (isSpeaking) Color.White else KokoroViolet,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // Proactive greeting text
                    Text(
                        text = companionContext.greeting,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        color = KokoroTextPrimary,
                        lineHeight = 23.sp
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = companionContext.subDetail,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Normal,
                        color = KokoroTextSecondary,
                        lineHeight = 19.sp
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    // Interactive Quick Reply buttons
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Button(
                            onClick = {
                                viewModel.orderRamenFood()
                                showReplySent = true
                            },
                            modifier = Modifier.weight(1.3f),
                            shape = RoundedCornerShape(20.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = PedidosYaPink
                            ),
                            contentPadding = PaddingValues(vertical = 10.dp)
                        ) {
                            Text(
                                text = "¡Pedir Ramen!",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }

                        OutlinedButton(
                            onClick = {
                                showReplySent = false
                                viewModel.speakText("De acuerdo Matias, descansamos un ratito más.")
                            },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(20.dp),
                            colors = ButtonDefaults.outlinedButtonColors(
                                contentColor = KokoroTextSecondary
                            ),
                            border = androidx.compose.foundation.BorderStroke(1.dp, KokoroViolet.copy(alpha = 0.3f)),
                            contentPadding = PaddingValues(vertical = 10.dp)
                        ) {
                            Text(
                                text = "Más tarde",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // ================= CHAT CON KOKORO (GEMINI AI) =================
                    val chatText by viewModel.chatInputText.collectAsState()
                    val isChatLoading by viewModel.isChatLoading.collectAsState()

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color(0xFFF5F0FF), RoundedCornerShape(24.dp))
                            .border(1.dp, KokoroViolet.copy(alpha = 0.35f), RoundedCornerShape(24.dp))
                            .padding(horizontal = 12.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        TextField(
                            value = chatText,
                            onValueChange = { viewModel.onChatInputChanged(it) },
                            placeholder = {
                                Text(
                                    text = "Escribe algo a Kokoro AI...",
                                    fontSize = 13.sp,
                                    color = KokoroTextHint
                                )
                            },
                            modifier = Modifier.weight(1f),
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = Color.Transparent,
                                unfocusedContainerColor = Color.Transparent,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent
                            ),
                            singleLine = true,
                            enabled = !isChatLoading
                        )

                        if (isChatLoading) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(24.dp),
                                color = KokoroPink,
                                strokeWidth = 2.dp
                            )
                        } else {
                            IconButton(
                                onClick = { viewModel.sendChatMessage() },
                                modifier = Modifier
                                    .size(36.dp)
                                    .background(
                                        Brush.linearGradient(listOf(KokoroViolet, KokoroPink)),
                                        CircleShape
                                    )
                            ) {
                                Icon(
                                    imageVector = Icons.Rounded.Send,
                                    contentDescription = "Enviar mensaje a Gemini",
                                    tint = Color.White,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Spotify Lo-Fi Beats Widget (Real Audio Player)
            SpotifyWidget(
                isPlaying = companionContext.isPlaying,
                progress = companionContext.progress,
                onPlayPauseToggle = { viewModel.toggleSpotifyPlayPause() },
                onProgressChange = { viewModel.setSpotifyProgress(it) },
                onNextTrack = { viewModel.nextTrack() },
                onPrevTrack = { viewModel.prevTrack() }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Acciones Proactivas Header & Quick Action Pills
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Acciones Proactivas",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = KokoroTextPrimary
                )
                Text(
                    text = "Zero-UI",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = KokoroViolet
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            QuickActionPills(
                isStudyModeActive = companionContext.isStudyModeActive,
                onOrderFoodClick = { viewModel.orderRamenFood() },
                onStudyModeClick = { viewModel.toggleStudyMode() },
                onWardrobeClick = onNavigateToWardrobe
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Alarma & Rutina Card
            AlarmRoutineCard()
        }

        // Full Body Character Visualizer Modal
        if (showFullBodyModal) {
            KokoroFullBodyModal(
                currentOutfit = equippedOutfit,
                allOutfits = allOutfits,
                isVipActive = isVipActive,
                onSelectOutfit = { outfit ->
                    viewModel.selectOutfit(outfit)
                },
                onSpeakQuote = { quote ->
                    viewModel.speakText(quote)
                },
                onDismiss = {
                    viewModel.closeFullBodyModal()
                }
            )
        }
    }
}

@Composable
fun HomeHeader(isVipActive: Boolean) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Heart icon left
        Box(
            modifier = Modifier
                .size(38.dp)
                .shadow(4.dp, CircleShape)
                .background(Color.White.copy(alpha = 0.9f), CircleShape)
                .border(1.dp, Color.White, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "💖", fontSize = 16.sp)
        }

        // Center Title
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "KOKORO ",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = KokoroTextPrimary,
                    letterSpacing = 1.sp
                )
                Text(
                    text = "AI✦",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = KokoroPink,
                    letterSpacing = 1.sp
                )
            }
            Text(
                text = if (isVipActive) "Kokoro Plus VIP ✨" else "Modo Compañera Activa",
                fontSize = 11.sp,
                fontWeight = FontWeight.Medium,
                color = if (isVipActive) KokoroGold else KokoroViolet
            )
        }

        // Profile / Bot bubble right
        Box(
            modifier = Modifier
                .size(38.dp)
                .shadow(4.dp, CircleShape)
                .background(Color.White.copy(alpha = 0.9f), CircleShape)
                .border(1.dp, Color.White, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "👾", fontSize = 16.sp)
        }
    }
}

@Composable
fun AlarmRoutineCard() {
    GlassCard(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(22.dp),
        backgroundColor = Color(0xF2FFFFFF),
        borderColor = Color.White,
        elevation = 6.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(42.dp)
                        .background(KokoroViolet.copy(alpha = 0.12f), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "⏰", fontSize = 20.sp)
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = "Alarma Mañana 07:30",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = KokoroTextPrimary
                    )
                    Text(
                        text = "Despertador con voz dulce activado",
                        fontSize = 12.sp,
                        color = KokoroTextSecondary
                    )
                }
            }

            Switch(
                checked = true,
                onCheckedChange = {},
                colors = SwitchDefaults.colors(
                    checkedThumbColor = Color.White,
                    checkedTrackColor = KokoroViolet,
                    uncheckedThumbColor = Color.White,
                    uncheckedTrackColor = Color.LightGray
                )
            )
        }
    }
}
