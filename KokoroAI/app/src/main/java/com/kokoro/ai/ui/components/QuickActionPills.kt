package com.kokoro.ai.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kokoro.ai.ui.theme.KokoroPink
import com.kokoro.ai.ui.theme.KokoroTextPrimary
import com.kokoro.ai.ui.theme.KokoroViolet

@Composable
fun QuickActionPills(
    isStudyModeActive: Boolean,
    onOrderFoodClick: () -> Unit,
    onStudyModeClick: () -> Unit,
    onWardrobeClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        // Order Food Pill
        ActionPill(
            emoji = "🍜",
            label = "Order Food",
            isActive = false,
            accentColor = KokoroPink,
            onClick = onOrderFoodClick,
            modifier = Modifier.weight(1f)
        )

        // Study Mode Pill
        ActionPill(
            emoji = "📖",
            label = if (isStudyModeActive) "Studying..." else "Study Mode",
            isActive = isStudyModeActive,
            accentColor = KokoroViolet,
            onClick = onStudyModeClick,
            modifier = Modifier.weight(1f)
        )

        // Wardrobe Pill
        ActionPill(
            emoji = "👗",
            label = "Wardrobe",
            isActive = false,
            accentColor = Color(0xFFA855F7),
            onClick = onWardrobeClick,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun ActionPill(
    emoji: String,
    label: String,
    isActive: Boolean,
    accentColor: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val bgColor by animateColorAsState(
        targetValue = if (isActive) accentColor.copy(alpha = 0.15f) else Color.White.copy(alpha = 0.85f),
        label = "pillBg"
    )
    val borderColor by animateColorAsState(
        targetValue = if (isActive) accentColor else Color.White,
        label = "pillBorder"
    )

    Box(
        modifier = modifier
            .height(46.dp)
            .shadow(4.dp, RoundedCornerShape(24.dp))
            .background(bgColor, RoundedCornerShape(24.dp))
            .border(1.2.dp, borderColor, RoundedCornerShape(24.dp))
            .clickable(onClick = onClick)
            .padding(horizontal = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(text = emoji, fontSize = 14.sp)
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = label,
                fontSize = 11.sp,
                fontWeight = if (isActive) FontWeight.Bold else FontWeight.Medium,
                color = if (isActive) accentColor else KokoroTextPrimary,
                maxLines = 1
            )
        }
    }
}
