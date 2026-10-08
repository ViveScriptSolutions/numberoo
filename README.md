# 🦘 Numberoo - Learn Count Add Subtract

A colorful, playful educational Android application designed for young children to learn numbers, counting, simple addition, and simple subtraction through visual exploration.

Published by **ViveScript Solutions LLC**  
Website: [https://www.vivescriptsolutions.com/](https://www.vivescriptsolutions.com/)  
Copyright: **© 2026 ViveScript Solutions LLC.**

---

## Overview

**Numberoo** is crafted specifically for toddlers, preschoolers, and early elementary learners who are beginning to explore numbers and basic arithmetic. Guided by a cute kangaroo mascot named **Numberoo**, children discover mathematics through large colorful visuals, playful 2D illustrations, familiar everyday objects (apples, stars, balloons, ducks, cupcakes), animations, and cheerful positive feedback.

The app uses extra-large numbers, minimal text, clear visual instructions, and cheerful sound effects so children can independently use and enjoy the app even before they read fluently.

---

## Key Features

* **4 Focused Learning Modes:**
  1. **Learn Numbers (0–20):** Large numbers, bilingual number words, spoken pronunciations, and touch-to-count objects.
  2. **Count Objects:** Interactive items (apples, stars, balloons, kittens) that bounce and display sequential count badges when tapped.
  3. **Visual Addition (+):** Side-by-side object groups that clearly illustrate how quantities combine.
  4. **Visual Subtraction (−):** Intuitive "taking away" visualization where subtracted items are crossed out.
* **Bilingual Support (English & Bangla):** One-tap toggle switches between English numerals/words and Bengali numerals (১, ২, ৩...) with native speech.
* **Positive Feedback & Stars:** Gold star reward system with celebratory confetti particle bursts on correct answers.
* **Child-Friendly Audio & TTS:** Integrated `TextToSpeech` and audio tones with simple mute/unmute toggle.
* **100% Offline Capable:** Entire core educational experience works with zero internet connection.
* **No Accounts or Tracking:** Zero personal data collected; completely private and COPPA/Families compliant.
* **Centralized AdMob Integration:** Clean, child-directed test advertising setup isolated in `AdConfig.kt`.

---

## Technology Stack

* **Language:** Kotlin
* **UI Framework:** Jetpack Compose (Material 3)
* **Architecture:** Clean MVVM / State-Driven Compose Architecture
* **Audio & Speech:** Android `TextToSpeech` & `ToneGenerator`
* **Local Persistence:** Android `SharedPreferences` (stars, language, mute state)
* **Build System:** Gradle (Kotlin DSL - `.gradle.kts`) with Version Catalog (`libs.versions.toml`)
* **Monetization:** Google Mobile Ads SDK (AdMob) with child-directed COPPA request settings
* **Testing:** JUnit & Robolectric local JVM tests

---

## Requirements

* **Minimum SDK:** Android 7.0 (API 24)
* **Target SDK:** Android 16 (API 36)
* **Compile SDK:** Android 16 (API 36)
* **JDK:** Java 11 / Java 17

---

## Installation & Building

### Clone Repository
```bash
git clone https://github.com/vivescriptsolutions/numberoo.git
cd numberoo
```

### Open in Android Studio
1. Open Android Studio Ladybug or newer.
2. Select **File > Open** and choose the project directory.
3. Allow Gradle to synchronize dependencies.

### Build via Gradle
```bash
# Build Debug APK
gradle :app:assembleDebug

# Run Unit & Robolectric Tests
gradle :app:testDebugUnitTest

# Build Release App Bundle (for Google Play)
gradle :app:bundleRelease
```

---

## Project Structure

```text
numberoo/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/example/
│   │   │   │   ├── ads/
│   │   │   │   │   ├── AdBanner.kt           # Compose banner wrapper
│   │   │   │   │   └── AdConfig.kt           # Centralized AdMob test/prod IDs
│   │   │   │   ├── model/
│   │   │   │   │   ├── CountingItem.kt       # Themes (apples, stars, etc.)
│   │   │   │   │   ├── Language.kt           # English & Bangla language models
│   │   │   │   │   └── LearningMode.kt       # Learn, Count, Add, Subtract
│   │   │   │   ├── ui/
│   │   │   │   │   ├── components/
│   │   │   │   │   │   ├── CelebrationOverlay.kt  # Confetti celebration
│   │   │   │   │   │   ├── ChoiceButtons.kt       # Jumbo answer buttons
│   │   │   │   │   │   ├── NumberooMascotCard.kt  # Kangaroo speech bubble
│   │   │   │   │   │   └── TopHeaderBar.kt        # Mode tabs & controls
│   │   │   │   │   ├── screens/
│   │   │   │   │   │   ├── AdditionScreen.kt      # Visual addition
│   │   │   │   │   │   ├── CountObjectsScreen.kt  # Tap-to-count screen
│   │   │   │   │   │   ├── LearnNumbersScreen.kt  # 0 to 20 explorer
│   │   │   │   │   │   ├── ParentInfoDialog.kt    # About & privacy dialog
│   │   │   │   │   │   └── SubtractionScreen.kt   # Visual subtraction
│   │   │   │   │   └── theme/
│   │   │   │   │       ├── Color.kt
│   │   │   │   │       ├── Theme.kt
│   │   │   │   │       └── Type.kt
│   │   │   │   ├── util/
│   │   │   │   │   ├── NumberFormatter.kt    # English & Bangla conversions
│   │   │   │   │   ├── SoundHelper.kt        # Audio & TTS player
│   │   │   │   │   └── UserPreferences.kt    # Local star persistence
│   │   │   │   └── MainActivity.kt           # Application scaffold
│   │   │   ├── res/                          # Vector icons, mipmaps, drawables
│   │   │   └── AndroidManifest.xml
│   │   └── test/                             # Unit & Robolectric tests
│   └── build.gradle.kts
├── gradle/
│   └── libs.versions.toml
├── CHANGELOG.md
├── DESIGN_ASSETS.md
├── LICENSE
├── PLAY_STORE_DATA_SAFETY.md
├── PLAY_STORE_METADATA.md
├── PRIVACY_POLICY.md
├── README.md
├── settings.gradle.kts
└── TERMS_OF_SERVICE.md
```

---

## Privacy & Safety

* **No Personal Data Collected:** Numberoo does not require sign-up, email, phone number, or device IDs.
* **Child Safe:** Verified COPPA compliance with child-directed ad request tagging (`MAX_AD_CONTENT_RATING_G`).
* **Offline-First:** All math logic and counting mechanics run strictly locally.

---

## AdMob Production Migration

Ad configuration is centrally managed in `app/src/main/java/com/example/ads/AdConfig.kt`:
1. Set `IS_TEST_MODE = false`.
2. Replace `PROD_BANNER_AD_UNIT_ID` with your verified ViveScript AdMob Banner Unit ID.
3. Update `com.google.android.gms.ads.APPLICATION_ID` in `AndroidManifest.xml` with your production AdMob App ID.

---

## License

The original source code is released under the **MIT License**.  
See the [LICENSE](LICENSE) file for complete details.

### Proprietary Branding Notice
© 2026 ViveScript Solutions LLC. The app name **Numberoo**, mascot character designs, logos, app icons, screenshots, and visual branding assets are proprietary property of **ViveScript Solutions LLC** and are not licensed under the MIT License.
