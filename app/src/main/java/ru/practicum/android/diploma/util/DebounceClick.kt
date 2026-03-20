package ru.practicum.android.diploma.util

import androidx.compose.foundation.gestures.awaitFirstDown
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

fun Modifier.debounceClick(
    delayMillis: Long = DEBOUNCE_TIME,
    useLastParam: Boolean = true,
    onDebouncedClick: () -> Unit
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
            onDebouncedClick()
        }
    }

    this.then(
        Modifier.pointerInput(Unit) {
            awaitPointerEventScope {
                while (true) {
                    awaitFirstDown(requireUnconsumed = false)

                    if (useLastParam) debounceJob?.cancel()

                    if (shouldSkipClick(debounceJob, useLastParam)) continue
                    startDelay()
                }
            }
        }
    )
}

private fun shouldSkipClick(
    job: Job?,
    useLastParam: Boolean
): Boolean = job?.isCompleted != true && !useLastParam


private const val DEBOUNCE_TIME = 2000L
