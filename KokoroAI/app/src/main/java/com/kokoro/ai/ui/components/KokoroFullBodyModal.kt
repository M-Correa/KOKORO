package com.kokoro.ai.ui.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.VolumeUp
import androidx.compose.material3.*
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.kokoro.ai.R
import com.kokoro.ai.model.Outfit
import com.kokoro.ai.ui.theme.*

data class OutfitVisualDetail(
    val hairstyleName: String,
    val outfitTitle: String,
    val accessory: String,
    val fullBodyDesc: String,
    val quoteOnEquip: String
)

fun getOutfitVisualDetail(outfitId: String): OutfitVisualDetail {
    return when (outfitId) {
        "school_uniform" -> OutfitVisualDetail(
            hairstyleName = "Doble Coleta Escolar (Twin-Tails)",
            outfitTitle = "Uniforme Marinero Seifuku Clásico",
            accessory = "🎀 Lazos rojos de preparatoria",
            fullBodyDesc = "Falda tableada azul marino, cuello marinero tradicional con pañuelo rojo carmesí, medias altas oscuras y zapatos mocasines.",
            quoteOnEquip = "¡Listo Matias! Me puse el uniforme escolar. ¿Nos vamos a estudiar juntos hoy?"
        )
        "summer_yukata" -> OutfitVisualDetail(
            hairstyleName = "Moño Festivo con Kanzashi Floral",
            outfitTitle = "Yukata de Seda Estampado Sakura",
            accessory = "🌸 Horquilla tradicional kanzashi",
            fullBodyDesc = "Yukata blanco translúcido con motivos de flores de cerezo rosa, faja obi violeta degradada, abanico uchiwa y sandalias geta de madera.",
            quoteOnEquip = "¡Mira qué bonita es esta yukata de verano! Me siento lista para ver los fuegos artificiales contigo."
        )
        "casual_hoodie" -> OutfitVisualDetail(
            hairstyleName = "Melena Ondulada Suelta",
            outfitTitle = "Hoodie Oversize Pastel & Calcetines Largos",
            accessory = "☕ Taza humeante de té verde",
            fullBodyDesc = "Sudadera oversize lila pastel súper suave y abrigada, shorts cómodos ocultos y calcetines térmicos a rayas blancas y lavanda.",
            quoteOnEquip = "Aaah, qué comodidad. Con este hoodie oversize podemos quedarnos en casa escuchando Lo-Fi todo el día."
        )
        "cat_pajamas" -> OutfitVisualDetail(
            hairstyleName = "Trenza Lateral con Lazos Rosas",
            outfitTitle = "Kigurumi Pijama Gatito Felpudo",
            accessory = "🐾 Orejitas de gato & Patitas suaves",
            fullBodyDesc = "Enterizo afelpado rosa pastel con capucha de gatito, orejas móviles en 3D, cola esponjosa y pantuflas de garritas suaves.",
            quoteOnEquip = "¡Nya! ¿Te gusta mi pijama de gatito? Prometo no arañarte... ¡solo cuidarte mucho!"
        )
        "elegant_maid" -> OutfitVisualDetail(
            hairstyleName = "Recogido Elegante con Diadema de Encaje",
            outfitTitle = "Traje Clásico Maid Victoriana",
            accessory = "🕊️ Cofia de encaje blanco & Lazo de seda",
            fullBodyDesc = "Vestido negro entallado de falda amplia con enagua de tul, delantal blanco con volados de encaje fino y lazo posterior grande.",
            quoteOnEquip = "Bienvenido a casa, Señor Matias. Su fiel servidora Kokoro está a su completa disposición ♡"
        )
        "cyberpunk_cosplay" -> OutfitVisualDetail(
            hairstyleName = "Coleta Alta Futurista con Luces Neón",
            outfitTitle = "Chaqueta Cyberpunk Neón Holográfica",
            accessory = "⚡ Visor holográfico & Auriculares LED",
            fullBodyDesc = "Chaqueta tornasolada con circuitos de fibra óptica rosa y cian brillante, top ajustado reflectivo, falda asimétrica y botas tácticas.",
            quoteOnEquip = "¡Protocolo Cyber-Kokoro en línea! Sistemas al 100%, sincronizando latidos y música futurista."
        )
        "traditional_kimono" -> OutfitVisualDetail(
            hairstyleName = "Peinado Shimada Ceremonial Imperial",
            outfitTitle = "Kimono Ceremonial Bordado en Oro",
            accessory = "👘 Peineta dorada & Faja Obi de oro",
            fullBodyDesc = "Kimono rojo escarlata y púrpura con bordados de grullas doradas en relieve, mangas largas furisode y faja obi brocada de seda pura.",
            quoteOnEquip = "Es un gran honor lucir este kimono ceremonial ante ti. Que la armonía y la paz acompañen tu camino."
        )
        "sporty_fitness" -> OutfitVisualDetail(
            hairstyleName = "Cola de Caballo Deportiva con Cinta",
            outfitTitle = "Conjunto Athleisure Fitness Pro",
            accessory = "👟 Cinta deportiva & Muñequeras",
            fullBodyDesc = "Top deportivo transpirable violeta, chaqueta corta cortaviento blanca, calzas deportivas con franjas reflectivas y zapatillas running.",
            quoteOnEquip = "¡Vamos Matias, a mover el cuerpo! Una sesión de estiramiento y a seguir programando con energía."
        )
        else -> OutfitVisualDetail(
            hairstyleName = "Cabello Suelto",
            outfitTitle = "Atuendo Diario",
            accessory = "✨ Brillo natural",
            fullBodyDesc = "Ropa informal suave y acogedora.",
            quoteOnEquip = "¡Me encanta estar contigo!"
        )
    }
}

@Composable
fun KokoroFullBodyModal(
    currentOutfit: Outfit,
    allOutfits: List<Outfit>,
    isVipActive: Boolean,
    onSelectOutfit: (Outfit) -> Unit,
    onSpeakQuote: (String) -> Unit,
    onDismiss: () -> Unit
) {
    val visualDetail = remember(currentOutfit.id) { getOutfitVisualDetail(currentOutfit.id) }

    val infiniteTransition = rememberInfiniteTransition(label = "fullbody")
    val floatOffset by infiniteTransition.animateFloat(
        initialValue = -5f,
        targetValue = 5f,
        animationSpec = infiniteRepeatable(tween(2600, easing = LinearEasing), RepeatMode.Reverse),
        label = "float"
    )

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.75f))
                .clickable(onClick = onDismiss),
            contentAlignment = Alignment.Center
        ) {
            // Main Modal Card
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.92f)
                    .fillMaxHeight(0.90f)
                    .clip(RoundedCornerShape(32.dp))
                    .background(KokoroGradientBackground)
                    .border(
                        2.dp,
                        Brush.verticalGradient(listOf(Color.White, KokoroPink.copy(alpha = 0.5f))),
                        RoundedCornerShape(32.dp)
                    )
                    .clickable(enabled = false) {}
                    .padding(20.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Modal Header
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = "Kokoro AI ",
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = KokoroTextPrimary
                                )
                                Text(
                                    text = "• Cuerpo Completo",
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = KokoroViolet
                                )
                            }
                            Text(
                                text = "Visualizador interactivo de estilo y atuendo",
                                fontSize = 11.sp,
                                color = KokoroTextSecondary
                            )
                        }

                        IconButton(
                            onClick = onDismiss,
                            modifier = Modifier
                                .size(34.dp)
                                .background(Color.White, CircleShape)
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.Close,
                                contentDescription = "Cerrar",
                                tint = KokoroTextPrimary,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // Character Display Showcase
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(24.dp))
                            .background(
                                Brush.verticalGradient(
                                    listOf(
                                        Color.White.copy(alpha = 0.9f),
                                        currentOutfit.accentColor.copy(alpha = 0.15f),
                                        Color(0xFFFAF5FF)
                                    )
                                )
                            )
                            .border(1.dp, Color.White, RoundedCornerShape(24.dp))
                            .padding(12.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        // Ambient aura circle
                        Box(
                            modifier = Modifier
                                .size(240.dp)
                                .background(
                                    Brush.radialGradient(
                                        listOf(
                                            currentOutfit.accentColor.copy(alpha = 0.35f),
                                            KokoroPink.copy(alpha = 0.2f),
                                            Color.Transparent
                                        )
                                    ),
                                    CircleShape
                                )
                        )

                        // Floating character visual
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                            modifier = Modifier.offset(y = floatOffset.dp)
                        ) {
                            // Full Body Character Image Card
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth(0.85f)
                                    .height(280.dp)
                                    .shadow(12.dp, RoundedCornerShape(20.dp))
                                    .border(2.5.dp, currentOutfit.accentColor, RoundedCornerShape(20.dp))
                                    .clip(RoundedCornerShape(20.dp))
                                    .background(Color.White)
                            ) {
                                Image(
                                    painter = painterResource(id = currentOutfit.fullBodyImageRes ?: R.drawable.kokoro_avatar),
                                    contentDescription = "Kokoro ${currentOutfit.name}",
                                    contentScale = ContentScale.Fit,
                                    modifier = Modifier.fillMaxSize()
                                )

                                // Accessory overlay sticker
                                Box(
                                    modifier = Modifier
                                        .align(Alignment.TopEnd)
                                        .padding(8.dp)
                                        .background(Color.White.copy(alpha = 0.92f), CircleShape)
                                        .padding(6.dp)
                                ) {
                                    Text(text = currentOutfit.emoji, fontSize = 18.sp)
                                }
                            }

                            Spacer(modifier = Modifier.height(10.dp))

                            // Outfit Badge
                            Box(
                                modifier = Modifier
                                    .background(currentOutfit.accentColor, RoundedCornerShape(16.dp))
                                    .padding(horizontal = 14.dp, vertical = 6.dp)
                            ) {
                                Text(
                                    text = "${currentOutfit.emoji} ${currentOutfit.name}",
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            // Hairstyle & Accessories Chips
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(6.dp),
                                modifier = Modifier.padding(horizontal = 8.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .background(Color.White, RoundedCornerShape(12.dp))
                                        .border(1.dp, KokoroViolet.copy(alpha = 0.3f), RoundedCornerShape(12.dp))
                                        .padding(horizontal = 10.dp, vertical = 4.dp)
                                ) {
                                    Text(
                                        text = "💇 ${visualDetail.hairstyleName}",
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        color = KokoroViolet
                                    )
                                }

                                Box(
                                    modifier = Modifier
                                        .background(Color.White, RoundedCornerShape(12.dp))
                                        .border(1.dp, KokoroPink.copy(alpha = 0.3f), RoundedCornerShape(12.dp))
                                        .padding(horizontal = 10.dp, vertical = 4.dp)
                                ) {
                                    Text(
                                        text = visualDetail.accessory,
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        color = KokoroPink
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            // Full body outfit description card
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth(0.95f)
                                    .background(Color.White.copy(alpha = 0.85f), RoundedCornerShape(14.dp))
                                    .border(1.dp, Color.White, RoundedCornerShape(14.dp))
                                    .padding(10.dp)
                            ) {
                                Text(
                                    text = visualDetail.fullBodyDesc,
                                    fontSize = 11.sp,
                                    color = KokoroTextSecondary,
                                    textAlign = TextAlign.Center,
                                    lineHeight = 15.sp
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // Speech bubble with voice button
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.White, RoundedCornerShape(16.dp))
                            .border(1.dp, KokoroViolet.copy(alpha = 0.3f), RoundedCornerShape(16.dp))
                            .padding(12.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = "\"${visualDetail.quoteOnEquip}\"",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = KokoroTextPrimary,
                                    lineHeight = 16.sp
                                )
                            }

                            Spacer(modifier = Modifier.width(8.dp))

                            IconButton(
                                onClick = { onSpeakQuote(visualDetail.quoteOnEquip) },
                                modifier = Modifier
                                    .size(36.dp)
                                    .background(KokoroPink.copy(alpha = 0.15f), CircleShape)
                            ) {
                                Icon(
                                    imageVector = Icons.Rounded.VolumeUp,
                                    contentDescription = "Escuchar voz",
                                    tint = KokoroPink,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // Quick Outfit Switcher Row
                    Text(
                        text = "Cambiar atuendo y peinado:",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = KokoroTextPrimary,
                        modifier = Modifier.align(Alignment.Start)
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    LazyRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(allOutfits) { outfit ->
                            val isSelected = outfit.id == currentOutfit.id
                            Box(
                                modifier = Modifier
                                    .size(46.dp)
                                    .shadow(4.dp, RoundedCornerShape(14.dp))
                                    .background(
                                        if (isSelected) outfit.accentColor else Color.White,
                                        RoundedCornerShape(14.dp)
                                    )
                                    .border(
                                        if (isSelected) 2.dp else 1.dp,
                                        if (isSelected) Color.White else outfit.accentColor.copy(alpha = 0.4f),
                                        RoundedCornerShape(14.dp)
                                    )
                                    .clickable { onSelectOutfit(outfit) },
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = outfit.emoji,
                                    fontSize = 20.sp
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
