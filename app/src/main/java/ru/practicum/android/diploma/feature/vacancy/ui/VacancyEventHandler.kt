package ru.practicum.android.diploma.feature.vacancy.ui

import android.content.Context
import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.core.net.toUri
import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.feature.vacancy.presentation.VacancyDetailsUiEvent

@Composable
fun ObserveVacancyEvents(
    events: Flow<VacancyDetailsUiEvent>,
    context: Context
) {
    LaunchedEffect(Unit) {
        events.collect { event ->
            handleVacancyEvent(event, context)
        }
    }
}

private fun handleVacancyEvent(
    event: VacancyDetailsUiEvent,
    context: Context
) {
    when (event) {
        is VacancyDetailsUiEvent.ShareVacancyLink -> {
            val intent = Intent(Intent.ACTION_SEND).apply {
                putExtra(Intent.EXTRA_TEXT, event.url)
                type = "text/plain"
            }
            context.startActivity(Intent.createChooser(intent, null))
        }

        is VacancyDetailsUiEvent.OpenEmailTo -> {
            val intent = Intent(Intent.ACTION_SENDTO).apply {
                data = "mailto:${event.email}".toUri()
            }
            context.startActivity(intent)
        }

        is VacancyDetailsUiEvent.OpenPhoneCall -> {
            val intent = Intent(Intent.ACTION_DIAL).apply {
                data = "tel:${event.phone}".toUri()
            }
            context.startActivity(intent)
        }
    }
}
