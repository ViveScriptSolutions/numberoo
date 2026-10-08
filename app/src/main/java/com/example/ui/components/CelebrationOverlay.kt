package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.Language
import com.example.ui.theme.KangarooGold
import com.example.ui.theme.PlayfulMintGreen
import com.example.ui.theme.PlayfulYellow
import kotlin.random.Random

data class ConfettiParticle(
    val x: Float,
    val initialY: Float,
    val speed: Float,
    val color: Color,
    val radius: Float
)

@Composable
fun CelebrationOverlay(
    visible: Boolean,
    language: Language,
    onContinue: () -> Unit,
    modifier: Modifier = Modifier
) {
    if (!visible) return

    val progress = remember { Animatable(0f) }
    val particles = remember {
        val colors = listOf(
            Color(0xFFFF5722),
            Color(0xFFFFEB3B),
            Color(0xFF4CAF50),
            Color(0xFF2196F3),
            Color(0xFFE91E63),
            Color(0xFF9C27B0)
        )
        List(40) {
            ConfettiParticle(
                x = Random.nextFloat(),
                initialY = Random.nextFloat() * -0.5f,
                speed = 0.5f + Random.nextFloat() * 0.8f,
                color = colors.random(),
                radius = 6f + Random.nextFloat() * 8f
            )
        }
    }

    LaunchedEffect(visible) {
        progress.snapTo(0f)
        progress.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 1200, easing = FastOutSlowInEasing)
        )
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.45f))
            .testTag("celebration_overlay"),
        contentAlignment = Alignment.Center
    ) {
        // Confetti Canvas
        Canvas(modifier = Modifier.fillMaxSize()) {
            val canvasWidth = size.width
            val canvasHeight = size.height
            val currentProgress = progress.value

            particles.forEach { p ->
                val currentY = (p.initialY + currentProgress * p.speed) * canvasHeight
                val currentX = p.x * canvasWidth + kotlin.math.sin(currentProgress * 6f + p.x * 10f) * 30f
                if (currentY in 0f..canvasHeight) {
                    drawCircle(
                        color = p.color,
                        radius = p.radius,
                        center = Offset(currentX.toFloat(), currentY)
                    )
                }
            }
        }

        // Dialog Card
        AnimatedVisibility(
            visible = visible,
            enter = scaleIn() + fadeIn(),
            exit = scaleOut() + fadeOut()
        ) {
            Card(
                shape = RoundedCornerShape(28.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 12.dp),
                modifier = Modifier
                    .padding(32.dp)
                    .testTag("celebration_card")
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(76.dp)
                            .clip(CircleShape)
                            .background(PlayfulYellow.copy(alpha = 0.3f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "⭐", fontSize = 42.sp)
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = if (language == Language.BANGLA) "চমৎকার হয়েছে!" else "Great Job!",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = KangarooGold
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = if (language == Language.BANGLA) "তুমি ১টি স্টার পেয়েছো!" else "You earned a Star!",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = PlayfulMintGreen
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    Button(
                        onClick = onContinue,
                        colors = ButtonDefaults.buttonColors(containerColor = KangarooGold),
                        shape = RoundedCornerShape(20.dp),
                        modifier = Modifier
                            .height(52.dp)
                            .testTag("celebration_continue_button")
                    ) {
                        Text(
                            text = if (language == Language.BANGLA) "পরের খেলা ➡️" else "Next Question ➡️",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}
