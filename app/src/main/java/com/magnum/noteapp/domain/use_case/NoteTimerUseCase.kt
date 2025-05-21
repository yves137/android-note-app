package com.magnum.noteapp.domain.use_case

import android.annotation.SuppressLint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import java.util.Date
import java.text.SimpleDateFormat

class NoteTimerUseCase {
    @SuppressLint("SimpleDateFormat")
    private fun noteTime(): String {
        val currentDate = Date()
        val dateFormat = SimpleDateFormat("HH:mm:ss")
        return dateFormat.format(currentDate)
    }

    operator fun invoke() = flow {
        while (true) {
            emit(noteTime())
            delay(1000)
        }
    }
}