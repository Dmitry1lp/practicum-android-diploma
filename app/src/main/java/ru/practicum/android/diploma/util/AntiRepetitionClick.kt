package ru.practicum.android.diploma.util

import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.input.pointer.pointerInput
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

fun Modifier.antiRepetitionClick(
    lockTimeMillis: Long = LOCK_TIME,
    onClick: () -> Unit
): Modifier = composed {
    val scope = rememberCoroutineScope()
    var isLocked by remember { mutableStateOf(false) }

    fun startDelay() {
        scope.launch {
            delay(lockTimeMillis)
            isLocked = false
        }
    }

    this.then(
        Modifier.pointerInput(Unit) {
            awaitPointerEventScope {
                while (true) {
                    awaitFirstDown(requireUnconsumed = false)

                    if (isLocked) continue

                    onClick()
                    isLocked = true
                    startDelay()
                }
            }
        }
    )
}

private const val LOCK_TIME = 300L
