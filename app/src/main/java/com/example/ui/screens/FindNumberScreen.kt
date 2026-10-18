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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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

@Composable
fun FindNumberScreen(
    currentLanguage: Language,
    soundHelper: SoundHelper,
    onAwardStar: () -> Unit,
    onNextQuestion: (() -> Unit) -> Unit = { it() },
    modifier: Modifier = Modifier
) {
    // Numbers 1 to 10 for toddlers
    var targetNumber by remember { mutableIntStateOf(Random.nextInt(1, 11)) }
    var itemTheme by remember { mutableStateOf(CountingThemes.getItemForNumber(targetNumber)) }

    var options by remember(targetNumber) {
        val list = mutableSetOf(targetNumber)
        while (list.size < 3) {
            val offset = Random.nextInt(1, 11)
            if (offset != targetNumber) list.add(offset)
        }
        mutableStateOf(list.toList().shuffled())
    }
    var showCelebration by remember { mutableStateOf(false) }

    fun askQuestion() {
        val word = NumberFormatter.getWord(targetNumber, currentLanguage)
        val prompt = if (currentLanguage == Language.BANGLA) {
            "তুমি কি $word ($targetNumber) নম্বরটি খুঁজে পাবে? ${itemTheme.banglaName}"
        } else {
            "Can you tap number $word ($targetNumber)? ${itemTheme.emoji}"
        }
        soundHelper.speak(prompt, currentLanguage)
    }

    LaunchedEffect(targetNumber, itemTheme) {
        askQuestion()
    }

    fun resetQuestion() {
        val newTarget = Random.nextInt(1, 11)
        targetNumber = newTarget
        itemTheme = CountingThemes.getItemForNumber(newTarget)
        val list = mutableSetOf(newTarget)
        while (list.size < 3) {
            val offset = Random.nextInt(1, 11)
            if (offset != newTarget) list.add(offset)
        }
        options = list.toList().shuffled()
        showCelebration = false
    }

    val infiniteTransition = rememberInfiniteTransition(label = "animalDance")
    val animalBounce by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = -12f,
        animationSpec = infiniteRepeatable(
            animation = tween(500, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "bounce"
    )

    val mascotHint = if (currentLanguage == Language.BANGLA) {
        "নাম্বারটি খুঁজে বের করো: ${NumberFormatter.getWord(targetNumber, currentLanguage)} (${itemTheme.emoji})"
    } else {
        "Can you find number ${NumberFormatter.getWord(targetNumber, currentLanguage)}? (${itemTheme.emoji})"
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

            // Animated Animal Showcase Card
            Card(
                shape = RoundedCornerShape(32.dp),
                colors = CardDefaults.cardColors(containerColor = itemTheme.backgroundColor),
                border = androidx.compose.foundation.BorderStroke(3.dp, itemTheme.primaryColor.copy(alpha = 0.5f)),
                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("find_number_card")
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = if (currentLanguage == Language.BANGLA) "খুঁজে বের করো:" else "Find this number:",
                        fontSize = 19.sp,
                        fontWeight = FontWeight.Black,
                        color = Color(0xFF1E1E1E)
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    // Big Animal Mascot Avatar with bouncy animation
                    Box(
                        modifier = Modifier
                            .size(110.dp)
                            .clip(CircleShape)
                            .background(Color.White)
                            .border(3.5.dp, itemTheme.primaryColor, CircleShape)
                            .clickable { askQuestion() },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = itemTheme.emoji,
                            fontSize = 62.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Spoken Word Bar
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .background(Color.White)
                            .border(2.dp, itemTheme.primaryColor.copy(alpha = 0.35f), RoundedCornerShape(20.dp))
                            .clickable { askQuestion() }
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        Text(
                            text = NumberFormatter.getWord(targetNumber, currentLanguage),
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Black,
                            color = itemTheme.primaryColor
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.VolumeUp,
                            contentDescription = "Hear number word",
                            tint = itemTheme.primaryColor,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = if (currentLanguage == Language.BANGLA) "সঠিক বাটনটিতে চাপ দাও:" else "Tap the matching number button:",
                fontSize = 17.sp,
                fontWeight = FontWeight.Black,
                color = Color(0xFF1E1E1E)
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Large Choice Buttons
            ChoiceButtons(
                options = options,
                currentLanguage = currentLanguage,
                onSelectAnswer = { selected ->
                    if (selected == targetNumber) {
                        soundHelper.playFanfare()
                        val correctSpeech = if (currentLanguage == Language.BANGLA) {
                            "চমৎকার! এটা হলো ${NumberFormatter.getWord(targetNumber, currentLanguage)}!"
                        } else {
                            "Hooray! That is number ${NumberFormatter.getWord(targetNumber, currentLanguage)}!"
                        }
                        soundHelper.speak(correctSpeech, currentLanguage)
                        onAwardStar()
                        showCelebration = true
                    } else {
                        soundHelper.playError()
                        val retrySpeech = if (currentLanguage == Language.BANGLA) {
                            "আবার খুঁজে দেখো! তুমি পারবে!"
                        } else {
                            "Look again! You can do it!"
                        }
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
