package com.example.ui.screens

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
    var targetCount by remember { mutableIntStateOf(Random.nextInt(1, 9)) }
    var itemTheme by remember { mutableStateOf(CountingThemes.random()) }
    val tappedCountMap = remember(targetCount, itemTheme) { mutableStateMapOf<Int, Int>() }
    var tapCounter by remember(targetCount, itemTheme) { mutableIntStateOf(0) }
    var options by remember(targetCount) {
        val list = mutableSetOf(targetCount)
        while (list.size < 3) {
            val offset = Random.nextInt(1, 10)
            if (offset != targetCount) list.add(offset)
        }
        mutableStateOf(list.toList().shuffled())
    }
    var showCelebration by remember { mutableStateOf(false) }
    var mascotMessage by remember(targetCount, currentLanguage) {
        mutableStateOf(
            if (currentLanguage == Language.BANGLA) "এখানে কয়টি ${itemTheme.banglaName} আছে? স্পর্শ করে গুনো!"
            else "How many ${itemTheme.englishNamePlural} can you count? Tap them!"
        )
    }

    val haptic = LocalHapticFeedback.current
    val scope = rememberCoroutineScope()

    fun resetQuestion() {
        val newCount = Random.nextInt(1, 10)
        targetCount = newCount
        itemTheme = CountingThemes.random()
        val list = mutableSetOf(newCount)
        while (list.size < 3) {
            val offset = Random.nextInt(1, 10)
            if (offset != newCount) list.add(offset)
        }
        options = list.toList().shuffled()
        showCelebration = false
        mascotMessage = if (currentLanguage == Language.BANGLA)
            "এখানে কয়টি ${itemTheme.banglaName} আছে? স্পর্শ করে গুনো!"
        else
            "How many ${itemTheme.englishNamePlural} can you count? Tap them!"
    }

    Box(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            NumberooMascotCard(
                speechText = mascotMessage,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            // Playful Counting Playmat Card
            Card(
                shape = RoundedCornerShape(28.dp),
                colors = CardDefaults.cardColors(containerColor = itemTheme.backgroundColor),
                border = androidx.compose.foundation.BorderStroke(2.dp, itemTheme.primaryColor.copy(alpha = 0.5f)),
                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("count_playmat_card")
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(18.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = if (currentLanguage == Language.BANGLA) "গণনা করো:" else "Count the objects:",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = itemTheme.primaryColor
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    // Flow of objects to count
                    FlowRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(20.dp))
                            .background(Color.White.copy(alpha = 0.75f))
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalArrangement = Arrangement.Center
                    ) {
                        for (i in 1..targetCount) {
                            val badgeNumber = tappedCountMap[i]
                            val scale = remember { Animatable(1f) }

                            Box(
                                modifier = Modifier
                                    .padding(8.dp)
                                    .size(60.dp)
                                    .scale(scale.value)
                                    .clip(CircleShape)
                                    .background(if (badgeNumber != null) itemTheme.primaryColor.copy(alpha = 0.25f) else Color.White)
                                    .border(
                                        width = if (badgeNumber != null) 2.5.dp else 1.5.dp,
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
                                        } else {
                                            soundHelper.playPop()
                                        }
                                        scope.launch {
                                            scale.animateTo(1.35f, spring(stiffness = Spring.StiffnessHigh))
                                            scale.animateTo(1f, spring(stiffness = Spring.StiffnessMedium))
                                        }
                                    }
                                    .testTag("countable_item_$i"),
                                contentAlignment = Alignment.Center
                            ) {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Text(text = itemTheme.emoji, fontSize = 26.sp)
                                    if (badgeNumber != null) {
                                        Text(
                                            text = NumberFormatter.formatNumber(badgeNumber, currentLanguage),
                                            fontSize = 11.sp,
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

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = if (currentLanguage == Language.BANGLA) "সঠিক উত্তরটি বাছাই করো:" else "Choose the correct answer:",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = KangarooGold
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Choice Buttons
            ChoiceButtons(
                options = options,
                currentLanguage = currentLanguage,
                onSelectAnswer = { selected ->
                    if (selected == targetCount) {
                        soundHelper.playFanfare()
                        val correctSpeech = if (currentLanguage == Language.BANGLA) "সঠিক হয়েছে!" else "Correct! Awesome job!"
                        soundHelper.speak(correctSpeech, currentLanguage)
                        onAwardStar()
                        showCelebration = true
                    } else {
                        soundHelper.playError()
                        mascotMessage = if (currentLanguage == Language.BANGLA)
                            "আবার চেষ্টা করো! তুমি পারবে!"
                        else
                            "Try counting one more time! You can do it!"
                    }
                }
            )
        }

        // Star Celebration Overlay
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
