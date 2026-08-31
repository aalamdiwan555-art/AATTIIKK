package com.topperg.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.topperg.data.local.entity.SubjectEntity
import com.topperg.ui.components.BottomNavBar
import com.topperg.ui.components.SubjectIcon
import com.topperg.ui.navigation.Screen
import com.topperg.viewmodel.ProfileViewModel
import com.topperg.viewmodel.SubjectViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onSubjectClick: (String) -> Unit,
    onPreviousYearPapersClick: () -> Unit,
    onSearchClick: () -> Unit,
    onSavedClick: () -> Unit,
    onScoreHistoryClick: () -> Unit,
    onSettingsClick: () -> Unit,
    profileViewModel: ProfileViewModel = hiltViewModel(),
    subjectViewModel: SubjectViewModel = hiltViewModel()
) {
    val profile by profileViewModel.profile.collectAsState()
    val subjects by subjectViewModel.subjects.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = "TopperG",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )
                        profile?.let {
                            Text(
                                text = "${it.boardName} | Class ${it.classLevel} | ${it.languageName}",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.8f)
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                ),
                actions = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search",
                        tint = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier
                            .padding(end = 16.dp)
                            .clickable { onSearchClick() }
                    )
                }
            )
        },
        bottomBar = {
            BottomNavBar(
                currentRoute = Screen.Home.route,
                onItemClick = { route ->
                    when (route) {
                        Screen.Search.route -> onSearchClick()
                        Screen.Saved.route -> onSavedClick()
                        Screen.ScoreHistory.route -> onScoreHistoryClick()
                        Screen.Settings.route -> onSettingsClick()
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            // Quick Access Row
            QuickAccessRow(
                onContinueStudying = { /* Navigate to last studied */ },
                onDailyStreak = { /* Show streak info */ },
                onPreviousYearPapers = onPreviousYearPapersClick
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Subjects Header
            Text(
                text = "Your Subjects",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(12.dp))

            // Subject Grid
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(bottom = 16.dp)
            ) {
                items(subjects) { subject ->
                    SubjectCard(
                        subject = subject,
                        onClick = { onSubjectClick(subject.id) }
                    )
                }
            }
        }
    }
}

@Composable
private fun QuickAccessRow(
    onContinueStudying: () -> Unit,
    onDailyStreak: () -> Unit,
    onPreviousYearPapers: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        QuickAccessCard(
            icon = Icons.Default.School,
            title = "Continue",
            subtitle = "Studying",
            modifier = Modifier.weight(1f),
            onClick = onContinueStudying
        )
        QuickAccessCard(
            icon = Icons.Default.LocalFireDepartment,
            title = "Daily",
            subtitle = "Streak",
            modifier = Modifier.weight(1f),
            onClick = onDailyStreak
        )
        QuickAccessCard(
            icon = Icons.Default.Book,
            title = "Previous",
            subtitle = "Year Papers",
            modifier = Modifier.weight(1f),
            onClick = onPreviousYearPapers
        )
    }
}

@Composable
private fun QuickAccessCard(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    subtitle: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = subtitle,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun SubjectCard(
    subject: SubjectEntity,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            SubjectIcon(
                subjectName = subject.name,
                size = 48
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = subject.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = subject.nameLocal,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}
