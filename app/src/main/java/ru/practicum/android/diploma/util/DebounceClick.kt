package ru.practicum.android.diploma.util

import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.input.pointer.pointerInput
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

fun Modifier.debounceClick(
    coroutineScope: CoroutineScope,
    delayMillis: Long = DEBOUNCE_TIME,
    useLastParam: Boolean = true,
    onDebouncedClick: () -> Unit
): Modifier = composed {
    var debounceJob: Job? by remember { mutableStateOf(null) }

    this.then(
        Modifier.pointerInput(Unit) {
            awaitPointerEventScope {
                while (true) {
                    awaitFirstDown(requireUnconsumed = false)

                    if (useLastParam) {
                        debounceJob?.cancel()
                    }

                    if (debounceJob?.isCompleted == true || useLastParam) {
                        debounceJob = coroutineScope.launch {
                            delay(delayMillis)
                            onDebouncedClick()
                        }
                    }
                }
            }
        }
    )
}

private const val DEBOUNCE_TIME = 2000L
