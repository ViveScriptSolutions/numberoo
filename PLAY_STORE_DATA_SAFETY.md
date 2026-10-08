# Google Play Data Safety & Compliance Guide

**Application:** Numberoo - Learn Count Add Subtract  
**Package:** `com.vivescriptsolutions.numberoo`  
**Publisher:** ViveScript Solutions LLC  
**Target Audience:** Children (Ages 3–8), Families  

---

## 1. Google Play Data Safety Declarations

Fill out the Google Play Console Data Safety questionnaire using these factual declarations:

### Data Collection & Sharing Overview
* **Does your app collect or share any of the required user data types?**  
  👉 **No.** (The core educational app collects zero user data. All math gameplay is 100% on-device).

* **Is all of the user data collected by your app encrypted in transit?**  
  👉 **Yes.** (Any network requests initiated by Google Mobile Ads SDK for test ad delivery use HTTPS/TLS).

* **Do you provide a way for users to request that their data be deleted?**  
  👉 **Yes / Not Applicable.** (No account or remote database exists; uninstalling the app permanently purges all local shared preferences).

### Specific Data Type Declarations

| Data Category | Data Type | Collected? | Shared? | Purpose |
|---|---|---|---|---|
| **Location** | Approximate / Precise | No | No | N/A |
| **Personal Info** | Name, Email, Phone, User IDs | No | No | N/A |
| **Financial Info** | Credit card, purchase history | No | No | N/A |
| **Health & Fitness**| Fitness, health info | No | No | N/A |
| **Messages** | Emails, SMS, Chat | No | No | N/A |
| **Photos & Videos** | Photos, Videos | No | No | N/A |
| **Audio Files** | Voice recordings | No | No | N/A |
| **Files & Docs** | Documents | No | No | N/A |
| **Calendar** | Calendar events | No | No | N/A |
| **Contacts** | Contact book | No | No | N/A |
| **App Activity** | In-app searches, interactions | No | No | N/A |
| **Web Browsing** | Web history | No | No | N/A |
| **App Info / Performance** | Diagnostics, crash logs | No | No | N/A |
| **Device or other IDs** | Advertising ID | Only if AdMob is live | Ad delivery | Non-personalized, COPPA child-directed |

---

## 2. Google Play Families Policy & COPPA Compliance

* **Target Age Groups:**  
  * Ages 5 & under: ✅ Selected  
  * Ages 6–8: ✅ Selected  
* **Neutral Age Screen:** Not required as the application is directed strictly to children and does not solicit personal data.
* **Child-Directed Advertising:**  
  * Configured with `TAG_FOR_CHILD_DIRECTED_TREATMENT_TRUE` and `TAG_FOR_UNDER_AGE_OF_CONSENT_TRUE`.
  * Max Ad Content Rating locked to `MAX_AD_CONTENT_RATING_G`.
  * Ad requests exclude behavioral targeting and remarketing.
* **SDK Compliance:** Google Mobile Ads SDK is an approved Google Play certified family ad SDK.

---

## 3. Google Play Content Rating Questionnaire

Recommended responses based strictly on implemented features:

* **Violence:** No
* **Sexuality:** No
* **Language / Profanity:** No
* **Controlled Substances:** No
* **Gambling:** No
* **User Interaction / Chat:** No
* **Physical Location Sharing:** No
* **Purchase of Digital Goods:** No
* **Rating Result:** Everyone / PEGI 3 / USK 0

---

## 4. App Access Instructions for Reviewers

* **Credentials Required:** None.
* **Restricted Content:** None.
* **Reviewer Note:**  
  *"The application is an educational mathematics utility for children. No login, credentials, or special configuration is required. All learning modes (Learn, Count, Add, Subtract) are immediately accessible on launch."*
