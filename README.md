# TopperG

An Android study app for Indian school students (Class 1-12) supporting all state boards and regional languages.

## Features

- **Multi-Board Support**: CBSE, ICSE, and all major state boards
- **Multi-Language**: Hindi, English, Marathi, Bengali, Tamil, Telugu, Gujarati, Kannada, Malayalam, Punjabi, Odia, Assamese, Urdu
- **Study Notes**: Board-specific chapter-wise notes with TTS
- **MCQ Practice**: Instant feedback with explanations
- **Test Mode**: Timed tests with auto-grading
- **Previous Year Papers**: Filterable by year and subject
- **Score History**: Track performance over time
- **Offline Mode**: Download content for offline study
- **Dark Mode**: Full dark theme support
- **Ad-Supported**: AdMob integration with user-friendly placement rules

## Tech Stack

- Kotlin
- Jetpack Compose
- Room (Local DB)
- Supabase (Backend)
- Hilt (DI)
- AdMob (Ads)

## Setup

1. Clone the repository
2. For a local build, add your Supabase credentials to `local.properties` (this file is ignored by Git):
   ```
   SUPABASE_URL=https://your-project.supabase.co
   SUPABASE_KEY=your-anon-key
   ```
3. Sync project with Gradle
4. Run on emulator or device

## GitHub APK builds

The repository includes `android-workflow.yml`, a ready-to-use GitHub Actions workflow template. To enable automatic APK builds, copy it to `.github/workflows/android.yml` using GitHub's web editor, or authorize the GitHub connection with workflow-file permission.

The workflow builds a debug APK and publishes it as a downloadable artifact named `TopperG-debug-apk`.

For live Supabase connectivity in GitHub Actions, add repository Actions secrets named `SUPABASE_URL` and `SUPABASE_KEY`. If they are not configured, the build uses safe placeholder values and remote content will not load until the values are supplied.
