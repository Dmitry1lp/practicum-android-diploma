package ru.practicum.android.diploma.feature.vacancy.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.app.ui.theme.AppDimensions
import ru.practicum.android.diploma.app.ui.theme.AppTypography
import ru.practicum.android.diploma.core.domain.model.Contacts
import ru.practicum.android.diploma.core.utils.antiRepetitionClick

@Composable
fun VacancyContacts(
    contacts: Contacts,
    onPhoneClick: (String) -> Unit,
    onEmailClick: (String) -> Unit
) {
    Text(
        modifier = Modifier.padding(top = AppDimensions.paddingBig),
        text = stringResource(R.string.vacancy_contacts),
        style = AppTypography.titleMedium
    )

    Text(text = contacts.name)

    Text(
        text = contacts.email,
        modifier = Modifier.antiRepetitionClick { onEmailClick(contacts.email) }
    )

    contacts.phoneNumbers.forEach { phone ->
        Text(
            text = phone,
            modifier = Modifier.antiRepetitionClick { onPhoneClick(phone) }
        )
    }
}
