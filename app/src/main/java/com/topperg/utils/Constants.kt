package com.topperg.utils

object Constants {
    const val MIN_CLASS = 1
    const val MAX_CLASS = 12

    val LANGUAGES = listOf(
        Language("en", "English", "English"),
        Language("hi", "Hindi", "हिन्दी"),
        Language("mr", "Marathi", "मराठी"),
        Language("bn", "Bengali", "বাংলা"),
        Language("ta", "Tamil", "தமிழ்"),
        Language("te", "Telugu", "తెలుగు"),
        Language("gu", "Gujarati", "ગુજરાતી"),
        Language("kn", "Kannada", "ಕನ್ನಡ"),
        Language("ml", "Malayalam", "മലയാളം"),
        Language("pa", "Punjabi", "ਪੰਜਾਬੀ"),
        Language("or", "Odia", "ଓଡ଼ିଆ"),
        Language("as", "Assamese", "অসমীয়া"),
        Language("ur", "Urdu", "اردو")
    )

    // Ad Unit IDs (Test IDs - replace with production before release)
    const val ADMOB_APP_ID = "ca-app-pub-3940256099942544~3347511713"
    const val BANNER_AD_UNIT_ID = "ca-app-pub-3940256099942544/6300978111"
    const val INTERSTITIAL_AD_UNIT_ID = "ca-app-pub-3940256099942544/1033173712"
    const val APP_OPEN_AD_UNIT_ID = "ca-app-pub-3940256099942544/9257395921"
    const val NATIVE_AD_UNIT_ID = "ca-app-pub-3940256099942544/2247696110"

    // Interstitial frequency cap (milliseconds)
    const val INTERSTITIAL_COOLDOWN_MS = 10 * 60 * 1000L // 10 minutes

    // App Open Ad frequency cap
    const val APP_OPEN_COOLDOWN_MS = 60 * 60 * 1000L // 1 hour
}

data class Language(
    val code: String,
    val name: String,
    val nativeName: String
)
