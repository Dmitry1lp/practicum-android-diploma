package ru.practicum.android.diploma.feature.team.presentation

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.feature.team.domain.Developer

class TeamViewModel : ViewModel() {
    private val _developers = MutableStateFlow(
        listOf(
            Developer(
                name = "Дмитрий Крылов",
                role = ROLE_TEAM_LEAD,
                avatarRes = R.drawable.img_dmitrii_krylov,
                github = GITHUB_BASE + "dimasla4ee",
                telegram = TELEGRAM_BASE + "dimasla4ee_dimaslop"
            ),
            Developer(
                name = "Сергей Аникин",
                role = ROLE_ANDROID,
                avatarRes = R.drawable.img_sergei_anikin,
                github = GITHUB_BASE + "Nicoanik",
                telegram = TELEGRAM_BASE + "Nicoanik"
            ),
            Developer(
                name = "Дмитрий Перов",
                role = ROLE_ANDROID,
                avatarRes = R.drawable.img_dmitrii_perov,
                github = GITHUB_BASE + "Dmitry1lp",
                telegram = TELEGRAM_BASE + "glowapkapps"
            ),
            Developer(
                name = "Иван Свиридов",
                role = ROLE_ANDROID,
                avatarRes = R.drawable.img_ivan_sviridov,
                github = GITHUB_BASE + "Sviridov-Ivan",
                telegram = TELEGRAM_BASE + "Ivan_Sviridov_razRab"
            ),
        )
    )
    val developers: StateFlow<List<Developer>> = _developers

    companion object {
        private const val ROLE_ANDROID = "Android Developer"
        private const val ROLE_TEAM_LEAD = "Team Lead"
        private const val GITHUB_BASE = "https://github.com/"
        private const val TELEGRAM_BASE = "https://t.me/"

    }
}
