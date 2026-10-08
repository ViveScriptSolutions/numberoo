# Design Assets Specification — Numberoo

**Publisher:** ViveScript Solutions LLC  
**Application:** Numberoo - Learn Count Add Subtract  

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

## 2. Google Play Store Assets

### 2.1 High-Resolution App Icon
* **Dimensions:** 512 x 512 px
* **Format:** 32-bit PNG (with alpha)
* **Maximum File Size:** 1024 KB
* **Design Composition:** Cute smiling baby kangaroo mascot (Numberoo) holding a colorful number 1 numeral on a solid `#FFB300` sunny amber background with subtle rounded border.
* **Adaptive Layers in App:**
  * Foreground: `@drawable/ic_numberoo_logo` (centered in 66dp safe zone)
  * Background: `@drawable/ic_launcher_background` (`#FFB300`)
  * Legacy PNG fallbacks generated across `mdpi`, `hdpi`, `xhdpi`, `xxhdpi`, and `xxxhdpi` mipmap folders.

### 2.2 Feature Graphic
* **Dimensions:** 1024 x 500 px
* **Format:** JPEG or 24-bit PNG (no alpha)
* **Maximum File Size:** 15 MB
* **Design Composition:**
  * Left: Cheerful Numberoo mascot hopping with open arms surrounded by floating colorful balloons (🎈), apples (🍎), and gold stars (⭐).
  * Right: Large friendly typography:
    * Headline: **Numberoo**
    * Sub-headline: **Learn • Count • Add • Subtract**
    * Badges: **Fun Math for Kids • Bilingual English & বাংলা**
  * Background: Soft gradient shifting from sky blue (`#E1F5FE`) to sunny warm cream (`#FFF9C4`).

### 2.3 Screenshots Plan (Phone 16:9 / 20:9)
* **Dimensions:** 1080 x 2400 px (Portrait)
* **Minimum Required:** 4 screenshots (Max 8)
* **Slide Plan:**
  1. **Screenshot 1 — Learn Numbers 0–20:**  
     * Headline: *Learn Numbers 0 to 20!*  
     * Subtitle: *Interactive cards, cheerful sounds, and bilingual words.*
  2. **Screenshot 2 — Count with Numberoo:**  
     * Headline: *Tap & Count Friendly Objects!*  
     * Subtitle: *Apples, stars, ducks, and cupcakes that bounce and pop.*
  3. **Screenshot 3 — Simple Visual Addition:**  
     * Headline: *See Numbers Join Together!*  
     * Subtitle: *Visual groups make addition easy and intuitive.*
  4. **Screenshot 4 — Simple Visual Subtraction:**  
     * Headline: *Taking Away Made Simple!*  
     * Subtitle: *Cross out objects to understand subtraction in seconds.*
  5. **Screenshot 5 — Star Celebrations:**  
     * Headline: *Celebrate Every Milestone!*  
     * Subtitle: *Earn gold stars with fun confetti animations.*

---

## 3. In-App Visual Assets
* `res/drawable/ic_numberoo_logo.png`: Mascot logo for app launcher and top brand identity.
* `res/drawable/img_numberoo_hero.png`: Hero visual graphic showing Numberoo with floating math items.
* Adaptive launcher icon XML drawables in `res/drawable/` and `res/mipmap-*/`.
