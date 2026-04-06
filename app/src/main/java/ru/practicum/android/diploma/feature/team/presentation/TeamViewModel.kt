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
                role = "Team Lead",
                avatarRes = R.drawable.img_dmitrii_krylov,
                github = "https://github.com/dimasla4ee",
                telegram = "https://t.me/dimasla4ee_dimaslop"
            ),
            Developer(
                name = "Сергей Аникин",
                role = "Android Developer",
                avatarRes = R.drawable.img_sergey_anikin,
                github = "https://github.com/Nicoanik",
                telegram = "https://t.me/Nicoanik"
            ),
            Developer(
                name = "Дмитрий Перов",
                role = "Android Developer",
                avatarRes = R.drawable.img_dmitrii_perov,
                github = "https://github.com/Dmitry1lp",
                telegram = "https://t.me/glowapkapps"
            ),
            Developer(
                name = "Иван Свиридов",
                role = "Android Developer",
                avatarRes = R.drawable.img_ivan_sviridov,
                github = "https://github.com/Sviridov-Ivan",
                telegram = "https://t.me/Ivan_Sviridov_razRab"
            ),
        )
    )
    val developers: StateFlow<List<Developer>> = _developers
}
