package ru.practicum.android.diploma.feature.vacancy.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
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

        Row(
            horizontalArrangement = Arrangement.spacedBy(AppDimensions.paddingSmall)
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_outline_attach_email_24),
                contentDescription = null
            )

            Text(
                text = contacts.email,
                style = AppTypography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.primary
                ),
                modifier = Modifier.antiRepetitionClick { onEmailClick(contacts.email) }
            )
        }
        contacts.phoneNumbers.forEach { phone ->
            Row(
                horizontalArrangement = Arrangement.spacedBy(AppDimensions.paddingSmall)
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_outline_call_24),
                    contentDescription = null
                )
                Text(
                    text = phone,
                    style = AppTypography.bodyMedium.copy(
                        color = MaterialTheme.colorScheme.primary
                    ),
                    modifier = Modifier.antiRepetitionClick { onPhoneClick(phone) }
                )
            }
        }
    }
}
