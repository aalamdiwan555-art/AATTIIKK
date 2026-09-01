package com.topperg.ui.screens.notes

import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import android.widget.Toast
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.filled.SkipPrevious
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.topperg.data.local.entity.NoteEntity
import com.topperg.ui.components.BannerAd
import com.topperg.viewmodel.ProfileViewModel
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteReaderScreen(
    chapterId: String,
    onBackClick: () -> Unit,
    profileViewModel: ProfileViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val profile by profileViewModel.profile.collectAsState()
    var note by remember { mutableStateOf<NoteEntity?>(null) }

    // TTS State
    var tts by remember { mutableStateOf<TextToSpeech?>(null) }
    var isPlaying by remember { mutableStateOf(false) }
    var currentSentenceIndex by remember { mutableIntStateOf(-1) }
    var speechRate by remember { mutableFloatStateOf(1.0f) }
    var isTtsAvailable by remember { mutableStateOf(true) }
    var showBanner by remember { mutableStateOf(true) }

    val sentences = remember(note?.content) {
        note?.content?.split(Regex("(?<=[.!?।])\\s+"))?.filter { it.isNotBlank() } ?: emptyList()
    }

    // Initialize TTS
    LaunchedEffect(profile?.languageCode) {
        tts = TextToSpeech(context) { status ->
            if (status == TextToSpeech.SUCCESS) {
                val langCode = profile?.languageCode ?: "en"
                val locale = when (langCode) {
                    "hi" -> Locale("hi", "IN")
                    "bn" -> Locale("bn", "IN")
                    "mr" -> Locale("mr", "IN")
                    "ta" -> Locale("ta", "IN")
                    "te" -> Locale("te", "IN")
                    "gu" -> Locale("gu", "IN")
                    "kn" -> Locale("kn", "IN")
                    "ml" -> Locale("ml", "IN")
                    "pa" -> Locale("pa", "IN")
                    "or" -> Locale("or", "IN")
                    "as" -> Locale("as", "IN")
                    "ur" -> Locale("ur", "IN")
                    else -> Locale.ENGLISH
                }
                val result = tts?.setLanguage(locale)
                if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                    isTtsAvailable = false
                }
                tts?.setSpeechRate(speechRate)
            } else {
                isTtsAvailable = false
            }
        }
    }

    // TTS Progress Listener
    DisposableEffect(tts) {
        tts?.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
            override fun onStart(utteranceId: String?) {}
            override fun onDone(utteranceId: String?) {
                val index = utteranceId?.toIntOrNull() ?: return
                if (index < sentences.size - 1) {
                    currentSentenceIndex = index + 1
                    speakSentence(tts, sentences, currentSentenceIndex, speechRate)
                } else {
                    isPlaying = false
                    currentSentenceIndex = -1
                    showBanner = true
                }
            }
            override fun onError(utteranceId: String?) {
                isPlaying = false
            }
        })
        onDispose {
            tts?.stop()
            tts?.shutdown()
        }
    }

    // Hide banner during TTS
    LaunchedEffect(isPlaying) {
        showBanner = !isPlaying
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = note?.title ?: "Notes",
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
                actions = {
                    IconButton(onClick = { /* Toggle bookmark */ }) {
                        Icon(
                            imageVector = Icons.Default.BookmarkBorder,
                            contentDescription = "Bookmark"
                        )
                    }
                    IconButton(onClick = { /* Share */ }) {
                        Icon(
                            imageVector = Icons.Default.Share,
                            contentDescription = "Share"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
                    actionIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        },
        bottomBar = {
            Column {
                // TTS Controls
                if (isTtsAvailable && sentences.isNotEmpty()) {
                    TtsControlBar(
                        isPlaying = isPlaying,
                        speechRate = speechRate,
                        onPlayPause = {
                            if (isPlaying) {
                                tts?.stop()
                                isPlaying = false
                            } else {
                                if (currentSentenceIndex < 0) currentSentenceIndex = 0
                                speakSentence(tts, sentences, currentSentenceIndex, speechRate)
                                isPlaying = true
                            }
                        },
                        onSpeedChange = { rate ->
                            speechRate = rate
                            tts?.setSpeechRate(rate)
                        },
                        onSkipNext = {
                            tts?.stop()
                            if (currentSentenceIndex < sentences.size - 1) {
                                currentSentenceIndex++
                                speakSentence(tts, sentences, currentSentenceIndex, speechRate)
                                isPlaying = true
                            }
                        },
                        onSkipPrevious = {
                            tts?.stop()
                            if (currentSentenceIndex > 0) {
                                currentSentenceIndex--
                                speakSentence(tts, sentences, currentSentenceIndex, speechRate)
                                isPlaying = true
                            }
                        }
                    )
                }
                if (showBanner) {
                    BannerAd()
                }
            }
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

            if (!isTtsAvailable) {
                Text(
                    text = "Text-to-Speech is not available for this language. Please install the voice pack from device settings.",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            }

            // Note Content with sentence highlighting
            if (note != null) {
                sentences.forEachIndexed { index, sentence ->
                    val isCurrentSentence = index == currentSentenceIndex && isPlaying
                    val bgColor by animateColorAsState(
                        targetValue = if (isCurrentSentence) 
                            MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)
                        else 
                            MaterialTheme.colorScheme.background,
                        label = "sentence_highlight"
                    )

                    Text(
                        text = buildAnnotatedString {
                            withStyle(
                                style = SpanStyle(
                                    background = bgColor,
                                    fontWeight = if (isCurrentSentence) FontWeight.SemiBold else FontWeight.Normal
                                )
                            ) {
                                append(sentence)
                            }
                            append(" ")
                        },
                        style = MaterialTheme.typography.bodyLarge.copy(
                            lineHeight = 28.sp,
                            fontSize = 17.sp
                        ),
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.padding(vertical = 2.dp)
                    )
                }
            } else {
                Text(
                    text = "Loading notes...",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

private fun speakSentence(tts: TextToSpeech?, sentences: List<String>, index: Int, rate: Float) {
    if (index in sentences.indices) {
        tts?.setSpeechRate(rate)
        tts?.speak(sentences[index], TextToSpeech.QUEUE_FLUSH, null, index.toString())
    }
}

@Composable
private fun TtsControlBar(
    isPlaying: Boolean,
    speechRate: Float,
    onPlayPause: () -> Unit,
    onSpeedChange: (Float) -> Unit,
    onSkipNext: () -> Unit,
    onSkipPrevious: () -> Unit
) {
    var showSpeedSlider by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        if (showSpeedSlider) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Speed,
                    contentDescription = "Speed",
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.size(8.dp))
                Slider(
                    value = speechRate,
                    onValueChange = onSpeedChange,
                    valueRange = 0.5f..1.5f,
                    steps = 3,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = "${speechRate}x",
                    style = MaterialTheme.typography.labelMedium,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onSkipPrevious) {
                Icon(Icons.Default.SkipPrevious, "Previous")
            }

            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primary)
                    .clickable { onPlayPause() },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                    contentDescription = if (isPlaying) "Pause" else "Play",
                    tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.size(28.dp)
                )
            }

            IconButton(onClick = onSkipNext) {
                Icon(Icons.Default.SkipNext, "Next")
            }

            IconButton(onClick = { showSpeedSlider = !showSpeedSlider }) {
                Icon(Icons.Default.Speed, "Speed")
            }
        }
    }
}
