package ru.practicum.android.diploma.app.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween

object NavigationTransitions {

    private const val ANIM_DURATION = 300

    fun slideInHorizontally(rightToLeft: Boolean): EnterTransition {
        val directionModifier = if (rightToLeft) 1 else -1
        return androidx.compose.animation.slideInHorizontally(
            initialOffsetX = { it * directionModifier },
            animationSpec = tween(ANIM_DURATION)
        )
    }

    fun slideOutHorizontally(rightToLeft: Boolean): ExitTransition {
        val directionModifier = if (rightToLeft) 1 else -1
        return androidx.compose.animation.slideOutHorizontally(
            targetOffsetX = { it * directionModifier },
            animationSpec = tween(ANIM_DURATION)
        )
    }
}
