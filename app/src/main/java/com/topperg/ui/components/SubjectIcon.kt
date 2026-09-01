package com.topperg.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.Computer
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.Science
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.topperg.ui.theme.SubjectComputer
import com.topperg.ui.theme.SubjectEnglish
import com.topperg.ui.theme.SubjectHindi
import com.topperg.ui.theme.SubjectMath
import com.topperg.ui.theme.SubjectScience
import com.topperg.ui.theme.SubjectSocial

@Composable
fun SubjectIcon(
    subjectName: String,
    modifier: Modifier = Modifier,
    size: Int = 48
) {
    val (icon, color) = when {
        subjectName.contains("Math", ignoreCase = true) ->
            Icons.Default.Calculate to SubjectMath
        subjectName.contains("Science", ignoreCase = true) ||
        subjectName.contains("Physics", ignoreCase = true) ||
        subjectName.contains("Chemistry", ignoreCase = true) ||
        subjectName.contains("Biology", ignoreCase = true) ->
            Icons.Default.Science to SubjectScience
        subjectName.contains("Social", ignoreCase = true) ||
        subjectName.contains("History", ignoreCase = true) ||
        subjectName.contains("Civics", ignoreCase = true) ||
        subjectName.contains("Geography", ignoreCase = true) ->
            Icons.Default.Public to SubjectSocial
        subjectName.contains("English", ignoreCase = true) ->
            Icons.Default.Language to SubjectEnglish
        subjectName.contains("Hindi", ignoreCase = true) ||
        subjectName.contains("Sanskrit", ignoreCase = true) ->
            Icons.AutoMirrored.Filled.MenuBook to SubjectHindi
        subjectName.contains("Computer", ignoreCase = true) ||
        subjectName.contains("IT", ignoreCase = true) ->
            Icons.Default.Computer to SubjectComputer
        else -> Icons.AutoMirrored.Filled.MenuBook to MaterialTheme.colorScheme.primary
    }

    Box(
        modifier = modifier
            .size(size.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(color.copy(alpha = 0.15f)),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = subjectName,
            tint = color,
            modifier = Modifier.size((size * 0.5).dp)
        )
    }
}
