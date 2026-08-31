package com.topperg.ui.screens.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrivacyPolicyScreen(onBackClick: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Privacy Policy",
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
                .padding(horizontal = 20.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Privacy Policy",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Last updated: January 2024",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(24.dp))

            PolicySection("1. Information We Collect") {
                Text("We collect minimal information necessary to provide our services:")
                Text("• Board, Class, and Language preferences")
                Text("• Test scores and practice history (stored locally)")
                Text("• Email or phone number (only if you create an account)")
            }

            PolicySection("2. How We Use Your Information") {
                Text("Your information is used to:")
                Text("• Personalize study content for your board and class")
                Text("• Track your learning progress")
                Text("• Sync data across devices (if logged in)")
            }

            PolicySection("3. Data Storage") {
                Text("Most of your data is stored locally on your device. If you create an account, your data is synced to our secure servers.")
            }

            PolicySection("4. Children's Privacy") {
                Text("This app is designed for students. We comply with COPPA and India's DPDP Act. We do not knowingly collect personal information from children under 13 without parental consent.")
            }

            PolicySection("5. Contact Us") {
                Text("If you have any questions about this Privacy Policy, please contact us at support@topperg.app")
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
private fun PolicySection(title: String, content: @Composable () -> Unit) {
    Column(modifier = Modifier.padding(vertical = 12.dp)) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(8.dp))
        Column {
            content()
        }
    }
}
