package com.example.ui.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.togetherWith
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
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
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
import com.example.util.NumberFormatter
import com.example.util.SoundHelper
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun LearnNumbersScreen(
    currentLanguage: Language,
    soundHelper: SoundHelper,
    modifier: Modifier = Modifier
) {
    var selectedNumber by remember { mutableIntStateOf(1) }
    var isAutoLoopPlaying by remember { mutableStateOf(false) }

    val itemTheme = CountingThemes.getItemForNumber(selectedNumber)
    val tappedItems = remember(selectedNumber) { mutableStateMapOf<Int, Boolean>() }
    val haptic = LocalHapticFeedback.current
    val scope = rememberCoroutineScope()

    // Announce when number changes
    LaunchedEffect(selectedNumber, currentLanguage) {
        val word = NumberFormatter.getWord(selectedNumber, currentLanguage)
        soundHelper.speakNumberWithAnimal(
            number = selectedNumber,
            numberWord = word,
            animalNameSingular = itemTheme.englishNameSingular,
            animalNamePlural = itemTheme.englishNamePlural,
            banglaName = itemTheme.banglaName,
            language = currentLanguage
        )
    }

    // Auto-loop timer for toddlers (cycles 1 to 20)
    LaunchedEffect(isAutoLoopPlaying) {
        while (isAutoLoopPlaying) {
            delay(3200)
            if (isAutoLoopPlaying) {
                selectedNumber = if (selectedNumber >= 20) 1 else selectedNumber + 1
            }
        }
    }

    val mascotHint = when (currentLanguage) {
        Language.BANGLA -> "সংখ্যায় চাপ দাও, শোনো এবং পশুদের গুনো! 🐾"
        Language.ENGLISH -> "Tap any number below to learn! Meet cute animals! 🐾"
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 12.dp, vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // High-contrast Mascot Explanation Card
        NumberooMascotCard(
            speechText = mascotHint,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // Main Jumbo Toddler Card with AnimatedContent for smooth, instant visual updates
        AnimatedContent(
            targetState = selectedNumber,
            transitionSpec = {
                (scaleIn(animationSpec = spring(stiffness = Spring.StiffnessMediumLow)) + fadeIn()) togetherWith
                        (scaleOut(animationSpec = spring(stiffness = Spring.StiffnessMediumLow)) + fadeOut())
            },
            label = "numberCardTransition"
        ) { currentNum ->
            val currentTheme = CountingThemes.getItemForNumber(currentNum)

            Card(
                shape = RoundedCornerShape(28.dp),
                colors = CardDefaults.cardColors(containerColor = currentTheme.backgroundColor),
                border = androidx.compose.foundation.BorderStroke(3.dp, currentTheme.primaryColor),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("learn_hero_card_$currentNum")
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Top Navigation Row: Big Prev, Jumbo Number, Big Next
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        IconButton(
                            onClick = {
                                selectedNumber = if (currentNum <= 1) 20 else currentNum - 1
                                soundHelper.playPop()
                            },
                            modifier = Modifier
                                .size(56.dp)
                                .shadow(3.dp, CircleShape)
                                .clip(CircleShape)
                                .background(Color.White)
                                .border(2.dp, currentTheme.primaryColor, CircleShape)
                                .testTag("learn_prev_button")
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Previous number",
                                tint = currentTheme.primaryColor,
                                modifier = Modifier.size(30.dp)
                            )
                        }

                        // Jumbo Number Badge with High Contrast
                        Box(
                            modifier = Modifier
                                .size(110.dp)
                                .shadow(6.dp, CircleShape)
                                .clip(CircleShape)
                                .background(Color.White)
                                .border(4.dp, currentTheme.primaryColor, CircleShape)
                                .clickable {
                                    soundHelper.playSuccess()
                                    val word = NumberFormatter.getWord(currentNum, currentLanguage)
                                    soundHelper.speakNumberWithAnimal(
                                        number = currentNum,
                                        numberWord = word,
                                        animalNameSingular = currentTheme.englishNameSingular,
                                        animalNamePlural = currentTheme.englishNamePlural,
                                        banglaName = currentTheme.banglaName,
                                        language = currentLanguage
                                    )
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = NumberFormatter.formatNumber(currentNum, currentLanguage),
                                fontSize = 64.sp,
                                fontWeight = FontWeight.Black,
                                color = currentTheme.primaryColor
                            )
                        }

                        IconButton(
                            onClick = {
                                selectedNumber = if (currentNum >= 20) 1 else currentNum + 1
                                soundHelper.playPop()
                            },
                            modifier = Modifier
                                .size(56.dp)
                                .shadow(3.dp, CircleShape)
                                .clip(CircleShape)
                                .background(Color.White)
                                .border(2.dp, currentTheme.primaryColor, CircleShape)
                                .testTag("learn_next_button")
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                contentDescription = "Next number",
                                tint = currentTheme.primaryColor,
                                modifier = Modifier.size(30.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // Number Word & Spoken Animal Label
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .shadow(2.dp, RoundedCornerShape(20.dp))
                            .clip(RoundedCornerShape(20.dp))
                            .background(Color.White)
                            .border(2.dp, currentTheme.primaryColor, RoundedCornerShape(20.dp))
                            .clickable {
                                val word = NumberFormatter.getWord(currentNum, currentLanguage)
                                soundHelper.speakNumberWithAnimal(
                                    number = currentNum,
                                    numberWord = word,
                                    animalNameSingular = currentTheme.englishNameSingular,
                                    animalNamePlural = currentTheme.englishNamePlural,
                                    banglaName = currentTheme.banglaName,
                                    language = currentLanguage
                                )
                            }
                            .padding(horizontal = 14.dp, vertical = 6.dp)
                    ) {
                        Text(
                            text = NumberFormatter.getWord(currentNum, currentLanguage),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Black,
                            color = currentTheme.primaryColor
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = if (currentLanguage == Language.BANGLA) currentTheme.banglaName
                            else if (currentNum == 1) currentTheme.englishNameSingular
                            else currentTheme.englishNamePlural,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color(0xFF212121)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.VolumeUp,
                            contentDescription = "Hear pronunciation",
                            tint = currentTheme.primaryColor,
                            modifier = Modifier.size(22.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // Animated Interactive Animals Grid
                    FlowRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(20.dp))
                            .background(Color.White)
                            .border(1.5.dp, Color(0xFFEEEEEE), RoundedCornerShape(20.dp))
                            .padding(10.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalArrangement = Arrangement.Center
                    ) {
                        for (i in 1..currentNum) {
                            val isTapped = tappedItems[i] == true
                            val scale = remember { Animatable(1f) }

                            Box(
                                modifier = Modifier
                                    .padding(4.dp)
                                    .size(46.dp)
                                    .scale(scale.value)
                                    .clip(CircleShape)
                                    .background(if (isTapped) currentTheme.primaryColor.copy(alpha = 0.25f) else Color(0xFFFAFAFA))
                                    .border(
                                        width = if (isTapped) 2.5.dp else 1.5.dp,
                                        color = if (isTapped) currentTheme.primaryColor else Color(0xFFE0E0E0),
                                        shape = CircleShape
                                    )
                                    .clickable {
                                        haptic.performHapticFeedback(androidx.compose.ui.hapticfeedback.HapticFeedbackType.LongPress)
                                        tappedItems[i] = true
                                        soundHelper.playPop()
                                        val countWord = NumberFormatter.getWord(i, currentLanguage)
                                        soundHelper.speak(countWord, currentLanguage)
                                        scope.launch {
                                            scale.animateTo(1.35f, spring(stiffness = Spring.StiffnessHigh))
                                            scale.animateTo(1.0f, spring(stiffness = Spring.StiffnessMedium))
                                        }
                                    },
                                contentAlignment = Alignment.Center
                            ) {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Text(text = currentTheme.emoji, fontSize = 20.sp)
                                    Text(
                                        text = NumberFormatter.formatNumber(i, currentLanguage),
                                        fontSize = 9.sp,
                                        fontWeight = FontWeight.Black,
                                        color = currentTheme.primaryColor
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // Toddler Auto-Play Loop Button
                    Button(
                        onClick = {
                            isAutoLoopPlaying = !isAutoLoopPlaying
                            soundHelper.playPop()
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (isAutoLoopPlaying) Color(0xFFD32F2F) else KangarooGold
                        ),
                        shape = RoundedCornerShape(18.dp),
                        modifier = Modifier
                            .fillMaxWidth(0.9f)
                            .height(48.dp)
                            .testTag("auto_loop_button")
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = if (isAutoLoopPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                                contentDescription = if (isAutoLoopPlaying) "Pause loop" else "Play loop",
                                tint = Color.White,
                                modifier = Modifier.size(22.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = if (isAutoLoopPlaying) {
                                    if (currentLanguage == Language.BANGLA) "লুপ থামাও ⏸️" else "Pause Loop ⏸️"
                                } else {
                                    if (currentLanguage == Language.BANGLA) "অটো-প্লে শুরু করো ▶️" else "Auto-Play 1–20 ▶️"
                                },
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Quick Pick Numbers Carousel Strip (1 to 20)
        Text(
            text = if (currentLanguage == Language.BANGLA) "সংখ্যা নির্বাচন করো (১–২০):" else "Choose a number (1–20):",
            fontSize = 14.sp,
            fontWeight = FontWeight.Black,
            color = Color(0xFF333333),
            modifier = Modifier
                .align(Alignment.Start)
                .padding(horizontal = 4.dp, vertical = 2.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            for (num in 1..20) {
                val isSelected = num == selectedNumber
                val numTheme = CountingThemes.getItemForNumber(num)

                Box(
                    modifier = Modifier
                        .size(52.dp)
                        .shadow(if (isSelected) 5.dp else 1.dp, RoundedCornerShape(16.dp))
                        .clip(RoundedCornerShape(16.dp))
                        .background(if (isSelected) numTheme.primaryColor else Color.White)
                        .border(
                            width = if (isSelected) 3.dp else 1.5.dp,
                            color = if (isSelected) Color(0xFFD84315) else Color(0xFFE0E0E0),
                            shape = RoundedCornerShape(16.dp)
                        )
                        .clickable {
                            selectedNumber = num
                            soundHelper.playPop()
                        }
                        .testTag("number_strip_$num"),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = NumberFormatter.formatNumber(num, currentLanguage),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Black,
                            color = if (isSelected) Color.White else Color(0xFF1E1E1E)
                        )
                        Text(
                            text = numTheme.emoji,
                            fontSize = 12.sp
                        )
                    }
                }
            }
        }
    }
}
