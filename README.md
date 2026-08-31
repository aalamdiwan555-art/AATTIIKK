# TopperG

An offline-first Android study app for Indian school students (Class 1-12), supporting state boards and regional languages without requiring an online account or backend service.

## Features

- **Multi-Board Support**: CBSE, ICSE, and all major state boards
- **Multi-Language**: Hindi, English, Marathi, Bengali, Tamil, Telugu, Gujarati, Kannada, Malayalam, Punjabi, Odia, Assamese, Urdu
- **Study Notes**: Board-specific chapter-wise notes with TTS
- **MCQ Practice**: Instant feedback with explanations
- **Test Mode**: Timed tests with auto-grading
- **Previous Year Papers**: Filterable by year and subject
- **Score History**: Track performance over time
- **Offline Mode**: Local Room database for studying without an account or connection
- **Dark Mode**: Full dark theme support
- **Ad-Supported**: AdMob integration with user-friendly placement rules

## Tech Stack

- Kotlin
- Jetpack Compose
- Room (Local DB)
- Hilt (DI)
- AdMob (Ads)

## Setup

1. Clone the repository.
2. Open the project in Android Studio.
3. Sync the project with Gradle.
4. Run it on an emulator or Android device.

No Supabase project, authentication credentials, or repository secrets are required. All app data is stored locally on the device.

## GitHub APK builds

The workflow in `.github/workflows/android.yml` builds a debug APK whenever changes are pushed to `main`, and it can also be started manually from the repository Actions tab. After the run finishes, download `TopperG-debug-apk` from the workflow run Artifacts section.
