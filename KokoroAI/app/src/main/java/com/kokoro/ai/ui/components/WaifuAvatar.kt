package com.kokoro.ai.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kokoro.ai.R
import com.kokoro.ai.model.Outfit
import com.kokoro.ai.ui.theme.KokoroPink
import com.kokoro.ai.ui.theme.KokoroViolet

@Composable
fun WaifuAvatar(
    equippedOutfit: Outfit,
    onAvatarClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "breathing")

    // Subtle breathing animation
    val scale by infiniteTransition.animateFloat(
        initialValue = 0.985f,
        targetValue = 1.015f,
        animationSpec = infiniteRepeatable(
            animation = tween(2400, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "scale"
    )

    // Gentle vertical float
    val floatOffset by infiniteTransition.animateFloat(
        initialValue = -3f,
        targetValue = 3f,
        animationSpec = infiniteRepeatable(
            animation = tween(2800, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "float"
    )

    // Pulse aura alpha
    val auraAlpha by infiniteTransition.animateFloat(
        initialValue = 0.25f,
        targetValue = 0.55f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "aura"
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(265.dp),
        contentAlignment = Alignment.Center
    ) {
        // Glowing halo behind avatar tinted by outfit accent
        Box(
            modifier = Modifier
                .size(240.dp)
                .scale(scale * 1.08f)
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            equippedOutfit.accentColor.copy(alpha = auraAlpha),
                            KokoroPink.copy(alpha = auraAlpha * 0.4f),
                            Color.Transparent
                        )
                    ),
                    shape = CircleShape
                )
        )

        // Floating sparkles and hearts
        Text(
            text = "✨",
            fontSize = 18.sp,
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(start = 50.dp, top = 20.dp)
                .offset(y = (floatOffset * 1.2f).dp)
        )
        Text(
            text = "💖",
            fontSize = 16.sp,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(end = 50.dp, top = 30.dp)
                .offset(y = (-floatOffset * 1.5f).dp)
        )
        Text(
            text = "🌸",
            fontSize = 16.sp,
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(start = 35.dp)
                .offset(y = (floatOffset * 0.8f).dp)
        )

        // Main Avatar Card
        Box(
            modifier = Modifier
                .size(210.dp)
                .scale(scale)
                .offset(y = floatOffset.dp)
                .shadow(
                    elevation = 16.dp,
                    shape = CircleShape,
                    ambientColor = equippedOutfit.accentColor.copy(alpha = 0.35f),
                    spotColor = KokoroPink.copy(alpha = 0.4f)
                )
                .border(
                    width = 3.dp,
                    brush = Brush.linearGradient(
                        colors = listOf(
                            Color.White,
                            equippedOutfit.accentColor.copy(alpha = 0.9f),
                            KokoroPink.copy(alpha = 0.8f)
                        )
                    ),
                    shape = CircleShape
                )
                .clip(CircleShape)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = onAvatarClick
                )
        ) {
            Image(
                painter = painterResource(id = equippedOutfit.fullBodyImageRes ?: R.drawable.kokoro_avatar),
                contentDescription = "Kokoro Waifu Avatar",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            // Accessory badge
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(8.dp)
                    .shadow(4.dp, CircleShape)
                    .background(Color.White.copy(alpha = 0.92f), CircleShape)
                    .padding(5.dp)
            ) {
                Text(text = equippedOutfit.emoji, fontSize = 16.sp)
            }
        }

        // Equipped outfit indicator pill (clickable to open full body view)
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .offset(y = (-4).dp)
                .shadow(6.dp, RoundedCornerShape(20.dp))
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(Color.White, Color(0xFFFAF5FF))
                    ),
                    shape = RoundedCornerShape(20.dp)
                )
                .border(
                    width = 1.2.dp,
                    brush = Brush.horizontalGradient(
                        listOf(equippedOutfit.accentColor, KokoroPink.copy(alpha = 0.7f))
                    ),
                    shape = RoundedCornerShape(20.dp)
                )
                .clickable(onClick = onAvatarClick)
                .padding(horizontal = 14.dp, vertical = 6.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = equippedOutfit.emoji, fontSize = 13.sp)
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = equippedOutfit.name,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = equippedOutfit.accentColor
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "• Ver cuerpo 👗",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Medium,
                    color = KokoroViolet
                )
            }
        }
    }
}
