package com.kokoro.ai.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Pause
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material.icons.rounded.SkipNext
import androidx.compose.material.icons.rounded.SkipPrevious
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kokoro.ai.R
import com.kokoro.ai.ui.theme.KokoroPink
import com.kokoro.ai.ui.theme.KokoroTextHint
import com.kokoro.ai.ui.theme.KokoroTextPrimary
import com.kokoro.ai.ui.theme.KokoroViolet
import com.kokoro.ai.ui.theme.SpotifyGreen

@Composable
fun SpotifyWidget(
    isPlaying: Boolean,
    progress: Float,
    onPlayPauseToggle: () -> Unit,
    onProgressChange: (Float) -> Unit,
    onNextTrack: () -> Unit,
    onPrevTrack: () -> Unit,
    modifier: Modifier = Modifier
) {
    GlassCard(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(26.dp),
        backgroundColor = Color(0xF2FFFFFF),
        borderColor = Color(0xCCFFFFFF),
        elevation = 10.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Album Art with rounded corners
                Box(
                    modifier = Modifier
                        .size(62.dp)
                        .shadow(6.dp, RoundedCornerShape(16.dp))
                        .clip(RoundedCornerShape(16.dp))
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.kokoro_lofi_cover),
                        contentDescription = "Kokoro Lo-Fi Album Cover",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }

                Spacer(modifier = Modifier.width(14.dp))

                // Title & Subtitle + Spotify indicator
                Column(modifier = Modifier.weight(1f)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(14.dp)
                                .background(SpotifyGreen, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "♫",
                                color = Color.White,
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Spotify",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Medium,
                            color = KokoroTextHint
                        )
                    }

                    Spacer(modifier = Modifier.height(2.dp))

                    Text(
                        text = "Lo-Fi Beats",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = KokoroTextPrimary
                    )

                    Text(
                        text = "Kokoro AI Playlist",
                        fontSize = 12.sp,
                        color = KokoroPink,
                        fontWeight = FontWeight.Medium
                    )
                }

                // Audio waveform equalizer animation
                SoundWaveVisualizer(isPlaying = isPlaying)
            }

            Spacer(modifier = Modifier.height(6.dp))

            // Progress Slider
            Slider(
                value = progress,
                onValueChange = onProgressChange,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(24.dp),
                colors = SliderDefaults.colors(
                    thumbColor = KokoroViolet,
                    activeTrackColor = KokoroViolet,
                    inactiveTrackColor = KokoroViolet.copy(alpha = 0.2f)
                )
            )

            // Playback controls row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onPrevTrack) {
                    Icon(
                        imageVector = Icons.Rounded.SkipPrevious,
                        contentDescription = "Previous Track",
                        tint = KokoroTextPrimary,
                        modifier = Modifier.size(26.dp)
                    )
                }

                // Play / Pause round button with gradient
                Box(
                    modifier = Modifier
                        .size(46.dp)
                        .shadow(8.dp, CircleShape)
                        .background(
                            brush = Brush.horizontalGradient(
                                listOf(KokoroViolet, KokoroPink)
                            ),
                            shape = CircleShape
                        )
                        .clickable(onClick = onPlayPauseToggle),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = if (isPlaying) Icons.Rounded.Pause else Icons.Rounded.PlayArrow,
                        contentDescription = if (isPlaying) "Pause" else "Play",
                        tint = Color.White,
                        modifier = Modifier.size(28.dp)
                    )
                }

                IconButton(onClick = onNextTrack) {
                    Icon(
                        imageVector = Icons.Rounded.SkipNext,
                        contentDescription = "Next Track",
                        tint = KokoroTextPrimary,
                        modifier = Modifier.size(26.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun SoundWaveVisualizer(isPlaying: Boolean) {
    val infiniteTransition = rememberInfiniteTransition(label = "waveform")

    val h1 by infiniteTransition.animateFloat(
        initialValue = 6f, targetValue = 22f,
        animationSpec = infiniteRepeatable(tween(450, easing = LinearEasing), RepeatMode.Reverse),
        label = "h1"
    )
    val h2 by infiniteTransition.animateFloat(
        initialValue = 18f, targetValue = 8f,
        animationSpec = infiniteRepeatable(tween(550, easing = LinearEasing), RepeatMode.Reverse),
        label = "h2"
    )
    val h3 by infiniteTransition.animateFloat(
        initialValue = 10f, targetValue = 24f,
        animationSpec = infiniteRepeatable(tween(380, easing = LinearEasing), RepeatMode.Reverse),
        label = "h3"
    )
    val h4 by infiniteTransition.animateFloat(
        initialValue = 20f, targetValue = 6f,
        animationSpec = infiniteRepeatable(tween(500, easing = LinearEasing), RepeatMode.Reverse),
        label = "h4"
    )

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(3.dp),
        modifier = Modifier.height(28.dp)
    ) {
        val bars = listOf(h1, h2, h3, h4)
        bars.forEach { h ->
            Box(
                modifier = Modifier
                    .width(3.5.dp)
                    .height(if (isPlaying) h.dp else 6.dp)
                    .background(
                        brush = Brush.verticalGradient(
                            listOf(KokoroPink, KokoroViolet)
                        ),
                        shape = RoundedCornerShape(2.dp)
                    )
            )
        }
    }
}
