package com.example.ads

import android.app.Activity
import android.content.Context
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback

/**
 * Manages interstitial ad preloading and display in strict compliance with
 * Google Play Families Policy & COPPA guidelines:
 * - Only displayed during natural app transitions (e.g., after earning a set of stars)
 * - Never displayed unexpectedly or during active child counting/math solving
 * - Frequency capped so children have a smooth, uninterrupted learning experience
 */
class InterstitialAdManager(private val context: Context) {

    private var interstitialAd: InterstitialAd? = null
    private var isLoading: Boolean = false
    private var questionsSinceLastAd: Int = 0

    companion object {
        // Minimum questions completed between interstitial ads to protect UX
        private const val AD_FREQUENCY_MILESTONE = 5
    }

    init {
        loadAd()
    }

    fun loadAd() {
        if (isLoading || interstitialAd != null) return
        isLoading = true

        val adRequest = AdRequest.Builder().build()
        InterstitialAd.load(
            context,
            AdConfig.interstitialAdUnitId,
            adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdLoaded(ad: InterstitialAd) {
                    interstitialAd = ad
                    isLoading = false
                    setupCallback(ad)
                }

                override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                    interstitialAd = null
                    isLoading = false
                }
            }
        )
    }

    private fun setupCallback(ad: InterstitialAd) {
        ad.fullScreenContentCallback = object : FullScreenContentCallback() {
            override fun onAdDismissedFullScreenContent() {
                interstitialAd = null
                questionsSinceLastAd = 0
                loadAd()
            }

            override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                interstitialAd = null
                loadAd()
            }
        }
    }

    /**
     * Records a question completed and shows an interstitial ad if a natural milestone is reached.
     * Always calls onFinished so the child's learning journey continues seamlessly.
     */
    fun onMilestoneReached(activity: Activity?, onFinished: () -> Unit) {
        questionsSinceLastAd++

        if (questionsSinceLastAd >= AD_FREQUENCY_MILESTONE && interstitialAd != null && activity != null) {
            val ad = interstitialAd
            ad?.fullScreenContentCallback = object : FullScreenContentCallback() {
                override fun onAdDismissedFullScreenContent() {
                    interstitialAd = null
                    questionsSinceLastAd = 0
                    loadAd()
                    onFinished()
                }

                override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                    interstitialAd = null
                    loadAd()
                    onFinished()
                }
            }
            ad?.show(activity)
        } else {
            onFinished()
        }
    }
}
