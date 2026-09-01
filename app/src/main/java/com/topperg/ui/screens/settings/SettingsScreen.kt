package com.topperg.ui.screens.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Brightness4
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Policy
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.topperg.viewmodel.ProfileViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onChangeBoard: () -> Unit,
    onChangeClass: () -> Unit,
    onChangeLanguage: () -> Unit,
    onManageDownloads: () -> Unit,
    onAbout: () -> Unit,
    onPrivacyPolicy: () -> Unit,
    onBackClick: () -> Unit,
    profileViewModel: ProfileViewModel = hiltViewModel()
) {
    val profile by profileViewModel.profile.collectAsState()
    var darkMode by remember { mutableStateOf(false) }
    var notifications by remember { mutableStateOf(true) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Settings",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.SemiBold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
        ) {
            // Profile Section
            profile?.let {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Profile",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Board: ${it.boardName}", style = MaterialTheme.typography.bodyLarge)
                    Text("Class: ${it.classLevel}", style = MaterialTheme.typography.bodyLarge)
                    Text("Language: ${it.languageName}", style = MaterialTheme.typography.bodyLarge)
                }
                HorizontalDivider()
            }

            // Account Settings
            SettingsSection(title = "Account") {
                SettingsItem(
                    icon = Icons.Default.School,
                    title = "Change Board",
                    subtitle = profile?.boardName ?: "",
                    onClick = onChangeBoard
                )
                SettingsItem(
                    icon = Icons.Default.School,
                    title = "Change Class",
                    subtitle = "Class ${profile?.classLevel ?: ""}",
                    onClick = onChangeClass
                )
                SettingsItem(
                    icon = Icons.Default.Language,
                    title = "Change Language",
                    subtitle = profile?.languageName ?: "",
                    onClick = onChangeLanguage
                )
            }

            // App Settings
            SettingsSection(title = "App Settings") {
                SettingsItem(
                    icon = Icons.Default.Download,
                    title = "Manage Downloads",
                    subtitle = "View and delete downloaded content",
                    onClick = onManageDownloads
                )
                SettingsToggleItem(
                    icon = Icons.Default.Notifications,
                    title = "Study Reminders",
                    subtitle = "Daily notification at your chosen time",
                    checked = notifications,
                    onCheckedChange = { notifications = it }
                )
                SettingsItem(
                    icon = Icons.Default.Speed,
                    title = "TTS Default Speed",
                    subtitle = "1.0x",
                    onClick = {}
                )
                SettingsToggleItem(
                    icon = Icons.Default.Brightness4,
                    title = "Dark Mode",
                    subtitle = "Toggle dark theme",
                    checked = darkMode,
                    onCheckedChange = { darkMode = it }
                )
            }

            // About
            SettingsSection(title = "About") {
                SettingsItem(
                    icon = Icons.Default.Info,
                    title = "About TopperG",
                    subtitle = "Version 1.0.0",
                    onClick = onAbout
                )
                SettingsItem(
                    icon = Icons.Default.Policy,
                    title = "Privacy Policy",
                    subtitle = "Read our privacy policy",
                    onClick = onPrivacyPolicy
                )
            }

        }
    }
}

@Composable
private fun SettingsSection(title: String, content: @Composable () -> Unit) {
    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        Text(
            text = title,
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )
        content()
    }
}

@Composable
private fun SettingsItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    subtitle: String,
    onClick: () -> Unit,
    tint: androidx.compose.ui.graphics.Color = MaterialTheme.colorScheme.onSurface
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = title,
            tint = tint,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.size(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(text = title, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Medium, color = tint)
            if (subtitle.isNotEmpty()) {
                Text(text = subtitle, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
    }
}

@Composable
private fun SettingsToggleItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    subtitle: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = title,
            tint = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.size(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(text = title, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Medium)
            Text(text = subtitle, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
        Switch(checked = checked, onCheckedChange = onCheckedChange)
    }
}
