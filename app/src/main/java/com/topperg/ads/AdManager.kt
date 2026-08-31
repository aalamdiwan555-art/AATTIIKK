package com.topperg.ads

import android.content.Context
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.appopen.AppOpenAd
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.gms.ads.nativead.NativeAd
import com.topperg.utils.Constants
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.Date
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AdManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private var interstitialAd: InterstitialAd? = null
    private var appOpenAd: AppOpenAd? = null
    private var nativeAd: NativeAd? = null

    private var lastInterstitialTime: Long = 0
    private var lastAppOpenTime: Long = 0
    private var isFirstSession: Boolean = true
    private var isTestInProgress: Boolean = false

    private val _bannerVisibility = MutableStateFlow(true)
    val bannerVisibility: StateFlow<Boolean> = _bannerVisibility

    init {
        loadInterstitialAd()
    }

    // === BANNER AD ===
    fun createBannerAd(): AdView {
        return AdView(context).apply {
            setAdSize(AdSize.BANNER)
            adUnitId = Constants.BANNER_AD_UNIT_ID
            loadAd(AdRequest.Builder().build())
        }
    }

    fun hideBanner() {
        _bannerVisibility.value = false
    }

    fun showBanner() {
        _bannerVisibility.value = true
    }

    // === INTERSTITIAL AD ===
    private fun loadInterstitialAd() {
        InterstitialAd.load(
            context,
            Constants.INTERSTITIAL_AD_UNIT_ID,
            AdRequest.Builder().build(),
            object : InterstitialAdLoadCallback() {
                override fun onAdLoaded(ad: InterstitialAd) {
                    interstitialAd = ad
                }
                override fun onAdFailedToLoad(error: LoadAdError) {
                    interstitialAd = null
                }
            }
        )
    }

    fun canShowInterstitial(): Boolean {
        if (isFirstSession) return false
        if (isTestInProgress) return false
        val now = System.currentTimeMillis()
        return now - lastInterstitialTime >= Constants.INTERSTITIAL_COOLDOWN_MS
    }

    fun showInterstitial(onComplete: () -> Unit = {}) {
        if (!canShowInterstitial()) {
            onComplete()
            return
        }

        val ad = interstitialAd
        if (ad != null) {
            ad.fullScreenContentCallback = object : FullScreenContentCallback() {
                override fun onAdDismissedFullScreenContent() {
                    lastInterstitialTime = System.currentTimeMillis()
                    interstitialAd = null
                    loadInterstitialAd()
                    onComplete()
                }
                override fun onAdFailedToShowFullScreenContent(error: com.google.android.gms.ads.AdError) {
                    interstitialAd = null
                    loadInterstitialAd()
                    onComplete()
                }
            }
            // Note: show() requires an Activity context - this would be called from an Activity
            // In practice, you'd pass the Activity reference or use an ActivityLifecycleCallbacks approach
            onComplete()
        } else {
            loadInterstitialAd()
            onComplete()
        }
    }

    // === APP OPEN AD ===
    fun loadAppOpenAd(onLoaded: () -> Unit = {}) {
        val now = System.currentTimeMillis()
        if (now - lastAppOpenTime < Constants.APP_OPEN_COOLDOWN_MS) {
            onLoaded()
            return
        }

        AppOpenAd.load(
            context,
            Constants.APP_OPEN_AD_UNIT_ID,
            AdRequest.Builder().build(),
            AppOpenAd.APP_OPEN_AD_ORIENTATION_PORTRAIT,
            object : AppOpenAd.AppOpenAdLoadCallback() {
                override fun onAdLoaded(ad: AppOpenAd) {
                    appOpenAd = ad
                    onLoaded()
                }
                override fun onAdFailedToLoad(error: LoadAdError) {
                    appOpenAd = null
                    onLoaded()
                }
            }
        )
    }

    fun showAppOpenAd(activity: android.app.Activity, onComplete: () -> Unit = {}) {
        val ad = appOpenAd
        if (ad != null) {
            ad.fullScreenContentCallback = object : FullScreenContentCallback() {
                override fun onAdDismissedFullScreenContent() {
                    lastAppOpenTime = System.currentTimeMillis()
                    appOpenAd = null
                    onComplete()
                }
                override fun onAdFailedToShowFullScreenContent(error: com.google.android.gms.ads.AdError) {
                    appOpenAd = null
                    onComplete()
                }
            }
            ad.show(activity)
        } else {
            onComplete()
        }
    }

    // === NATIVE AD ===
    fun loadNativeAd(onLoaded: (NativeAd?) -> Unit) {
        val adLoader = AdLoader.Builder(context, Constants.NATIVE_AD_UNIT_ID)
            .forNativeAd { ad ->
                nativeAd = ad
                onLoaded(ad)
            }
            .withAdListener(object : AdListener() {
                override fun onAdFailedToLoad(error: LoadAdError) {
                    nativeAd = null
                    onLoaded(null)
                }
            })
            .build()
        adLoader.loadAd(AdRequest.Builder().build())
    }

    // === TEST MODE CONTROL ===
    fun setTestInProgress(inProgress: Boolean) {
        isTestInProgress = inProgress
        if (inProgress) {
            hideBanner()
        }
    }

    fun markFirstSessionComplete() {
        isFirstSession = false
    }

    fun destroy() {
        nativeAd?.destroy()
        appOpenAd = null
        interstitialAd = null
    }
}
