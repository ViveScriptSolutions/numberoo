package com.example.ui.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.CountingThemes
import com.example.model.Language
import com.example.ui.components.NumberooMascotCard
import com.example.ui.theme.KangarooGold
import com.example.ui.theme.PlayfulMintGreen
import com.example.ui.theme.PlayfulSkyBlue
import com.example.ui.theme.PlayfulYellow
import com.example.util.NumberFormatter
import com.example.util.SoundHelper
import kotlinx.coroutines.launch

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun LearnNumbersScreen(
    currentLanguage: Language,
    soundHelper: SoundHelper,
    modifier: Modifier = Modifier
) {
    var selectedNumber by remember { mutableIntStateOf(1) }
    val itemTheme = remember(selectedNumber) { CountingThemes.items[selectedNumber % CountingThemes.items.size] }
    val tappedItems = remember(selectedNumber) { mutableStateMapOf<Int, Boolean>() }
    val haptic = LocalHapticFeedback.current
    val scope = rememberCoroutineScope()

    val mascotHint = when (currentLanguage) {
        Language.BANGLA -> "সংখ্যাটি চাপো ও শুনো! গোল জিনিসগুলো স্পর্শ করে গুনতে পারো।"
        Language.ENGLISH -> "Tap the speaker to hear the number, or tap the items to count along!"
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        NumberooMascotCard(
            speechText = mascotHint,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        // Main Jumbo Number Hero Card
        Card(
            shape = RoundedCornerShape(28.dp),
            colors = CardDefaults.cardColors(containerColor = itemTheme.backgroundColor),
            border = androidx.compose.foundation.BorderStroke(2.dp, itemTheme.primaryColor.copy(alpha = 0.5f)),
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
            modifier = Modifier
                .fillMaxWidth()
                .testTag("learn_hero_card")
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Top controls: Prev, Number, Next
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    IconButton(
                        onClick = {
                            if (selectedNumber > 0) {
                                selectedNumber--
                                soundHelper.playPop()
                            }
                        },
                        enabled = selectedNumber > 0,
                        modifier = Modifier
                            .size(50.dp)
                            .clip(CircleShape)
                            .background(Color.White)
                            .testTag("learn_prev_button")
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Previous number",
                            tint = if (selectedNumber > 0) itemTheme.primaryColor else Color.LightGray
                        )
                    }

                    // Jumbo Number Display
                    Box(
                        modifier = Modifier
                            .size(110.dp)
                            .clip(CircleShape)
                            .background(Color.White)
                            .border(3.dp, itemTheme.primaryColor, CircleShape)
                            .clickable {
                                soundHelper.playSuccess()
                                val word = NumberFormatter.getWord(selectedNumber, currentLanguage)
                                soundHelper.speak(word, currentLanguage)
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = NumberFormatter.formatNumber(selectedNumber, currentLanguage),
                            fontSize = 62.sp,
                            fontWeight = FontWeight.Black,
                            color = itemTheme.primaryColor
                        )
                    }

                    IconButton(
                        onClick = {
                            if (selectedNumber < 20) {
                                selectedNumber++
                                soundHelper.playPop()
                            }
                        },
                        enabled = selectedNumber < 20,
                        modifier = Modifier
                            .size(50.dp)
                            .clip(CircleShape)
                            .background(Color.White)
                            .testTag("learn_next_button")
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = "Next number",
                            tint = if (selectedNumber < 20) itemTheme.primaryColor else Color.LightGray
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Number Word in English & Bangla + Speaker Button
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .background(Color.White)
                        .border(1.5.dp, itemTheme.primaryColor.copy(alpha = 0.4f), RoundedCornerShape(20.dp))
                        .clickable {
                            val word = NumberFormatter.getWord(selectedNumber, currentLanguage)
                            soundHelper.speak(word, currentLanguage)
                        }
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    Text(
                        text = NumberFormatter.getWord(selectedNumber, currentLanguage),
                        fontSize = 22.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = itemTheme.primaryColor
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.VolumeUp,
                        contentDescription = "Hear pronunciation",
                        tint = itemTheme.primaryColor,
                        modifier = Modifier.size(24.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Visual Representation of Countable Objects
                if (selectedNumber == 0) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(Color.White.copy(alpha = 0.7f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = if (currentLanguage == Language.BANGLA) "শূন্য মানে কিছু নেই (Empty!)" else "Zero means empty!",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.Gray
                        )
                    }
                } else {
                    FlowRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(18.dp))
                            .background(Color.White.copy(alpha = 0.6f))
                            .padding(12.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalArrangement = Arrangement.Center
                    ) {
                        for (i in 1..selectedNumber) {
                            val isTapped = tappedItems[i] == true
                            val scale = remember { Animatable(1f) }

                            Box(
                                modifier = Modifier
                                    .padding(6.dp)
                                    .size(46.dp)
                                    .scale(scale.value)
                                    .clip(CircleShape)
                                    .background(if (isTapped) itemTheme.primaryColor.copy(alpha = 0.2f) else Color.White)
                                    .border(
                                        width = if (isTapped) 2.dp else 1.dp,
                                        color = if (isTapped) itemTheme.primaryColor else Color(0xFFE0E0E0),
                                        shape = CircleShape
                                    )
                                    .clickable {
                                        haptic.performHapticFeedback(androidx.compose.ui.hapticfeedback.HapticFeedbackType.TextHandleMove)
                                        tappedItems[i] = true
                                        soundHelper.playPop()
                                        val countWord = NumberFormatter.getWord(i, currentLanguage)
                                        soundHelper.speak(countWord, currentLanguage)
                                        scope.launch {
                                            scale.animateTo(1.3f, spring(stiffness = Spring.StiffnessHigh))
                                            scale.animateTo(1.0f, spring(stiffness = Spring.StiffnessMedium))
                                        }
                                    },
                                contentAlignment = Alignment.Center
                            ) {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Text(text = itemTheme.emoji, fontSize = 20.sp)
                                    Text(
                                        text = NumberFormatter.formatNumber(i, currentLanguage),
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = itemTheme.primaryColor
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Quick Horizontal Number Strip (0 to 20)
        Text(
            text = if (currentLanguage == Language.BANGLA) "যে কোনো সংখ্যা বেছে নাও:" else "Pick any number:",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier
                .align(Alignment.Start)
                .padding(horizontal = 4.dp, vertical = 4.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            for (num in 0..20) {
                val isSelected = num == selectedNumber
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(RoundedCornerShape(14.dp))
                        .background(if (isSelected) KangarooGold else Color.White)
                        .border(
                            2.dp,
                            if (isSelected) KangarooGold else Color(0xFFE0E0E0),
                            RoundedCornerShape(14.dp)
                        )
                        .clickable {
                            selectedNumber = num
                            soundHelper.playPop()
                        }
                        .testTag("learn_strip_$num"),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = NumberFormatter.formatNumber(num, currentLanguage),
                        fontSize = 18.sp,
                        fontWeight = if (isSelected) FontWeight.Black else FontWeight.Bold,
                        color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }
    }
}
