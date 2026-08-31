package com.topperg.ui.navigation

sealed class Screen(val route: String) {
    // Onboarding
    object Splash : Screen("splash")
    object Permissions : Screen("permissions")
    object BoardSelection : Screen("board_selection")
    object ClassSelection : Screen("class_selection")
    object LanguageSelection : Screen("language_selection")

    // Core
    object Home : Screen("home")
    object Subject : Screen("subject/{subjectId}") {
        fun createRoute(subjectId: String) = "subject/$subjectId"
    }
    object Chapter : Screen("chapter/{chapterId}") {
        fun createRoute(chapterId: String) = "chapter/$chapterId"
    }
    object NoteReader : Screen("note_reader/{chapterId}") {
        fun createRoute(chapterId: String) = "note_reader/$chapterId"
    }
    object McqPractice : Screen("mcq_practice/{chapterId}") {
        fun createRoute(chapterId: String) = "mcq_practice/$chapterId"
    }
    object McqResult : Screen("mcq_result/{attemptId}") {
        fun createRoute(attemptId: String) = "mcq_result/$attemptId"
    }
    object TestSetup : Screen("test_setup/{subjectId}?chapterId={chapterId}") {
        fun createRoute(subjectId: String, chapterId: String? = null): String {
            return if (chapterId != null) {
                "test_setup/$subjectId?chapterId=$chapterId"
            } else {
                "test_setup/$subjectId"
            }
        }
    }
    object TestTaking : Screen("test_taking/{testSessionId}") {
        fun createRoute(testSessionId: String) = "test_taking/$testSessionId"
    }
    object TestResult : Screen("test_result/{attemptId}") {
        fun createRoute(attemptId: String) = "test_result/$attemptId"
    }
    object PreviousYearPapers : Screen("previous_year_papers")
    object PaperView : Screen("paper_view/{paperId}") {
        fun createRoute(paperId: String) = "paper_view/$paperId"
    }
    object Search : Screen("search")
    object Saved : Screen("saved")
    object ScoreHistory : Screen("score_history")
    object Settings : Screen("settings")
    object DownloadsManager : Screen("downloads_manager")
    object Auth : Screen("auth")
    object About : Screen("about")
    object PrivacyPolicy : Screen("privacy_policy")
}

object NavRoutes {
    const val ONBOARDING_GRAPH = "onboarding"
    const val MAIN_GRAPH = "main"
}
