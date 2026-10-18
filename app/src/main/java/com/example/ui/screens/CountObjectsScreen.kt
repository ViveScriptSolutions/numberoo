package com.example.ui.screens

import androidx.compose.animation.core.Animatable
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
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.CountingItemType
import com.example.model.CountingThemes
import com.example.model.Language
import com.example.ui.components.CelebrationOverlay
import com.example.ui.components.ChoiceButtons
import com.example.ui.components.NumberooMascotCard
import com.example.ui.theme.KangarooGold
import com.example.util.NumberFormatter
import com.example.util.SoundHelper
import kotlinx.coroutines.launch
import kotlin.random.Random

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CountObjectsScreen(
    currentLanguage: Language,
    soundHelper: SoundHelper,
    onAwardStar: () -> Unit,
    onNextQuestion: (() -> Unit) -> Unit = { it() },
    modifier: Modifier = Modifier
) {
    // Strictly 1 to 10 for toddlers (3–6 years)
    var targetCount by remember { mutableIntStateOf(Random.nextInt(1, 10)) }
    var itemTheme by remember { mutableStateOf(CountingThemes.getItemForNumber(targetCount)) }
    val tappedCountMap = remember(targetCount, itemTheme) { mutableStateMapOf<Int, Int>() }
    var tapCounter by remember(targetCount, itemTheme) { mutableIntStateOf(0) }

    var options by remember(targetCount) {
        val list = mutableSetOf(targetCount)
        while (list.size < 3) {
            val offset = Random.nextInt(1, 11)
            if (offset != targetCount) list.add(offset)
        }
        mutableStateOf(list.toList().shuffled())
    }
    var showCelebration by remember { mutableStateOf(false) }

    val haptic = LocalHapticFeedback.current
    val scope = rememberCoroutineScope()

    fun announceQuestion() {
        val prompt = if (currentLanguage == Language.BANGLA) {
            "এখানে কয়টি ${itemTheme.banglaName} আছে? স্পর্শ করে গুনো!"
        } else {
            "How many ${itemTheme.englishNamePlural} can you count? Tap them!"
        }
        soundHelper.speak(prompt, currentLanguage)
    }

    LaunchedEffect(targetCount, itemTheme) {
        announceQuestion()
    }

    fun resetQuestion() {
        val newCount = Random.nextInt(1, 10)
        targetCount = newCount
        itemTheme = CountingThemes.getItemForNumber(newCount)
        tapCounter = 0
        tappedCountMap.clear()
        val list = mutableSetOf(newCount)
        while (list.size < 3) {
            val offset = Random.nextInt(1, 11)
            if (offset != newCount) list.add(offset)
        }
        options = list.toList().shuffled()
        showCelebration = false
    }

    val mascotHint = if (currentLanguage == Language.BANGLA) {
        "কয়টি ${itemTheme.banglaName} দেখতে পাচ্ছো? সবগুলোকে ছুঁয়ে গুনো!"
    } else {
        "Count the ${itemTheme.englishNamePlural}! Tap each one to count along!"
    }

    Box(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(14.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            NumberooMascotCard(
                speechText = mascotHint,
                modifier = Modifier.padding(bottom = 10.dp)
            )

            // Playful Animal Counting Mat
            Card(
                shape = RoundedCornerShape(32.dp),
                colors = CardDefaults.cardColors(containerColor = itemTheme.backgroundColor),
                border = androidx.compose.foundation.BorderStroke(3.dp, itemTheme.primaryColor),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("count_playmat_card")
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = if (currentLanguage == Language.BANGLA)
                                "ছুঁয়ে গুনো: ${itemTheme.banglaName}"
                            else
                                "Tap & Count: ${itemTheme.englishNamePlural}",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Black,
                            color = Color(0xFF1E1E1E)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        IconButton(
                            onClick = { announceQuestion() },
                            modifier = Modifier.size(36.dp)
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.VolumeUp,
                                contentDescription = "Hear question again",
                                tint = itemTheme.primaryColor
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Animated Tappable Animals (1 to 10)
                    FlowRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(24.dp))
                            .background(Color.White.copy(alpha = 0.85f))
                            .padding(14.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalArrangement = Arrangement.Center
                    ) {
                        for (i in 1..targetCount) {
                            val badgeNumber = tappedCountMap[i]
                            val scale = remember { Animatable(1f) }

                            Box(
                                modifier = Modifier
                                    .padding(8.dp)
                                    .size(68.dp)
                                    .scale(scale.value)
                                    .clip(CircleShape)
                                    .background(
                                        if (badgeNumber != null) itemTheme.primaryColor.copy(alpha = 0.22f)
                                        else Color.White
                                    )
                                    .border(
                                        width = if (badgeNumber != null) 3.dp else 2.dp,
                                        color = if (badgeNumber != null) itemTheme.primaryColor else Color(0xFFE0E0E0),
                                        shape = CircleShape
                                    )
                                    .clickable {
                                        haptic.performHapticFeedback(androidx.compose.ui.hapticfeedback.HapticFeedbackType.LongPress)
                                        if (badgeNumber == null) {
                                            tapCounter++
                                            tappedCountMap[i] = tapCounter
                                            val spoken = NumberFormatter.getWord(tapCounter, currentLanguage)
                                            soundHelper.speak(spoken, currentLanguage)
                                            soundHelper.playPop()
                                        } else {
                                            soundHelper.playPop()
                                        }
                                        scope.launch {
                                            scale.animateTo(1.4f, spring(stiffness = Spring.StiffnessHigh))
                                            scale.animateTo(1.0f, spring(stiffness = Spring.StiffnessMedium))
                                        }
                                    }
                                    .testTag("countable_item_$i"),
                                contentAlignment = Alignment.Center
                            ) {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Text(text = itemTheme.emoji, fontSize = 32.sp)
                                    if (badgeNumber != null) {
                                        Text(
                                            text = NumberFormatter.formatNumber(badgeNumber, currentLanguage),
                                            fontSize = 13.sp,
                                            fontWeight = FontWeight.Black,
                                            color = itemTheme.primaryColor
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text = if (currentLanguage == Language.BANGLA) "কয়টি ছিল? উত্তর বেছে নাও:" else "How many did you count?",
                fontSize = 18.sp,
                fontWeight = FontWeight.Black,
                color = Color(0xFF1E1E1E)
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Large Candy Answer Buttons
            ChoiceButtons(
                options = options,
                currentLanguage = currentLanguage,
                onSelectAnswer = { selected ->
                    if (selected == targetCount) {
                        soundHelper.playFanfare()
                        val correctSpeech = if (currentLanguage == Language.BANGLA) {
                            "সঠিক হয়েছে! তুমি ${NumberFormatter.toBanglaNumeral(targetCount)}টি ${itemTheme.banglaName} গুনেছো!"
                        } else {
                            val animal = if (targetCount == 1) itemTheme.englishNameSingular else itemTheme.englishNamePlural
                            "Awesome job! You counted $targetCount $animal!"
                        }
                        soundHelper.speak(correctSpeech, currentLanguage)
                        onAwardStar()
                        showCelebration = true
                    } else {
                        soundHelper.playError()
                        val retrySpeech = if (currentLanguage == Language.BANGLA)
                            "আবার গুনে দেখো! তুমি পারবে!"
                        else
                            "Try counting them again! You can do it!"
                        soundHelper.speak(retrySpeech, currentLanguage)
                    }
                }
            )
        }

        CelebrationOverlay(
            visible = showCelebration,
            language = currentLanguage,
            onContinue = {
                onNextQuestion {
                    resetQuestion()
                }
            }
        )
    }
}
