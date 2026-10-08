package com.example.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.VolumeMute
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.Info
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.Language
import com.example.model.LearningMode
import com.example.ui.theme.KangarooGold
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
    starsCollected: Int,
    onOpenInfo: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.surface,
        shadowElevation = 4.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 8.dp)
        ) {
            // Top Row: Brand mascot badge, stars, language toggle, sound toggle, info
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Numberoo Brand Title
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clickable { onOpenInfo() }
                ) {
                    Box(
                        modifier = Modifier
                            .size(38.dp)
                            .clip(CircleShape)
                            .background(KangarooGold),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "🦘", fontSize = 22.sp)
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(
                            text = "Numberoo",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.ExtraBold,
                                color = KangarooGold,
                                fontSize = 19.sp
                            )
                        )
                        Text(
                            text = if (currentLanguage == Language.BANGLA) "মজার গণিত" else "Fun Math for Kids",
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                fontSize = 11.sp
                            )
                        )
                    }
                }

                // Action Controls: Stars, Language, Sound, Info
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    // Star counter pill
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .background(PlayfulYellow.copy(alpha = 0.25f))
                            .border(1.5.dp, PlayfulYellow, RoundedCornerShape(20.dp))
                            .padding(horizontal = 8.dp, vertical = 5.dp)
                            .testTag("stars_badge"),
                        contentAlignment = Alignment.Center
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = "Stars collected",
                                tint = Color(0xFFF57F17),
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(3.dp))
                            Text(
                                text = NumberFormatter.formatNumber(starsCollected, currentLanguage),
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFFE65100),
                                fontSize = 14.sp
                            )
                        }
                    }

                    // Language toggle pill button
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .background(PlayfulSkyBlue.copy(alpha = 0.15f))
                            .border(1.5.dp, PlayfulSkyBlue, RoundedCornerShape(20.dp))
                            .clickable(onClick = onToggleLanguage)
                            .padding(horizontal = 8.dp, vertical = 5.dp)
                            .testTag("language_toggle"),
                        contentAlignment = Alignment.Center
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(text = currentLanguage.flag, fontSize = 13.sp)
                            Spacer(modifier = Modifier.width(3.dp))
                            Text(
                                text = if (currentLanguage == Language.ENGLISH) "EN" else "বাংলা",
                                fontWeight = FontWeight.Bold,
                                color = PlayfulSkyBlue,
                                fontSize = 12.sp
                            )
                        }
                    }

                    // Sound Toggle Icon
                    IconButton(
                        onClick = onToggleSound,
                        modifier = Modifier
                            .size(36.dp)
                            .testTag("sound_toggle")
                    ) {
                        Icon(
                            imageVector = if (isSoundEnabled) Icons.AutoMirrored.Filled.VolumeUp else Icons.AutoMirrored.Filled.VolumeMute,
                            contentDescription = if (isSoundEnabled) "Mute sound" else "Enable sound",
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
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Mode Selector Pill Tabs
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
                            .clip(RoundedCornerShape(16.dp))
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
                            Text(text = mode.iconEmoji, fontSize = 14.sp)
                            Spacer(modifier = Modifier.width(3.dp))
                            Text(
                                text = if (currentLanguage == Language.BANGLA) mode.banglaTitle else mode.englishTitle,
                                fontWeight = if (isSelected) FontWeight.ExtraBold else FontWeight.Medium,
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
