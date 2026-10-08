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
import com.example.ui.theme.PlayfulMintGreen
import com.example.ui.theme.PlayfulSkyBlue
import com.example.util.NumberFormatter
import com.example.util.SoundHelper
import kotlin.random.Random

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AdditionScreen(
    currentLanguage: Language,
    soundHelper: SoundHelper,
    onAwardStar: () -> Unit,
    modifier: Modifier = Modifier
) {
    var num1 by remember { mutableIntStateOf(Random.nextInt(1, 6)) }
    var num2 by remember { mutableIntStateOf(Random.nextInt(1, 6)) }
    val correctAnswer = num1 + num2
    var itemTheme by remember { mutableStateOf(CountingThemes.random()) }

    var options by remember(correctAnswer) {
        val list = mutableSetOf(correctAnswer)
        while (list.size < 3) {
            val offset = Random.nextInt(2, 12)
            if (offset != correctAnswer) list.add(offset)
        }
        mutableStateOf(list.toList().shuffled())
    }
    var showCelebration by remember { mutableStateOf(false) }
    var mascotHint by remember(num1, num2, currentLanguage) {
        mutableStateOf(
            if (currentLanguage == Language.BANGLA) "দুটো দল মিলিয়ে মোট কতগুলো হয়?"
            else "Put the two groups together to find the sum!"
        )
    }

    fun resetQuestion() {
        val n1 = Random.nextInt(1, 6)
        val n2 = Random.nextInt(1, 6)
        num1 = n1
        num2 = n2
        val sum = n1 + n2
        itemTheme = CountingThemes.random()
        val list = mutableSetOf(sum)
        while (list.size < 3) {
            val offset = Random.nextInt(2, 12)
            if (offset != sum) list.add(offset)
        }
        options = list.toList().shuffled()
        showCelebration = false
        mascotHint = if (currentLanguage == Language.BANGLA) "দুটো দল মিলিয়ে মোট কতগুলো হয়?"
        else "Put the two groups together to find the sum!"
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

            // Visual Equation Box Card
            Card(
                shape = RoundedCornerShape(28.dp),
                colors = CardDefaults.cardColors(containerColor = itemTheme.backgroundColor),
                border = androidx.compose.foundation.BorderStroke(2.dp, itemTheme.primaryColor.copy(alpha = 0.5f)),
                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("addition_card")
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
                            text = NumberFormatter.formatNumber(num1, currentLanguage),
                            fontSize = 38.sp,
                            fontWeight = FontWeight.Black,
                            color = PlayfulSkyBlue
                        )
                        Text(
                            text = "  +  ",
                            fontSize = 36.sp,
                            fontWeight = FontWeight.Black,
                            color = KangarooGold
                        )
                        Text(
                            text = NumberFormatter.formatNumber(num2, currentLanguage),
                            fontSize = 38.sp,
                            fontWeight = FontWeight.Black,
                            color = PlayfulMintGreen
                        )
                        Text(
                            text = "  =  ?",
                            fontSize = 38.sp,
                            fontWeight = FontWeight.Black,
                            color = KangarooGold
                        )
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Visual Groups Container
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        // Left Group Box
                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(20.dp))
                                .background(Color.White)
                                .border(2.dp, PlayfulSkyBlue, RoundedCornerShape(20.dp))
                                .padding(10.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = NumberFormatter.formatNumber(num1, currentLanguage),
                                fontWeight = FontWeight.Bold,
                                color = PlayfulSkyBlue,
                                fontSize = 16.sp
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            FlowRow(
                                horizontalArrangement = Arrangement.Center,
                                verticalArrangement = Arrangement.Center
                            ) {
                                for (i in 1..num1) {
                                    Text(
                                        text = itemTheme.emoji,
                                        fontSize = 24.sp,
                                        modifier = Modifier.padding(3.dp)
                                    )
                                }
                            }
                        }

                        // Big Plus Badge
                        Box(
                            modifier = Modifier
                                .padding(horizontal = 8.dp)
                                .size(42.dp)
                                .clip(CircleShape)
                                .background(KangarooGold),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = "➕", fontSize = 20.sp)
                        }

                        // Right Group Box
                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(20.dp))
                                .background(Color.White)
                                .border(2.dp, PlayfulMintGreen, RoundedCornerShape(20.dp))
                                .padding(10.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = NumberFormatter.formatNumber(num2, currentLanguage),
                                fontWeight = FontWeight.Bold,
                                color = PlayfulMintGreen,
                                fontSize = 16.sp
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            FlowRow(
                                horizontalArrangement = Arrangement.Center,
                                verticalArrangement = Arrangement.Center
                            ) {
                                for (i in 1..num2) {
                                    Text(
                                        text = itemTheme.emoji,
                                        fontSize = 24.sp,
                                        modifier = Modifier.padding(3.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = if (currentLanguage == Language.BANGLA) "যোগফল কত হবে?" else "What is the total sum?",
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
                            "${NumberFormatter.toBanglaNumeral(num1)} যোগ ${NumberFormatter.toBanglaNumeral(num2)} সমান ${NumberFormatter.toBanglaNumeral(correctAnswer)}"
                        else
                            "$num1 plus $num2 equals $correctAnswer"
                        soundHelper.speak(text, currentLanguage)
                        onAwardStar()
                        showCelebration = true
                    } else {
                        soundHelper.playError()
                        mascotHint = if (currentLanguage == Language.BANGLA)
                            "সবগুলো একসাথে গুনে দেখো!"
                        else
                            "Count all of them together to find the answer!"
                    }
                }
            )
        }

        CelebrationOverlay(
            visible = showCelebration,
            language = currentLanguage,
            onContinue = { resetQuestion() }
        )
    }
}
