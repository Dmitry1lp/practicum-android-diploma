package ru.practicum.android.diploma.core.utils

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.input.pointer.pointerInput
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

fun Modifier.antiRepetitionClick(
    lockTimeMillis: Long = LOCK_TIME,
    onClick: () -> Unit
): Modifier = antiRepetitionClick(lockTimeMillis, Unit) { _ -> onClick() }

fun <T> Modifier.antiRepetitionClick(
    lockTimeMillis: Long = LOCK_TIME,
    param: T,
    onClick: (T) -> Unit
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
            detectTapGestures {
                if (!isLocked) {
                    onClick(param)
                    isLocked = true
                    startDelay()
                }
            }
        }
    )
}

fun Modifier.debounceClick(
    delayMillis: Long = DEBOUNCE_TIME,
    useLastParam: Boolean = true,
    onDebouncedClick: () -> Unit
): Modifier = debounceClick(delayMillis, useLastParam, Unit) { _ -> onDebouncedClick() }

fun <T> Modifier.debounceClick(
    delayMillis: Long = DEBOUNCE_TIME,
    useLastParam: Boolean = true,
    param: T,
    onDebouncedClick: (T) -> Unit
): Modifier = composed {
    val scope = rememberCoroutineScope()
    var debounceJob: Job? by remember { mutableStateOf(null) }

    DisposableEffect(Unit) {
        onDispose {
            debounceJob?.cancel()
        }
    }

    fun startDelay() {
        debounceJob = scope.launch {
            delay(delayMillis)
            onDebouncedClick(param)
        }
    }

    this.then(
        Modifier.pointerInput(Unit) {
            detectTapGestures {
                if (useLastParam) debounceJob?.cancel()
                if (debounceJob?.isCompleted == true || useLastParam) startDelay()
            }
        }
    )
}

private const val LOCK_TIME = 300L
private const val DEBOUNCE_TIME = 2000L
