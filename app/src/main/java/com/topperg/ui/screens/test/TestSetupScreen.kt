package com.topperg.ui.screens.test

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TestSetupScreen(
    subjectId: String,
    chapterId: String?,
    onStartTest: (String) -> Unit,
    onBackClick: () -> Unit
) {
    var selectedScope by remember { mutableStateOf(TestScope.SINGLE_CHAPTER) }
    var durationMinutes by remember { mutableIntStateOf(15) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Test Setup",
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
                .padding(horizontal = 24.dp)
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            // Scope Selection
            Text(
                text = "Select Test Scope",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(16.dp))

            TestScopeCard(
                scope = TestScope.SINGLE_CHAPTER,
                title = "Single Chapter",
                description = "Test on the current chapter only",
                selected = selectedScope == TestScope.SINGLE_CHAPTER,
                onClick = { selectedScope = TestScope.SINGLE_CHAPTER }
            )
            Spacer(modifier = Modifier.height(8.dp))
            TestScopeCard(
                scope = TestScope.FULL_SUBJECT,
                title = "Full Subject",
                description = "Test covering all chapters",
                selected = selectedScope == TestScope.FULL_SUBJECT,
                onClick = { selectedScope = TestScope.FULL_SUBJECT }
            )
            Spacer(modifier = Modifier.height(8.dp))
            TestScopeCard(
                scope = TestScope.CUSTOM,
                title = "Custom Selection",
                description = "Choose multiple chapters",
                selected = selectedScope == TestScope.CUSTOM,
                onClick = { selectedScope = TestScope.CUSTOM }
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Duration Selection
            Text(
                text = "Test Duration",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Timer,
                    contentDescription = "Duration",
                    tint = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "$durationMinutes minutes",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
            }
            Slider(
                value = durationMinutes.toFloat(),
                onValueChange = { durationMinutes = it.toInt() },
                valueRange = 5f..60f,
                steps = 10,
                modifier = Modifier.padding(vertical = 8.dp)
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("5 min", style = MaterialTheme.typography.labelSmall)
                Text("60 min", style = MaterialTheme.typography.labelSmall)
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    val testSessionId = UUID.randomUUID().toString()
                    onStartTest(testSessionId)
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Text(
                    "Start Test",
                    modifier = Modifier.padding(vertical = 8.dp),
                    fontWeight = FontWeight.SemiBold
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

enum class TestScope {
    SINGLE_CHAPTER, FULL_SUBJECT, CUSTOM
}

@Composable
private fun TestScopeCard(
    scope: TestScope,
    title: String,
    description: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (selected)
                MaterialTheme.colorScheme.primaryContainer
            else
                MaterialTheme.colorScheme.surfaceVariant
        ),
        border = if (selected) {
            androidx.compose.foundation.BorderStroke(2.dp, MaterialTheme.colorScheme.primary)
        } else null
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(
                selected = selected,
                onClick = onClick
            )
            Spacer(modifier = Modifier.height(12.dp))
            Column {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}
