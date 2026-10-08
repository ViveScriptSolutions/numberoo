package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.Language
import com.example.util.NumberFormatter

@Composable
fun ChoiceButtons(
    options: List<Int>,
    currentLanguage: Language,
    onSelectAnswer: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val haptic = LocalHapticFeedback.current

    val buttonColors = listOf(
        Pair(Color(0xFFFF7043), Color(0xFFFFCCBC)), // Coral
        Pair(Color(0xFF42A5F5), Color(0xFFBBDEFB)), // Blue
        Pair(Color(0xFF66BB6A), Color(0xFFC8E6C9)), // Green
        Pair(Color(0xFFAB47BC), Color(0xFFE1BEE7))  // Purple
    )

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        options.forEachIndexed { index, option ->
            val colorPair = buttonColors[index % buttonColors.size]
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(68.dp)
                    .shadow(4.dp, RoundedCornerShape(22.dp))
                    .clip(RoundedCornerShape(22.dp))
                    .background(colorPair.second)
                    .border(3.dp, colorPair.first, RoundedCornerShape(22.dp))
                    .clickable {
                        haptic.performHapticFeedback(androidx.compose.ui.hapticfeedback.HapticFeedbackType.LongPress)
                        onSelectAnswer(option)
                    }
                    .testTag("choice_button_$option"),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = NumberFormatter.formatNumber(option, currentLanguage),
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Black,
                    color = colorPair.first
                )
            }
        }
    }
}
