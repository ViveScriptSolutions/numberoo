# Design Assets Specification & Deliverables — Numberoo

**Publisher:** ViveScript Solutions LLC  
**Application:** Numberoo - Learn Count Add Subtract  
**Package:** `com.vivescriptsolutions.numberoo`  

---

## 1. Brand Visual Identity

* **Mascot:** "Numberoo", a cute baby cartoon kangaroo with warm golden-amber fur, an expressive joyful smile, and a playful pouch.
* **Vibe:** Cheerful, candy-bright, inviting, warm, uncluttered, and friendly for toddlers and young children.
* **Palette:**
  * **Kangaroo Amber Gold (Primary):** `#FFB300` / `#F57C00` (Pantone 137 C)
  * **Sky Blue (Secondary):** `#0288D1` / `#29B6F6` (Pantone 299 C)
  * **Mint Green (Accent):** `#43A047` / `#C8E6C9` (Pantone 361 C)
  * **Warm Cream (Background):** `#FFFBF0`
  * **Coral / Candy Pink (Alert / Math):** `#E53935` / `#FF8A80`
  * **Purple Plum (Playful Accent):** `#7E57C2`

---

## 2. Generated Google Play Store Graphics

All assets below are pre-rendered and exported directly in `/play_store_assets/` ready for immediate upload to the **Google Play Console**:

| Asset File | Target Purpose | Dimensions | Format |
|---|---|---|---|
| `play_store_assets/icon_512x512.png` | Google Play Store Icon | 512 x 512 px | 32-bit PNG |
| `play_store_assets/feature_graphic_1024x500.png` | Google Play Feature Graphic | 1024 x 500 px | 24-bit PNG |
| `play_store_assets/screenshot_phone_1080x1920.png` | Phone Screenshot Showcase | 1080 x 1920 px (9:16) | 24-bit PNG |
| `play_store_assets/screenshot_tablet_7inch_1200x1920.png` | 7-inch Tablet Screenshot | 1200 x 1920 px (10:16) | 24-bit PNG |
| `play_store_assets/screenshot_tablet_10inch_1920x1200.png` | 10-inch Tablet Screenshot | 1920 x 1200 px (16:10) | 24-bit PNG |

---

## 3. In-App Embedded Visual Assets

* `app/src/main/res/drawable/ic_numberoo_logo.png`: Mascot logo for adaptive icon foreground and in-app header branding.
* `app/src/main/res/drawable/img_numberoo_hero.png`: Hero visual graphic showing Numberoo with floating math items.
* `app/src/main/res/drawable/img_feature_graphic.png`: High-resolution feature banner for in-app welcome / about dialog.
* `app/src/main/res/drawable/img_screenshot_phone.png`: Phone screenshot graphic.
* `app/src/main/res/drawable/img_screenshot_tablet.png`: Tablet showcase graphic.
* Adaptive launcher icon XML drawables:
  * `app/src/main/res/drawable/ic_launcher_background.xml` (Solid `#FFB300`)
  * `app/src/main/res/drawable/ic_launcher_foreground.xml` (Safe-zone 66dp centered mascot layer)
  * Mipmap raster fallbacks (`mipmap-mdpi`, `mipmap-hdpi`, `mipmap-xhdpi`, `mipmap-xxhdpi`, `mipmap-xxxhdpi`).
