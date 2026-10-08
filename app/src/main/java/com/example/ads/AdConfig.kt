package com.example.ads

import android.content.Context
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.RequestConfiguration

/**
 * Centralized AdMob configuration for Numberoo - Learn Count Add Subtract.
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

    /**
     * Active Application ID
     */
    const val APP_ID: String = "ca-app-pub-5222053984568989~6477952392"

    /**
     * Active Banner Ad Unit ID
     */
    const val BANNER_AD_UNIT_ID: String = "ca-app-pub-5222053984568989/7992098638"

    /**
     * Active Interstitial Ad Unit ID
     */
    const val INTERSTITIAL_AD_UNIT_ID: String = "ca-app-pub-5222053984568989/1678061018"

    /**
     * Returns the active Banner Ad Unit ID
     */
    val bannerAdUnitId: String
        get() = BANNER_AD_UNIT_ID

    /**
     * Returns the active Interstitial Ad Unit ID
     */
    val interstitialAdUnitId: String
        get() = INTERSTITIAL_AD_UNIT_ID

    /**
     * Initializes Google Mobile Ads SDK with COPPA & Google Play Families policy settings.
     * Strictly tags all ad requests as child-directed with maximum rating G.
     */
    fun initialize(context: Context) {
        val requestConfiguration = RequestConfiguration.Builder()
            .setTagForChildDirectedTreatment(RequestConfiguration.TAG_FOR_CHILD_DIRECTED_TREATMENT_TRUE)
            .setTagForUnderAgeOfConsent(RequestConfiguration.TAG_FOR_UNDER_AGE_OF_CONSENT_TRUE)
            .setMaxAdContentRating(RequestConfiguration.MAX_AD_CONTENT_RATING_G)
            .build()

        MobileAds.setRequestConfiguration(requestConfiguration)
        MobileAds.initialize(context) { /* initialization callback */ }
    }
}
