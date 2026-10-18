package com.example.ads

import android.content.Context
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.RequestConfiguration
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Centralized AdMob configuration for Numberoo.
 * Published under ViveScript Solutions LLC.
 *
 * Configured in strict compliance with Google Play Families Policy & COPPA:
 * - Target Age Groups: Ages 5 & under, Ages 6–8
 * - TAG_FOR_CHILD_DIRECTED_TREATMENT_TRUE
 * - TAG_FOR_UNDER_AGE_OF_CONSENT_TRUE
 * - MAX_AD_CONTENT_RATING_G
 * - Excludes behavioral targeting and remarketing
 */
object AdConfig {

    const val APP_ID: String = "ca-app-pub-5222053984568989~6477952392"
    const val BANNER_AD_UNIT_ID: String = "ca-app-pub-5222053984568989/7992098638"
    const val INTERSTITIAL_AD_UNIT_ID: String = "ca-app-pub-5222053984568989/1678061018"

    val bannerAdUnitId: String
        get() = BANNER_AD_UNIT_ID

    val interstitialAdUnitId: String
        get() = INTERSTITIAL_AD_UNIT_ID

    fun initialize(context: Context) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val requestConfiguration = RequestConfiguration.Builder()
                    .setTagForChildDirectedTreatment(RequestConfiguration.TAG_FOR_CHILD_DIRECTED_TREATMENT_TRUE)
                    .setTagForUnderAgeOfConsent(RequestConfiguration.TAG_FOR_UNDER_AGE_OF_CONSENT_TRUE)
                    .setMaxAdContentRating(RequestConfiguration.MAX_AD_CONTENT_RATING_G)
                    .build()

                MobileAds.setRequestConfiguration(requestConfiguration)
                MobileAds.initialize(context) { /* initialization callback */ }
            } catch (e: Exception) {
                // Graceful fallback on emulators or when adservices is unavailable
            }
        }
    }
}
