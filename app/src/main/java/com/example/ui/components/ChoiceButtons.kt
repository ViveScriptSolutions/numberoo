package com.example.ui.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import com.example.model.Language
import com.example.util.NumberFormatter
import kotlinx.coroutines.launch

@Composable
fun ChoiceButtons(
    options: List<Int>,
    currentLanguage: Language,
    onSelectAnswer: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val haptic = LocalHapticFeedback.current
    val scope = rememberCoroutineScope()

    val buttonColors = listOf(
        Pair(Color(0xFFFF5722), Color(0xFFFFCCBC)), // Warm Coral
        Pair(Color(0xFF0288D1), Color(0xFFB3E5FC)), // Sky Blue
        Pair(Color(0xFF43A047), Color(0xFFC8E6C9)), // Mint Green
        Pair(Color(0xFF8E24AA), Color(0xFFE1BEE7))  // Purple Candy
    )

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 6.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        options.forEachIndexed { index, option ->
            val colorPair = buttonColors[index % buttonColors.size]
            val scale = remember { Animatable(1f) }

            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(74.dp)
                    .scale(scale.value)
                    .shadow(6.dp, RoundedCornerShape(24.dp))
                    .clip(RoundedCornerShape(24.dp))
                    .background(colorPair.second)
                    .border(3.5.dp, colorPair.first, RoundedCornerShape(24.dp))
                    .clickable {
                        haptic.performHapticFeedback(androidx.compose.ui.hapticfeedback.HapticFeedbackType.LongPress)
                        scope.launch {
                            scale.animateTo(0.88f, spring(stiffness = Spring.StiffnessHigh))
                            scale.animateTo(1.0f, spring(stiffness = Spring.StiffnessMedium))
                        }
                        onSelectAnswer(option)
                    }
                    .testTag("choice_button_$option"),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = NumberFormatter.formatNumber(option, currentLanguage),
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Black,
                    color = colorPair.first
                )
            }
        }
    }
}
