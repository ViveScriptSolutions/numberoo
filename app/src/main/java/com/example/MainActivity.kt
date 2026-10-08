package com.example

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import com.example.ads.AdBanner
import com.example.ads.AdConfig
import com.example.ads.InterstitialAdManager
import com.example.model.Language
import com.example.model.LearningMode
import com.example.ui.components.TopHeaderBar
import com.example.ui.screens.AdditionScreen
import com.example.ui.screens.CountObjectsScreen
import com.example.ui.screens.LearnNumbersScreen
import com.example.ui.screens.ParentInfoDialog
import com.example.ui.screens.SubtractionScreen
import com.example.ui.theme.NumberooTheme
import com.example.util.SoundHelper
import com.example.util.UserPreferences

class MainActivity : ComponentActivity() {

    private lateinit var soundHelper: SoundHelper
    private lateinit var userPreferences: UserPreferences
    private lateinit var interstitialAdManager: InterstitialAdManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Centralized Google Mobile Ads initialization (COPPA & Families compliant)
        AdConfig.initialize(this)

        soundHelper = SoundHelper(this)
        userPreferences = UserPreferences(this)
        interstitialAdManager = InterstitialAdManager(this)
        soundHelper.isSoundEnabled = userPreferences.isSoundEnabled

        setContent {
            NumberooTheme {
                NumberooApp(
                    soundHelper = soundHelper,
                    userPreferences = userPreferences,
                    interstitialAdManager = interstitialAdManager
                )
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        soundHelper.release()
    }
}

@Composable
fun NumberooApp(
    soundHelper: SoundHelper,
    userPreferences: UserPreferences,
    interstitialAdManager: InterstitialAdManager
) {
    val context = LocalContext.current
    val activity = context as? Activity

    var selectedMode by remember { mutableStateOf(LearningMode.LEARN) }
    var currentLanguage by remember { mutableStateOf(userPreferences.selectedLanguage) }
    var isSoundEnabled by remember { mutableStateOf(userPreferences.isSoundEnabled) }
    var starsCollected by remember { mutableIntStateOf(userPreferences.starsCollected) }
    var showParentInfo by remember { mutableStateOf(false) }

    fun handleAddStar() {
        val newStars = userPreferences.addStar()
        starsCollected = newStars
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .testTag("numberoo_main_scaffold"),
        topBar = {
            TopHeaderBar(
                selectedMode = selectedMode,
                onSelectMode = { mode ->
                    interstitialAdManager.onMilestoneReached(activity) {
                        selectedMode = mode
                        soundHelper.playPop()
                    }
                },
                currentLanguage = currentLanguage,
                onToggleLanguage = {
                    val nextLang = if (currentLanguage == Language.ENGLISH) Language.BANGLA else Language.ENGLISH
                    currentLanguage = nextLang
                    userPreferences.selectedLanguage = nextLang
                    soundHelper.playPop()
                },
                isSoundEnabled = isSoundEnabled,
                onToggleSound = {
                    val newSound = !isSoundEnabled
                    isSoundEnabled = newSound
                    userPreferences.isSoundEnabled = newSound
                    soundHelper.isSoundEnabled = newSound
                    if (newSound) soundHelper.playPop()
                },
                starsCollected = starsCollected,
                onOpenInfo = {
                    showParentInfo = true
                    soundHelper.playPop()
                }
            )
        },
        bottomBar = {
            Box(modifier = Modifier.navigationBarsPadding()) {
                AdBanner()
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Crossfade(
                targetState = selectedMode,
                label = "modeTransition"
            ) { mode ->
                when (mode) {
                    LearningMode.LEARN -> {
                        LearnNumbersScreen(
                            currentLanguage = currentLanguage,
                            soundHelper = soundHelper
                        )
                    }
                    LearningMode.COUNT -> {
                        CountObjectsScreen(
                            currentLanguage = currentLanguage,
                            soundHelper = soundHelper,
                            onAwardStar = { handleAddStar() },
                            onNextQuestion = { nextAction ->
                                interstitialAdManager.onMilestoneReached(activity) {
                                    nextAction()
                                }
                            }
                        )
                    }
                    LearningMode.ADD -> {
                        AdditionScreen(
                            currentLanguage = currentLanguage,
                            soundHelper = soundHelper,
                            onAwardStar = { handleAddStar() },
                            onNextQuestion = { nextAction ->
                                interstitialAdManager.onMilestoneReached(activity) {
                                    nextAction()
                                }
                            }
                        )
                    }
                    LearningMode.SUBTRACT -> {
                        SubtractionScreen(
                            currentLanguage = currentLanguage,
                            soundHelper = soundHelper,
                            onAwardStar = { handleAddStar() },
                            onNextQuestion = { nextAction ->
                                interstitialAdManager.onMilestoneReached(activity) {
                                    nextAction()
                                }
                            }
                        )
                    }
                }
            }
        }

        if (showParentInfo) {
            ParentInfoDialog(
                onDismiss = { showParentInfo = false }
            )
        }
    }
}
