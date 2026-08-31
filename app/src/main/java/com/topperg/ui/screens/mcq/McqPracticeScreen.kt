package com.topperg.ui.screens.mcq

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import androidx.hilt.navigation.compose.hiltViewModel
import com.topperg.data.local.entity.McqEntity
import com.topperg.ui.theme.Error
import com.topperg.ui.theme.Success
import com.topperg.viewmodel.ProfileViewModel
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun McqPracticeScreen(
    chapterId: String,
    onFinish: (String) -> Unit,
    onBackClick: () -> Unit,
    profileViewModel: ProfileViewModel = hiltViewModel()
) {
    val profile by profileViewModel.profile.collectAsState()
    var questions by remember { mutableStateOf<List<McqEntity>>(emptyList()) }
    var currentIndex by remember { mutableIntStateOf(0) }
    var selectedOption by remember { mutableIntStateOf(0) }
    var hasAnswered by remember { mutableStateOf(false) }
    var correctCount by remember { mutableIntStateOf(0) }
    var wrongCount by remember { mutableIntStateOf(0) }
    var answeredQuestions by remember { mutableStateOf(mutableSetOf<Int>()) }
    var missedQuestions by remember { mutableStateOf(mutableListOf<McqEntity>()) }

    // Load questions (mock data for now)
    LaunchedEffect(chapterId) {
        questions = generateMockQuestions(chapterId)
    }

    val currentQuestion = questions.getOrNull(currentIndex)
    val progress = if (questions.isNotEmpty()) (currentIndex + 1).toFloat() / questions.size else 0f

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "MCQ Practice",
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
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            // Progress
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Question ${currentIndex + 1} of ${questions.size}",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = "Score: $correctCount",
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.primary
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            LinearProgressIndicator(
                progress = { progress },
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.primary,
                trackColor = MaterialTheme.colorScheme.surfaceVariant
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Progress Dots
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                contentPadding = PaddingValues(vertical = 8.dp)
            ) {
                itemsIndexed(questions) { index, _ ->
                    val dotColor = when {
                        index == currentIndex -> MaterialTheme.colorScheme.primary
                        answeredQuestions.contains(index) -> Success
                        else -> MaterialTheme.colorScheme.surfaceVariant
                    }
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .clip(CircleShape)
                            .background(dotColor)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

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
                    val isSelected = selectedOption == optionNum
                    val isCorrect = question.correctOption == optionNum
                    val showCorrect = hasAnswered && isCorrect
                    val showWrong = hasAnswered && isSelected && !isCorrect

                    val backgroundColor = when {
                        showCorrect -> Success.copy(alpha = 0.15f)
                        showWrong -> Error.copy(alpha = 0.15f)
                        isSelected -> MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
                        else -> MaterialTheme.colorScheme.surface
                    }
                    val borderColor = when {
                        showCorrect -> Success
                        showWrong -> Error
                        isSelected -> MaterialTheme.colorScheme.primary
                        else -> MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
                    }

                    Card(
                        onClick = {
                            if (!hasAnswered) {
                                selectedOption = optionNum
                                hasAnswered = true
                                answeredQuestions.add(currentIndex)
                                if (optionNum == question.correctOption) {
                                    correctCount++
                                } else {
                                    wrongCount++
                                    missedQuestions.add(question)
                                }
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = backgroundColor),
                        border = androidx.compose.foundation.BorderStroke(1.5.dp, borderColor)
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
                                        when {
                                            showCorrect -> Success
                                            showWrong -> Error
                                            isSelected -> MaterialTheme.colorScheme.primary
                                            else -> MaterialTheme.colorScheme.surfaceVariant
                                        }
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = ('A' + index).toString(),
                                    fontWeight = FontWeight.Bold,
                                    color = when {
                                        showCorrect || showWrong || isSelected -> Color.White
                                        else -> MaterialTheme.colorScheme.onSurfaceVariant
                                    }
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

                // Explanation
                AnimatedVisibility(
                    visible = hasAnswered,
                    enter = fadeIn() + expandVertically()
                ) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 12.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.secondaryContainer
                        )
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = if (selectedOption == question.correctOption) "✓ Correct!" else "✗ Incorrect",
                                fontWeight = FontWeight.Bold,
                                color = if (selectedOption == question.correctOption) Success else Error
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Explanation: ${question.explanation}",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSecondaryContainer
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.weight(1f))

                // Next / Finish Button
                if (hasAnswered) {
                    val isLastQuestion = currentIndex == questions.size - 1
                    androidx.compose.material3.Button(
                        onClick = {
                            if (isLastQuestion) {
                                val attemptId = UUID.randomUUID().toString()
                                // Save attempt and navigate
                                onFinish(attemptId)
                            } else {
                                currentIndex++
                                selectedOption = 0
                                hasAnswered = false
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(
                            text = if (isLastQuestion) "Finish Practice" else "Next Question",
                            modifier = Modifier.padding(vertical = 8.dp),
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}

private fun generateMockQuestions(chapterId: String): List<McqEntity> {
    return listOf(
        McqEntity(
            id = "q1_$chapterId", chapterId = chapterId, subjectId = "sub1", boardId = "cbse",
            classLevel = 10, languageCode = "en",
            question = "What is the chemical formula of water?",
            optionA = "H2O", optionB = "CO2", optionC = "NaCl", optionD = "O2",
            correctOption = 1,
            explanation = "Water is composed of two hydrogen atoms and one oxygen atom, giving it the chemical formula H2O."
        ),
        McqEntity(
            id = "q2_$chapterId", chapterId = chapterId, subjectId = "sub1", boardId = "cbse",
            classLevel = 10, languageCode = "en",
            question = "Which gas is most abundant in Earth's atmosphere?",
            optionA = "Oxygen", optionB = "Carbon Dioxide", optionC = "Nitrogen", optionD = "Hydrogen",
            correctOption = 3,
            explanation = "Nitrogen makes up about 78% of Earth's atmosphere, making it the most abundant gas."
        ),
        McqEntity(
            id = "q3_$chapterId", chapterId = chapterId, subjectId = "sub1", boardId = "cbse",
            classLevel = 10, languageCode = "en",
            question = "What is the powerhouse of the cell?",
            optionA = "Nucleus", optionB = "Mitochondria", optionC = "Ribosome", optionD = "Chloroplast",
            correctOption = 2,
            explanation = "Mitochondria are known as the powerhouse of the cell because they generate ATP through cellular respiration."
        ),
        McqEntity(
            id = "q4_$chapterId", chapterId = chapterId, subjectId = "sub1", boardId = "cbse",
            classLevel = 10, languageCode = "en",
            question = "Which planet is known as the Red Planet?",
            optionA = "Venus", optionB = "Jupiter", optionC = "Mars", optionD = "Saturn",
            correctOption = 3,
            explanation = "Mars is called the Red Planet due to iron oxide (rust) on its surface, which gives it a reddish appearance."
        ),
        McqEntity(
            id = "q5_$chapterId", chapterId = chapterId, subjectId = "sub1", boardId = "cbse",
            classLevel = 10, languageCode = "en",
            question = "What is the speed of light in vacuum?",
            optionA = "3 x 10^8 m/s", optionB = "3 x 10^6 m/s", optionC = "3 x 10^10 m/s", optionD = "3 x 10^4 m/s",
            correctOption = 1,
            explanation = "The speed of light in vacuum is approximately 3 x 10^8 meters per second (299,792,458 m/s)."
        )
    )
}
