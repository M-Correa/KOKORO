package com.kokoro.ai.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material.icons.rounded.Star
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
import com.kokoro.ai.model.Outfit
import com.kokoro.ai.ui.components.GlassCard
import com.kokoro.ai.ui.theme.*
import com.kokoro.ai.ui.viewmodel.KokoroViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WardrobeScreen(
    viewModel: KokoroViewModel,
    modifier: Modifier = Modifier
) {
    val outfits by viewModel.outfits.collectAsState()
    val equippedOutfit by viewModel.equippedOutfit.collectAsState()
    val isVipActive by viewModel.isVipActive.collectAsState()
    val showVipDialog by viewModel.showVipDialog.collectAsState()

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(KokoroGradientBackground)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp)
                .padding(top = 16.dp, bottom = 88.dp)
        ) {
            // Header
            WardrobeHeader()

            Spacer(modifier = Modifier.height(14.dp))

            // Kokoro Plus VIP Banner
            KokoroPlusVipBanner(
                isVipActive = isVipActive,
                onActivateClick = { viewModel.activateVipTrial() }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Subtitle & Count
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Guardarropa de Kokoro",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = KokoroTextPrimary
                )
                Text(
                    text = "${outfits.size} Atuendos",
                    fontSize = 12.sp,
                    color = KokoroViolet,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // 2-Column Grid of Outfits
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.spacedBy(14.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp),
                contentPadding = PaddingValues(bottom = 20.dp)
            ) {
                items(outfits, key = { it.id }) { outfit ->
                    OutfitCard(
                        outfit = outfit,
                        isEquipped = outfit.id == equippedOutfit.id,
                        isVipUnlocked = isVipActive,
                        onClick = { viewModel.selectOutfit(outfit) }
                    )
                }
            }
        }

        // VIP Subscription Bottom Sheet Modal
        if (showVipDialog) {
            ModalBottomSheet(
                onDismissRequest = { viewModel.dismissVipDialog() },
                containerColor = Color.White,
                shape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp)
            ) {
                VipSubscriptionSheetContent(
                    onSubscribe = { viewModel.activateVipTrial() },
                    onDismiss = { viewModel.dismissVipDialog() }
                )
            }
        }
    }
}

@Composable
fun WardrobeHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = "Guardarropa & VIP",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = KokoroTextPrimary
            )
            Text(
                text = "Personaliza el estilo visual de tu waifu",
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
            Text(text = "✨", fontSize = 18.sp)
        }
    }
}

@Composable
fun KokoroPlusVipBanner(
    isVipActive: Boolean,
    onActivateClick: () -> Unit
) {
    GlassCard(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        backgroundColor = if (isVipActive) Color(0xFFF3E8FF) else Color.White.copy(alpha = 0.85f),
        elevation = 8.dp
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            KokoroViolet.copy(alpha = 0.12f),
                            KokoroPink.copy(alpha = 0.15f),
                            KokoroGold.copy(alpha = 0.12f)
                        )
                    )
                )
                .padding(18.dp)
        ) {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = "👑", fontSize = 20.sp)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Kokoro Plus VIP",
                            fontSize = 17.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = KokoroViolet
                        )
                    }

                    if (isVipActive) {
                        Box(
                            modifier = Modifier
                                .background(KokoroGreen, RoundedCornerShape(12.dp))
                                .padding(horizontal = 10.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = "ACTIVO",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = if (isVipActive)
                        "Tienes acceso ilimitado a todos los trajes exclusivos y voces neuronales."
                    else
                        "Desbloquea todos los atuendos, voces neuronales y modo ilimitado.",
                    fontSize = 13.sp,
                    color = KokoroTextSecondary,
                    lineHeight = 18.sp
                )

                Spacer(modifier = Modifier.height(12.dp))

                if (!isVipActive) {
                    Button(
                        onClick = onActivateClick,
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(18.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent
                        ),
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(KokoroGradientVip, RoundedCornerShape(18.dp))
                                .padding(vertical = 12.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = "Activar Prueba Gratis de 7 Días",
                                    color = Color.White,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(text = "⚡", fontSize = 14.sp)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun OutfitCard(
    outfit: Outfit,
    isEquipped: Boolean,
    isVipUnlocked: Boolean,
    onClick: () -> Unit
) {
    val isLocked = outfit.isVip && !isVipUnlocked

    GlassCard(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(22.dp),
        backgroundColor = if (isEquipped) Color(0xFFFBF7FF) else Color.White,
        borderColor = if (isEquipped) KokoroViolet else Color.White,
        elevation = if (isEquipped) 8.dp else 4.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Top Badge Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Category Tag
                Text(
                    text = outfit.category,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Medium,
                    color = KokoroTextHint
                )

                // Status Badge
                when {
                    isEquipped -> {
                        Box(
                            modifier = Modifier
                                .background(KokoroViolet, RoundedCornerShape(8.dp))
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = "Equipado",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }
                    }
                    outfit.isVip -> {
                        Box(
                            modifier = Modifier
                                .background(
                                    if (isVipUnlocked) KokoroGreen.copy(alpha = 0.2f) else KokoroGold.copy(alpha = 0.15f),
                                    RoundedCornerShape(8.dp)
                                )
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                if (isLocked) {
                                    Icon(
                                        imageVector = Icons.Rounded.Lock,
                                        contentDescription = "VIP Lock",
                                        tint = KokoroGold,
                                        modifier = Modifier.size(10.dp)
                                    )
                                    Spacer(modifier = Modifier.width(3.dp))
                                }
                                Text(
                                    text = "VIP",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (isVipUnlocked) KokoroGreen else KokoroGold
                                )
                            }
                        }
                    }
                    else -> {
                        Box(
                            modifier = Modifier
                                .background(KokoroViolet.copy(alpha = 0.1f), RoundedCornerShape(8.dp))
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = "Gratis",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Medium,
                                color = KokoroViolet
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Big Emoji / Outfit visual container
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .shadow(4.dp, CircleShape)
                    .background(outfit.accentColor.copy(alpha = 0.15f), CircleShape)
                    .border(1.dp, outfit.accentColor.copy(alpha = 0.3f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = outfit.emoji,
                    fontSize = 32.sp
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Outfit Name
            Text(
                text = outfit.name,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = KokoroTextPrimary,
                textAlign = TextAlign.Center,
                maxLines = 1
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = outfit.description,
                fontSize = 11.sp,
                color = KokoroTextSecondary,
                textAlign = TextAlign.Center,
                maxLines = 2,
                lineHeight = 14.sp
            )
        }
    }
}

@Composable
fun VipSubscriptionSheetContent(
    onSubscribe: () -> Unit,
    onDismiss: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(64.dp)
                .background(
                    brush = KokoroGradientVip,
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "👑", fontSize = 32.sp)
        }

        Spacer(modifier = Modifier.height(14.dp))

        Text(
            text = "Kokoro Plus VIP",
            fontSize = 22.sp,
            fontWeight = FontWeight.ExtraBold,
            color = KokoroTextPrimary
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = "Acceso total e ilimitado al universo de Kokoro AI",
            fontSize = 14.sp,
            color = KokoroTextSecondary,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(20.dp))

        VipFeatureRow(emoji = "👗", text = "Desbloqueo de los 8 trajes temáticos y futuros atuendos")
        VipFeatureRow(emoji = "🎙️", text = "Voces neuronales de alta fidelidad (Yuki y Rei)")
        VipFeatureRow(emoji = "🧠", text = "Diálogos contextuales avanzados sin límite de mensajes")
        VipFeatureRow(emoji = "⚡", text = "Modo Zero-UI con pedidos prioritarios y sincronización")

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = onSubscribe,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
            contentPadding = PaddingValues(0.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(KokoroGradientVip, RoundedCornerShape(20.dp))
                    .padding(vertical = 14.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Comenzar Prueba Gratis de 7 Días",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        TextButton(onClick = onDismiss) {
            Text(
                text = "Quizás más tarde",
                fontSize = 13.sp,
                color = KokoroTextHint
            )
        }
    }
}

@Composable
fun VipFeatureRow(emoji: String, text: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(32.dp)
                .background(KokoroViolet.copy(alpha = 0.1f), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(text = emoji, fontSize = 16.sp)
        }
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = text,
            fontSize = 13.sp,
            color = KokoroTextPrimary,
            fontWeight = FontWeight.Medium
        )
    }
}
