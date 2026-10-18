package com.example

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
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
import com.example.ui.screens.CountObjectsScreen
import com.example.ui.screens.FindNumberScreen
import com.example.ui.screens.LearnNumbersScreen
import com.example.ui.screens.ParentInfoDialog
import com.example.ui.theme.NumberooTheme
import com.example.util.BackgroundMusicPlayer
import com.example.util.SoundHelper
import com.example.util.UserPreferences

class MainActivity : ComponentActivity() {

    private lateinit var soundHelper: SoundHelper
    private lateinit var userPreferences: UserPreferences
    private lateinit var interstitialAdManager: InterstitialAdManager
    private lateinit var backgroundMusicPlayer: BackgroundMusicPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Centralized Google Mobile Ads initialization (COPPA & Families compliant)
        AdConfig.initialize(this)

        soundHelper = SoundHelper(this)
        userPreferences = UserPreferences(this)
        interstitialAdManager = InterstitialAdManager(this)
        backgroundMusicPlayer = BackgroundMusicPlayer()

        soundHelper.isSoundEnabled = userPreferences.isSoundEnabled

        if (userPreferences.isMusicEnabled) {
            backgroundMusicPlayer.start()
        }

        setContent {
            NumberooTheme {
                NumberooApp(
                    soundHelper = soundHelper,
                    userPreferences = userPreferences,
                    interstitialAdManager = interstitialAdManager,
                    backgroundMusicPlayer = backgroundMusicPlayer
                )
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (::userPreferences.isInitialized && userPreferences.isMusicEnabled && ::backgroundMusicPlayer.isInitialized) {
            backgroundMusicPlayer.start()
        }
    }

    override fun onPause() {
        super.onPause()
        if (::backgroundMusicPlayer.isInitialized) {
            backgroundMusicPlayer.pause()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        soundHelper.release()
        if (::backgroundMusicPlayer.isInitialized) {
            backgroundMusicPlayer.release()
        }
    }
}

@Composable
fun NumberooApp(
    soundHelper: SoundHelper,
    userPreferences: UserPreferences,
    interstitialAdManager: InterstitialAdManager,
    backgroundMusicPlayer: BackgroundMusicPlayer
) {
    val context = LocalContext.current
    val activity = context as? Activity

    var selectedMode by remember { mutableStateOf(LearningMode.NUMBER_LOOP) }
    var currentLanguage by remember { mutableStateOf(userPreferences.selectedLanguage) }
    var isSoundEnabled by remember { mutableStateOf(userPreferences.isSoundEnabled) }
    var isMusicEnabled by remember { mutableStateOf(userPreferences.isMusicEnabled) }
    var starsCollected by remember { mutableIntStateOf(userPreferences.starsCollected) }
    var showParentInfo by remember { mutableStateOf(false) }

    fun handleAddStar() {
        val newStars = userPreferences.addStar()
        starsCollected = newStars
    }

    // Outer container with statusBarsPadding and navigationBarsPadding:
    // Guarantees zero status bar overlap and zero navigation bar overlap on any phone
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .statusBarsPadding()
            .navigationBarsPadding()
            .testTag("numberoo_main_container")
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            contentWindowInsets = WindowInsets(0, 0, 0, 0),
            topBar = {
                TopHeaderBar(
                    selectedMode = selectedMode,
                    onSelectMode = { mode ->
                        // Immediate, unblocked tab switching so all screens display instantaneously
                        selectedMode = mode
                        soundHelper.playPop()
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
                    isMusicEnabled = isMusicEnabled,
                    onToggleMusic = {
                        val nextMusic = !isMusicEnabled
                        isMusicEnabled = nextMusic
                        userPreferences.isMusicEnabled = nextMusic
                        if (nextMusic) {
                            backgroundMusicPlayer.start()
                            soundHelper.playPop()
                        } else {
                            backgroundMusicPlayer.pause()
                        }
                    },
                    starsCollected = starsCollected,
                    onOpenInfo = {
                        showParentInfo = true
                        soundHelper.playPop()
                    }
                )
            },
            bottomBar = {
                AdBanner()
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
                        LearningMode.NUMBER_LOOP -> {
                            LearnNumbersScreen(
                                currentLanguage = currentLanguage,
                                soundHelper = soundHelper
                            )
                        }
                        LearningMode.COUNT_ANIMALS -> {
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
                        LearningMode.FIND_NUMBER -> {
                            FindNumberScreen(
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
}
