package com.topperg.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.topperg.ui.screens.auth.AuthScreen
import com.topperg.ui.screens.chapter.ChapterScreen
import com.topperg.ui.screens.home.HomeScreen
import com.topperg.ui.screens.mcq.McqPracticeScreen
import com.topperg.ui.screens.mcq.McqResultScreen
import com.topperg.ui.screens.notes.NoteReaderScreen
import com.topperg.ui.screens.onboarding.BoardSelectionScreen
import com.topperg.ui.screens.onboarding.ClassSelectionScreen
import com.topperg.ui.screens.onboarding.LanguageSelectionScreen
import com.topperg.ui.screens.onboarding.PermissionsScreen
import com.topperg.ui.screens.onboarding.SplashScreen
import com.topperg.ui.screens.papers.PaperViewScreen
import com.topperg.ui.screens.papers.PreviousYearPapersScreen
import com.topperg.ui.screens.saved.SavedScreen
import com.topperg.ui.screens.score.ScoreHistoryScreen
import com.topperg.ui.screens.search.SearchScreen
import com.topperg.ui.screens.settings.AboutScreen
import com.topperg.ui.screens.settings.DownloadsManagerScreen
import com.topperg.ui.screens.settings.PrivacyPolicyScreen
import com.topperg.ui.screens.settings.SettingsScreen
import com.topperg.ui.screens.subject.SubjectScreen
import com.topperg.ui.screens.test.TestResultScreen
import com.topperg.ui.screens.test.TestSetupScreen
import com.topperg.ui.screens.test.TestTakingScreen
import com.topperg.viewmodel.ProfileViewModel

@Composable
fun TopperGNavGraph(
    navController: NavHostController,
    startDestination: String = Screen.Splash.route
) {
    val profileViewModel: ProfileViewModel = hiltViewModel()
    val profile by profileViewModel.profile.collectAsState()

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        // Onboarding Flow
        composable(Screen.Splash.route) {
            SplashScreen(
                onNavigateToHome = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                },
                onNavigateToOnboarding = {
                    navController.navigate(Screen.Permissions.route) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.Permissions.route) {
            PermissionsScreen(
                onNavigateNext = {
                    navController.navigate(Screen.BoardSelection.route)
                }
            )
        }

        composable(Screen.BoardSelection.route) {
            BoardSelectionScreen(
                onBoardSelected = {
                    navController.navigate(Screen.ClassSelection.route)
                }
            )
        }

        composable(Screen.ClassSelection.route) {
            ClassSelectionScreen(
                onClassSelected = {
                    navController.navigate(Screen.LanguageSelection.route)
                }
            )
        }

        composable(Screen.LanguageSelection.route) {
            LanguageSelectionScreen(
                onLanguageSelected = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                }
            )
        }

        // Core App Flow
        composable(Screen.Home.route) {
            HomeScreen(
                onSubjectClick = { subjectId ->
                    navController.navigate(Screen.Subject.createRoute(subjectId))
                },
                onPreviousYearPapersClick = {
                    navController.navigate(Screen.PreviousYearPapers.route)
                },
                onSearchClick = {
                    navController.navigate(Screen.Search.route)
                },
                onSavedClick = {
                    navController.navigate(Screen.Saved.route)
                },
                onScoreHistoryClick = {
                    navController.navigate(Screen.ScoreHistory.route)
                },
                onSettingsClick = {
                    navController.navigate(Screen.Settings.route)
                }
            )
        }

        composable(
            route = Screen.Subject.route,
            arguments = listOf(navArgument("subjectId") { type = NavType.StringType })
        ) { backStackEntry ->
            val subjectId = backStackEntry.arguments?.getString("subjectId") ?: return@composable
            SubjectScreen(
                subjectId = subjectId,
                onChapterClick = { chapterId ->
                    navController.navigate(Screen.Chapter.createRoute(chapterId))
                },
                onBackClick = { navController.popBackStack() }
            )
        }

        composable(
            route = Screen.Chapter.route,
            arguments = listOf(navArgument("chapterId") { type = NavType.StringType })
        ) { backStackEntry ->
            val chapterId = backStackEntry.arguments?.getString("chapterId") ?: return@composable
            ChapterScreen(
                chapterId = chapterId,
                onNotesClick = {
                    navController.navigate(Screen.NoteReader.createRoute(chapterId))
                },
                onMcqPracticeClick = {
                    navController.navigate(Screen.McqPractice.createRoute(chapterId))
                },
                onChapterTestClick = { subjectId ->
                    navController.navigate(Screen.TestSetup.createRoute(subjectId, chapterId))
                },
                onBackClick = { navController.popBackStack() }
            )
        }

        composable(
            route = Screen.NoteReader.route,
            arguments = listOf(navArgument("chapterId") { type = NavType.StringType })
        ) { backStackEntry ->
            val chapterId = backStackEntry.arguments?.getString("chapterId") ?: return@composable
            NoteReaderScreen(
                chapterId = chapterId,
                onBackClick = { navController.popBackStack() }
            )
        }

        composable(
            route = Screen.McqPractice.route,
            arguments = listOf(navArgument("chapterId") { type = NavType.StringType })
        ) { backStackEntry ->
            val chapterId = backStackEntry.arguments?.getString("chapterId") ?: return@composable
            McqPracticeScreen(
                chapterId = chapterId,
                onFinish = { attemptId ->
                    navController.navigate(Screen.McqResult.createRoute(attemptId)) {
                        popUpTo(Screen.McqPractice.route) { inclusive = true }
                    }
                },
                onBackClick = { navController.popBackStack() }
            )
        }

        composable(
            route = Screen.McqResult.route,
            arguments = listOf(navArgument("attemptId") { type = NavType.StringType })
        ) { backStackEntry ->
            val attemptId = backStackEntry.arguments?.getString("attemptId") ?: return@composable
            McqResultScreen(
                attemptId = attemptId,
                onRetryMissed = { chapterId ->
                    navController.navigate(Screen.McqPractice.createRoute(chapterId)) {
                        popUpTo(Screen.McqResult.route) { inclusive = true }
                    }
                },
                onBackToHome = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Home.route) { inclusive = false }
                    }
                }
            )
        }

        composable(
            route = Screen.TestSetup.route,
            arguments = listOf(
                navArgument("subjectId") { type = NavType.StringType },
                navArgument("chapterId") {
                    type = NavType.StringType
                    nullable = true
                    defaultValue = null
                }
            )
        ) { backStackEntry ->
            val subjectId = backStackEntry.arguments?.getString("subjectId") ?: return@composable
            val chapterId = backStackEntry.arguments?.getString("chapterId")
            TestSetupScreen(
                subjectId = subjectId,
                chapterId = chapterId,
                onStartTest = { testSessionId ->
                    navController.navigate(Screen.TestTaking.createRoute(testSessionId))
                },
                onBackClick = { navController.popBackStack() }
            )
        }

        composable(
            route = Screen.TestTaking.route,
            arguments = listOf(navArgument("testSessionId") { type = NavType.StringType })
        ) { backStackEntry ->
            val testSessionId = backStackEntry.arguments?.getString("testSessionId") ?: return@composable
            TestTakingScreen(
                testSessionId = testSessionId,
                onTestComplete = { attemptId ->
                    navController.navigate(Screen.TestResult.createRoute(attemptId)) {
                        popUpTo(Screen.TestTaking.route) { inclusive = true }
                    }
                }
            )
        }

        composable(
            route = Screen.TestResult.route,
            arguments = listOf(navArgument("attemptId") { type = NavType.StringType })
        ) { backStackEntry ->
            val attemptId = backStackEntry.arguments?.getString("attemptId") ?: return@composable
            TestResultScreen(
                attemptId = attemptId,
                onReviewAnswers = { /* Navigate to review */ },
                onBackToHome = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Home.route) { inclusive = false }
                    }
                }
            )
        }

        composable(Screen.PreviousYearPapers.route) {
            PreviousYearPapersScreen(
                onPaperClick = { paperId ->
                    navController.navigate(Screen.PaperView.createRoute(paperId))
                },
                onBackClick = { navController.popBackStack() }
            )
        }

        composable(
            route = Screen.PaperView.route,
            arguments = listOf(navArgument("paperId") { type = NavType.StringType })
        ) { backStackEntry ->
            val paperId = backStackEntry.arguments?.getString("paperId") ?: return@composable
            PaperViewScreen(
                paperId = paperId,
                onPracticeAsTest = { /* Navigate to test setup with paper */ },
                onBackClick = { navController.popBackStack() }
            )
        }

        composable(Screen.Search.route) {
            SearchScreen(
                onResultClick = { route -> navController.navigate(route) },
                onBackClick = { navController.popBackStack() }
            )
        }

        composable(Screen.Saved.route) {
            SavedScreen(
                onItemClick = { route -> navController.navigate(route) },
                onBackClick = { navController.popBackStack() }
            )
        }

        composable(Screen.ScoreHistory.route) {
            ScoreHistoryScreen(
                onAttemptClick = { attemptId ->
                    navController.navigate(Screen.TestResult.createRoute(attemptId))
                },
                onBackClick = { navController.popBackStack() }
            )
        }

        composable(Screen.Settings.route) {
            SettingsScreen(
                onChangeBoard = {
                    navController.navigate(Screen.BoardSelection.route)
                },
                onChangeClass = {
                    navController.navigate(Screen.ClassSelection.route)
                },
                onChangeLanguage = {
                    navController.navigate(Screen.LanguageSelection.route)
                },
                onManageDownloads = {
                    navController.navigate(Screen.DownloadsManager.route)
                },
                onLoginLogout = {
                    navController.navigate(Screen.Auth.route)
                },
                onAbout = {
                    navController.navigate(Screen.About.route)
                },
                onPrivacyPolicy = {
                    navController.navigate(Screen.PrivacyPolicy.route)
                },
                onBackClick = { navController.popBackStack() }
            )
        }

        composable(Screen.DownloadsManager.route) {
            DownloadsManagerScreen(
                onBackClick = { navController.popBackStack() }
            )
        }

        composable(Screen.Auth.route) {
            AuthScreen(
                onAuthSuccess = {
                    navController.popBackStack()
                },
                onContinueAsGuest = {
                    navController.popBackStack()
                }
            )
        }

        composable(Screen.About.route) {
            AboutScreen(
                onBackClick = { navController.popBackStack() }
            )
        }

        composable(Screen.PrivacyPolicy.route) {
            PrivacyPolicyScreen(
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}
