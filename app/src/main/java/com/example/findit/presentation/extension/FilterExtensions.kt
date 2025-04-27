package com.example.findit.presentation.extension

import com.example.findit.presentation.model.PostPresentation
import java.util.Calendar

fun PostPresentation.isLost(): Boolean {
    return this.postType.name.equals("LOST", ignoreCase = true)
}

fun PostPresentation.isFound(): Boolean {
    return this.postType.name.equals("FOUND", ignoreCase = true)
}



fun PostPresentation.isToday(): Boolean {
    val postCalendar = getPostCalendar()
    val today = Calendar.getInstance()
    return postCalendar.get(Calendar.YEAR) == today.get(Calendar.YEAR) &&
            postCalendar.get(Calendar.DAY_OF_YEAR) == today.get(Calendar.DAY_OF_YEAR)
}

fun PostPresentation.isThisWeek(): Boolean {
    val postCalendar = getPostCalendar()
    val today = Calendar.getInstance()
    return postCalendar.get(Calendar.YEAR) == today.get(Calendar.YEAR) &&
            postCalendar.get(Calendar.WEEK_OF_YEAR) == today.get(Calendar.WEEK_OF_YEAR)
}

fun PostPresentation.isThisMonth(): Boolean {
    val postCalendar = getPostCalendar()
    val today = Calendar.getInstance()
    return postCalendar.get(Calendar.YEAR) == today.get(Calendar.YEAR) &&
            postCalendar.get(Calendar.MONTH) == today.get(Calendar.MONTH)
}


fun PostPresentation.getPostCalendar(): Calendar {
    val calendar = Calendar.getInstance()
    val timestampMillis = this.timestamp.toLongOrNull() ?: 0L
    calendar.timeInMillis = timestampMillis
    return calendar
}
