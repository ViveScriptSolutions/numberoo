package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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
import com.example.ui.theme.PlayfulCoral
import com.example.ui.theme.PlayfulMintGreen
import com.example.ui.theme.PlayfulSkyBlue
import com.example.util.NumberFormatter
import com.example.util.SoundHelper
import kotlin.random.Random

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SubtractionScreen(
    currentLanguage: Language,
    soundHelper: SoundHelper,
    onAwardStar: () -> Unit,
    onNextQuestion: (() -> Unit) -> Unit = { it() },
    modifier: Modifier = Modifier
) {
    // Total is between 3 and 10, takeAway is between 1 and total - 1
    var total by remember { mutableIntStateOf(Random.nextInt(3, 9)) }
    var takeAway by remember { mutableIntStateOf(Random.nextInt(1, total)) }
    val correctAnswer = total - takeAway
    var itemTheme by remember { mutableStateOf(CountingThemes.random()) }

    var options by remember(correctAnswer) {
        val list = mutableSetOf(correctAnswer)
        while (list.size < 3) {
            val offset = Random.nextInt(0, 8)
            if (offset != correctAnswer) list.add(offset)
        }
        mutableStateOf(list.toList().shuffled())
    }
    var showCelebration by remember { mutableStateOf(false) }
    var mascotHint by remember(total, takeAway, currentLanguage) {
        mutableStateOf(
            if (currentLanguage == Language.BANGLA) "কয়েকটি সরিয়ে নিলে কয়টি অবশিষ্ট থাকে?"
            else "Take some away! How many are left?"
        )
    }

    fun resetQuestion() {
        val t = Random.nextInt(3, 9)
        val s = Random.nextInt(1, t)
        total = t
        takeAway = s
        val remaining = t - s
        itemTheme = CountingThemes.random()
        val list = mutableSetOf(remaining)
        while (list.size < 3) {
            val offset = Random.nextInt(0, 8)
            if (offset != remaining) list.add(offset)
        }
        options = list.toList().shuffled()
        showCelebration = false
        mascotHint = if (currentLanguage == Language.BANGLA) "কয়েকটি সরিয়ে নিলে কয়টি অবশিষ্ট থাকে?"
        else "Take some away! How many are left?"
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
                speechText = mascotHint,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            // Visual Subtraction Card
            Card(
                shape = RoundedCornerShape(28.dp),
                colors = CardDefaults.cardColors(containerColor = itemTheme.backgroundColor),
                border = androidx.compose.foundation.BorderStroke(2.dp, itemTheme.primaryColor.copy(alpha = 0.5f)),
                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("subtraction_card")
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Jumbo Numeric Equation Header
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = NumberFormatter.formatNumber(total, currentLanguage),
                            fontSize = 38.sp,
                            fontWeight = FontWeight.Black,
                            color = PlayfulSkyBlue
                        )
                        Text(
                            text = "  −  ",
                            fontSize = 36.sp,
                            fontWeight = FontWeight.Black,
                            color = PlayfulCoral
                        )
                        Text(
                            text = NumberFormatter.formatNumber(takeAway, currentLanguage),
                            fontSize = 38.sp,
                            fontWeight = FontWeight.Black,
                            color = PlayfulCoral
                        )
                        Text(
                            text = "  =  ?",
                            fontSize = 38.sp,
                            fontWeight = FontWeight.Black,
                            color = KangarooGold
                        )
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Visual Representation with "Taken Away" crossed out items
                    FlowRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(20.dp))
                            .background(Color.White)
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalArrangement = Arrangement.Center
                    ) {
                        for (i in 1..total) {
                            val isTakenAway = i > (total - takeAway)

                            Box(
                                modifier = Modifier
                                    .padding(6.dp)
                                    .size(54.dp)
                                    .clip(CircleShape)
                                    .background(if (isTakenAway) Color(0xFFFFEBEE) else PlayfulMintGreen.copy(alpha = 0.15f))
                                    .border(
                                        2.dp,
                                        if (isTakenAway) PlayfulCoral.copy(alpha = 0.6f) else PlayfulMintGreen,
                                        CircleShape
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = itemTheme.emoji,
                                    fontSize = 24.sp,
                                    modifier = Modifier.alpha(if (isTakenAway) 0.35f else 1f)
                                )

                                if (isTakenAway) {
                                    // Big Red X overlay showing "taken away"
                                    Text(
                                        text = "❌",
                                        fontSize = 18.sp
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = if (currentLanguage == Language.BANGLA)
                            "বাকি রইল সবুজ বৃত্তের বস্তুগুলো!"
                        else
                            "Count the remaining items without ❌!",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.Gray
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = if (currentLanguage == Language.BANGLA) "বিয়োগফল কত?" else "How many are left?",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = KangarooGold
            )

            Spacer(modifier = Modifier.height(10.dp))

            ChoiceButtons(
                options = options,
                currentLanguage = currentLanguage,
                onSelectAnswer = { selected ->
                    if (selected == correctAnswer) {
                        soundHelper.playFanfare()
                        val text = if (currentLanguage == Language.BANGLA)
                            "${NumberFormatter.toBanglaNumeral(total)} বিয়োগ ${NumberFormatter.toBanglaNumeral(takeAway)} সমান ${NumberFormatter.toBanglaNumeral(correctAnswer)}"
                        else
                            "$total minus $takeAway equals $correctAnswer"
                        soundHelper.speak(text, currentLanguage)
                        onAwardStar()
                        showCelebration = true
                    } else {
                        soundHelper.playError()
                        mascotHint = if (currentLanguage == Language.BANGLA)
                            "যেগুলো কাটা পড়েনি, সেগুলো গুনে দেখো!"
                        else
                            "Count the items that are NOT crossed out!"
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
