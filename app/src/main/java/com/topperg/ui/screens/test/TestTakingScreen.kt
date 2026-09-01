package com.topperg.ui.screens.test

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.topperg.ads.AdManager
import kotlinx.coroutines.delay
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Suppress("UNUSED_PARAMETER")
@Composable
fun TestTakingScreen(
    testSessionId: String,
    onTestComplete: (String) -> Unit,
    adManager: AdManager? = null
) {
    // Hide all ads during test
    LaunchedEffect(testSessionId) {
        adManager?.setTestInProgress(true)
    }

    var questions by remember { mutableStateOf(generateTestQuestions()) }
    var currentIndex by remember { mutableIntStateOf(0) }
    var answers by remember { mutableStateOf<IntArray>(IntArray(questions.size) { -1 }) }
    var timeRemaining by remember { mutableIntStateOf(15 * 60) } // 15 minutes
    var showSubmitDialog by remember { mutableStateOf(false) }
    var showQuestionGrid by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()

    // Timer
    LaunchedEffect(Unit) {
        while (timeRemaining > 0) {
            delay(1000)
            timeRemaining--
        }
        // Auto-submit when time is up
        if (timeRemaining <= 0) {
            val attemptId = UUID.randomUUID().toString()
            adManager?.setTestInProgress(false)
            onTestComplete(attemptId)
        }
    }

    val currentQuestion = questions.getOrNull(currentIndex)
    val progress = if (questions.isNotEmpty()) (currentIndex + 1).toFloat() / questions.size else 0f
    val minutes = timeRemaining / 60
    val seconds = timeRemaining % 60
    val timerColor = if (timeRemaining < 60) Color.Red else MaterialTheme.colorScheme.onPrimary

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = "Test Mode",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            text = String.format("%02d:%02d", minutes, seconds),
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = timerColor
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { showQuestionGrid = true }) {
                        Icon(
                            imageVector = Icons.Default.GridView,
                            contentDescription = "Question Grid"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    actionIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 20.dp)
        ) {
            Spacer(modifier = Modifier.height(12.dp))

            // Progress
            LinearProgressIndicator(
                progress = { progress },
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.primary,
                trackColor = MaterialTheme.colorScheme.surfaceVariant
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Question ${currentIndex + 1} of ${questions.size}",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Question
            currentQuestion?.let { question ->
                Text(
                    text = question.question,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(bottom = 20.dp)
                )

                // Options
                val options = listOf(question.optionA, question.optionB, question.optionC, question.optionD)
                options.forEachIndexed { index, option ->
                    val optionNum = index + 1
                    val isSelected = answers[currentIndex] == optionNum

                    Card(
                        onClick = {
                            answers = answers.clone().also { it[currentIndex] = optionNum }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = if (isSelected)
                                MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
                            else
                                MaterialTheme.colorScheme.surface
                        ),
                        border = if (isSelected) {
                            androidx.compose.foundation.BorderStroke(2.dp, MaterialTheme.colorScheme.primary)
                        } else {
                            androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.3f))
                        }
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .clip(CircleShape)
                                    .background(
                                        if (isSelected) MaterialTheme.colorScheme.primary
                                        else MaterialTheme.colorScheme.surfaceVariant
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = ('A' + index).toString(),
                                    fontWeight = FontWeight.Bold,
                                    color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                            Spacer(modifier = Modifier.size(12.dp))
                            Text(
                                text = option,
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onSurface,
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.weight(1f))

                // Navigation Buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(
                        onClick = {
                            if (currentIndex > 0) currentIndex--
                        },
                        enabled = currentIndex > 0,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant,
                            contentColor = MaterialTheme.colorScheme.onSurface
                        )
                    ) {
                        Text("Previous")
                    }

                    if (currentIndex < questions.size - 1) {
                        Button(onClick = { currentIndex++ }) {
                            Text("Next")
                        }
                    } else {
                        Button(
                            onClick = { showSubmitDialog = true },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.primary
                            )
                        ) {
                            Text("Submit")
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }

    // Question Grid Bottom Sheet
    if (showQuestionGrid) {
        ModalBottomSheet(
            onDismissRequest = { showQuestionGrid = false },
            sheetState = sheetState
        ) {
            Column(modifier = Modifier.padding(24.dp)) {
                Text(
                    text = "Question Grid",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(16.dp))
                LazyVerticalGrid(
                    columns = GridCells.Fixed(5),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    itemsIndexed(questions) { index, _ ->
                        val isAnswered = answers[index] != -1
                        val isCurrent = index == currentIndex
                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .clip(CircleShape)
                                .background(
                                    when {
                                        isCurrent -> MaterialTheme.colorScheme.primary
                                        isAnswered -> MaterialTheme.colorScheme.primary.copy(alpha = 0.3f)
                                        else -> MaterialTheme.colorScheme.surfaceVariant
                                    }
                                )
                                .clickable {
                                    currentIndex = index
                                    showQuestionGrid = false
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "${index + 1}",
                                fontWeight = FontWeight.SemiBold,
                                color = if (isCurrent) Color.White else MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }

    // Submit Confirmation Dialog
    if (showSubmitDialog) {
        androidx.compose.material3.AlertDialog(
            onDismissRequest = { showSubmitDialog = false },
            title = { Text("Submit Test?") },
            text = {
                val answered = answers.count { it != -1 }
                Text("You have answered $answered out of ${questions.size} questions. Are you sure you want to submit?")
            },
            confirmButton = {
                Button(
                    onClick = {
                        val attemptId = UUID.randomUUID().toString()
                        adManager?.setTestInProgress(false)
                        onTestComplete(attemptId)
                    }
                ) {
                    Text("Submit")
                }
            },
            dismissButton = {
                Button(
                    onClick = { showSubmitDialog = false },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant
                    )
                ) {
                    Text("Cancel")
                }
            }
        )
    }
}

private fun generateTestQuestions(): List<TestQuestion> {
    return List(20) { index ->
        TestQuestion(
            id = "tq$index",
            question = "Sample test question ${index + 1}: What is the answer to this question?",
            optionA = "Option A",
            optionB = "Option B",
            optionC = "Option C",
            optionD = "Option D",
            correctOption = (index % 4) + 1
        )
    }
}

data class TestQuestion(
    val id: String,
    val question: String,
    val optionA: String,
    val optionB: String,
    val optionC: String,
    val optionD: String,
    val correctOption: Int
)
