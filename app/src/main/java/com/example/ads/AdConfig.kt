package com.example.ads

import android.content.Context
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.RequestConfiguration

/**
 * Centralized AdMob configuration for Numberoo.
 * Published under ViveScript Solutions LLC.
 *
 * Current configuration uses Google's official AdMob test identifiers.
 * When preparing for production release, replace the test IDs with your verified
 * ViveScript production AdMob App ID and Ad Unit IDs below.
 */
object AdConfig {

    /**
     * Set to false when compiling the production release build with registered AdMob IDs.
     */
    const val IS_TEST_MODE: Boolean = true

    /**
     * Google Mobile Ads Official Test App ID:
     * ca-app-pub-3940256099942544~3347511713
     */
    const val TEST_APP_ID: String = "ca-app-pub-3940256099942544~3347511713"

    /**
     * Google Mobile Ads Official Test Banner Ad Unit ID:
     * ca-app-pub-3940256099942544/6300978111
     */
    const val TEST_BANNER_AD_UNIT_ID: String = "ca-app-pub-3940256099942544/6300978111"

    /**
     * Production AdMob Banner Ad Unit ID placeholder.
     * Replace with production ID issued by Google AdMob console:
     */
    private const val PROD_BANNER_AD_UNIT_ID: String = "ca-app-pub-XXXXXXXXXXXXXXXX/YYYYYYYYYY"

    /**
     * Returns the active Banner Ad Unit ID depending on build configuration.
     */
    val bannerAdUnitId: String
        get() = if (IS_TEST_MODE) TEST_BANNER_AD_UNIT_ID else PROD_BANNER_AD_UNIT_ID

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
        MobileAds.initialize(context) { /* initialization status callback */ }
    }
}
