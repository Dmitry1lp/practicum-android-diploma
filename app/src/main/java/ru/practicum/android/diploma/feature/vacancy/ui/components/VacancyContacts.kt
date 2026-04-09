package ru.practicum.android.diploma.feature.vacancy.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.app.ui.theme.AppDimensions
import ru.practicum.android.diploma.app.ui.theme.AppTypography
import ru.practicum.android.diploma.app.ui.theme.Blue
import ru.practicum.android.diploma.core.domain.model.Contacts
import ru.practicum.android.diploma.core.utils.antiRepetitionClick

@Composable
fun VacancyContacts(
    contacts: Contacts,
    onPhoneClick: (String) -> Unit,
    onEmailClick: (String) -> Unit
) {
    Column(
        modifier = Modifier.padding(top = AppDimensions.paddingBig),
        verticalArrangement = Arrangement.spacedBy(AppDimensions.paddingSmall)
    ) {
        Text(
            text = stringResource(R.string.vacancy_contacts),
            style = AppTypography.titleMedium
        )

        Text(
            text = contacts.name,
            style = AppTypography.bodyMedium
        )

        if (contacts.email.isNotBlank()) {
            ContactsView(
                text = contacts.email,
                painter = painterResource(R.drawable.ic_outline_attach_email_24),
                onClick = { onEmailClick(contacts.email) }
            )
        }

        contacts.phoneNumbers.forEach { phone ->
            ContactsView(
                text = phone,
                painter = painterResource(R.drawable.ic_outline_call_24),
                onClick = { onPhoneClick(phone) }
            )
        }
    }
}

@Composable
fun ContactsView(
    text: String,
    painter: Painter,
    onClick: () -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(AppDimensions.paddingSmall)
    ) {
        Icon(
            painter = painter,
            contentDescription = null
        )
        Text(
            text = text,
            color = Blue,
            modifier = Modifier.antiRepetitionClick { onClick() }
        )
    }
}
