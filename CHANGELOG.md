# Changelog

All notable changes to the **Numberoo** application will be documented in this file.

## [4.0.0] - 2026-10-18
### Changed & Improved
- Bumped `versionCode` to 4 and `versionName` to "4.0".
- Configured Privacy Sandbox `ad-services-config.xml` with `allowAdPartners="false"` for attribution and topics.
- Refined UI layout with safe insets to guarantee zero top bar overlap on all edge-to-edge device form factors.
- Integrated mild background music player with dedicated header toggle.

## [3.0.0] - 2026-10-17
### Fixed & Optimized
- **Outdated SDK Fix:** Upgraded `androidx.fragment:fragment` to modern `1.8.6` (resolving Google Play SDK warning for fragment 1.1.0).
- **Memory Optimization:** Reduced in-app drawable memory footprint by over 90% (purged raw screenshot bitmaps from runtime APK, optimized remaining drawables, commented out unused SDK libraries).

### Changed & Refocused (Ages 3–6 Toddlers)
- Removed arithmetic calculation modes (Addition and Subtraction) to focus purely on number recognition and counting.
- **Numbers 1–20 Loop:** Interactive number loop with Auto-Play slideshow mode, voice pronunciation, and 20 adorable animated animals and characters.
- **Tap & Count 1–10:** Large, bouncy animal tapping with sequential count badges, pop tones, and celebratory confetti.
- **Find & Tap:** Playful animal number matching with gentle voice prompts and zero failure penalties.
- Bumped versionCode to 3 and versionName to "3.0".

## [2.0.0] - 2026-10-07
### Added
- Generated full Google Play Store graphics package in `/play_store_assets/`:
  - 512x512 High-resolution Play Store icon (`icon_512x512.png`).
  - 1024x500 Feature graphic banner (`feature_graphic_1024x500.png`).
  - Phone screenshot showcase (1080x1920 9:16).
  - 7-inch tablet screenshot showcase (1200x1920).
  - 10-inch tablet screenshot showcase (1920x1200 16:10).
- Embedded high-resolution feature banner in-app Parent Info dialog.
- Bumped versionCode to 2 and versionName to "2.0".

## [1.0.0] - 2026-10-07
### Added
- Initial production-ready release of Numberoo.
- 4 core interactive math activities:
  - **Learn Numbers:** Interactive 0 to 20 explorer with spoken pronunciations and bilingual cards.
  - **Count Objects:** Tap-to-count items with animated bounce, number badges, and pop sounds.
  - **Simple Addition (+):** Dual-group visual addition with sums up to 12.
  - **Simple Subtraction (−):** Visual "taking away" with crossed-out object illustrations.
- Numberoo Kangaroo mascot with encouraging feedback bubbles.
- Bilingual localization support: English & Bangla (বাংলা) with native Bengali digits (০-৯) and words.
- Gold star reward system with celebratory confetti overlays.
- Text-to-Speech audio support with pitch tuning for children.
- Isolated test AdMob configuration (`AdConfig`) with child-directed COPPA request parameters.
- Clean adaptive launcher icon and legacy density mipmaps.
- Complete Google Play store metadata, data safety documentation, privacy policy, and MIT license.
