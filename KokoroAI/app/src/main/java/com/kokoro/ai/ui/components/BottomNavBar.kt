package com.kokoro.ai.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Checkroom
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Tune
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kokoro.ai.ui.navigation.Screen
import com.kokoro.ai.ui.theme.KokoroPink
import com.kokoro.ai.ui.theme.KokoroTextHint
import com.kokoro.ai.ui.theme.KokoroTextPrimary
import com.kokoro.ai.ui.theme.KokoroViolet

sealed class NavItem(val route: String, val title: String, val icon: ImageVector) {
    object Home : NavItem(Screen.Home.route, "Inicio", Icons.Rounded.Home)
    object Wardrobe : NavItem(Screen.Wardrobe.route, "Guardarropa", Icons.Rounded.Checkroom)
    object Personality : NavItem(Screen.Personality.route, "Personalidad", Icons.Rounded.Tune)
}

@Composable
fun KokoroBottomNavBar(
    currentRoute: String,
    onNavigateToRoute: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val items = listOf(NavItem.Home, NavItem.Wardrobe, NavItem.Personality)

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 12.dp),
        contentAlignment = Alignment.Center
    ) {
        // Floating glass bar container
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(68.dp)
                .shadow(
                    elevation = 16.dp,
                    shape = RoundedCornerShape(32.dp),
                    ambientColor = KokoroViolet.copy(alpha = 0.25f),
                    spotColor = KokoroPink.copy(alpha = 0.2f)
                )
                .background(
                    color = Color.White.copy(alpha = 0.92f),
                    shape = RoundedCornerShape(32.dp)
                )
                .border(
                    width = 1.2.dp,
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            Color.White,
                            KokoroViolet.copy(alpha = 0.4f),
                            KokoroPink.copy(alpha = 0.4f),
                            Color.White
                        )
                    ),
                    shape = RoundedCornerShape(32.dp)
                )
                .padding(horizontal = 12.dp),
            contentAlignment = Alignment.Center
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                items.forEach { item ->
                    val isSelected = currentRoute == item.route

                    val iconTint by animateColorAsState(
                        targetValue = if (isSelected) KokoroViolet else KokoroTextHint,
                        label = "navIconTint"
                    )

                    val pillBg by animateColorAsState(
                        targetValue = if (isSelected) KokoroViolet.copy(alpha = 0.12f) else Color.Transparent,
                        label = "navPillBg"
                    )

                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .background(pillBg)
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null,
                                onClick = { onNavigateToRoute(item.route) }
                            )
                            .padding(horizontal = 14.dp, vertical = 8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = item.title,
                                tint = iconTint,
                                modifier = Modifier.size(24.dp)
                            )
                            if (isSelected) {
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = item.title,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = KokoroViolet
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
