package com.example.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.VolumeMute
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.MusicOff
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.Language
import com.example.model.LearningMode
import com.example.ui.theme.KangarooGold
import com.example.ui.theme.PlayfulMintGreen
import com.example.ui.theme.PlayfulSkyBlue
import com.example.ui.theme.PlayfulYellow
import com.example.util.NumberFormatter

@Composable
fun TopHeaderBar(
    selectedMode: LearningMode,
    onSelectMode: (LearningMode) -> Unit,
    currentLanguage: Language,
    onToggleLanguage: () -> Unit,
    isSoundEnabled: Boolean,
    onToggleSound: () -> Unit,
    isMusicEnabled: Boolean,
    onToggleMusic: () -> Unit,
    starsCollected: Int,
    onOpenInfo: () -> Unit,
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "mascotWiggle")
    val hopOffset by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = -4f,
        animationSpec = infiniteRepeatable(
            animation = tween(800, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "hop"
    )
    val musicPulseScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.15f,
        animationSpec = infiniteRepeatable(
            animation = tween(600, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "musicPulse"
    )

    Surface(
        modifier = modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.surface,
        shadowElevation = 4.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp, vertical = 6.dp)
        ) {
            // Top Row: Brand mascot badge, stars, language toggle, music toggle, sound toggle, info
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Mascot Brand Title with hopping animation
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clickable { onOpenInfo() }
                ) {
                    Box(
                        modifier = Modifier
                            .offset(y = hopOffset.dp)
                            .size(38.dp)
                            .clip(CircleShape)
                            .background(KangarooGold)
                            .border(1.5.dp, Color.White, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "🦘", fontSize = 21.sp)
                    }
                    Spacer(modifier = Modifier.width(6.dp))
                    Column {
                        Text(
                            text = "Numberoo",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Black,
                                color = KangarooGold,
                                fontSize = 18.sp
                            )
                        )
                        Text(
                            text = if (currentLanguage == Language.BANGLA) "মজার সংখ্যা" else "Numbers & Count",
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                fontSize = 10.sp
                            )
                        )
                    }
                }

                // Action Controls: Stars, Language, Music, Sound, Info
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    // Star counter pill
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(18.dp))
                            .background(PlayfulYellow.copy(alpha = 0.25f))
                            .border(1.5.dp, PlayfulYellow, RoundedCornerShape(18.dp))
                            .padding(horizontal = 7.dp, vertical = 4.dp)
                            .testTag("stars_badge"),
                        contentAlignment = Alignment.Center
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(text = "⭐", fontSize = 13.sp)
                            Spacer(modifier = Modifier.width(2.dp))
                            Text(
                                text = NumberFormatter.formatNumber(starsCollected, currentLanguage),
                                fontWeight = FontWeight.Black,
                                color = Color(0xFFE65100),
                                fontSize = 13.sp
                            )
                        }
                    }

                    // Language toggle pill button (touch target >= 48dp)
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(18.dp))
                            .background(PlayfulSkyBlue.copy(alpha = 0.15f))
                            .border(1.5.dp, PlayfulSkyBlue, RoundedCornerShape(18.dp))
                            .clickable(onClick = onToggleLanguage)
                            .padding(horizontal = 7.dp, vertical = 4.dp)
                            .testTag("language_toggle"),
                        contentAlignment = Alignment.Center
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(text = currentLanguage.flag, fontSize = 12.sp)
                            Spacer(modifier = Modifier.width(2.dp))
                            Text(
                                text = if (currentLanguage == Language.ENGLISH) "EN" else "বাং",
                                fontWeight = FontWeight.Bold,
                                color = PlayfulSkyBlue,
                                fontSize = 11.sp
                            )
                        }
                    }

                    // Background Music Toggle Button
                    IconButton(
                        onClick = onToggleMusic,
                        modifier = Modifier
                            .size(36.dp)
                            .testTag("music_toggle")
                    ) {
                        Icon(
                            imageVector = if (isMusicEnabled) Icons.Default.MusicNote else Icons.Default.MusicOff,
                            contentDescription = if (isMusicEnabled) "Pause background music" else "Play background music",
                            tint = if (isMusicEnabled) PlayfulMintGreen else Color.Gray,
                            modifier = Modifier
                                .size(22.dp)
                                .scale(if (isMusicEnabled) musicPulseScale else 1f)
                        )
                    }

                    // Sound Effects Toggle Icon
                    IconButton(
                        onClick = onToggleSound,
                        modifier = Modifier
                            .size(36.dp)
                            .testTag("sound_toggle")
                    ) {
                        Icon(
                            imageVector = if (isSoundEnabled) Icons.AutoMirrored.Filled.VolumeUp else Icons.AutoMirrored.Filled.VolumeMute,
                            contentDescription = if (isSoundEnabled) "Mute sound effects" else "Enable sound effects",
                            tint = if (isSoundEnabled) KangarooGold else Color.Gray,
                            modifier = Modifier.size(22.dp)
                        )
                    }

                    // Parent / About Info Icon
                    IconButton(
                        onClick = onOpenInfo,
                        modifier = Modifier
                            .size(36.dp)
                            .testTag("info_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = "About and Parents Info",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.size(19.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            // Mode Selector Pill Tabs (3 Toddler-friendly modes)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                LearningMode.values().forEach { mode ->
                    val isSelected = mode == selectedMode
                    val bgColor by animateColorAsState(
                        targetValue = if (isSelected) KangarooGold else MaterialTheme.colorScheme.surfaceVariant,
                        animationSpec = spring(stiffness = Spring.StiffnessMediumLow),
                        label = "modeBg"
                    )
                    val contentColor = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(44.dp)
                            .clip(RoundedCornerShape(18.dp))
                            .background(bgColor)
                            .clickable { onSelectMode(mode) }
                            .padding(horizontal = 4.dp)
                            .testTag("mode_${mode.name.lowercase()}"),
                        contentAlignment = Alignment.Center
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(text = mode.iconEmoji, fontSize = 15.sp)
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = if (currentLanguage == Language.BANGLA) mode.banglaTitle else mode.englishTitle,
                                fontWeight = if (isSelected) FontWeight.Black else FontWeight.Bold,
                                color = contentColor,
                                fontSize = 12.sp,
                                maxLines = 1
                            )
                        }
                    }
                }
            }
        }
    }
}
