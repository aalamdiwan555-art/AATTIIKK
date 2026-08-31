package com.topperg.utils

import android.content.Context
import android.widget.Toast
import androidx.compose.ui.graphics.Color

fun String.toColor(): Color {
    return try {
        Color(android.graphics.Color.parseColor(this))
    } catch (e: Exception) {
        Color.Gray
    }
}

fun Context.showToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, duration).show()
}

fun Int.padWithZero(): String = this.toString().padStart(2, '0')
