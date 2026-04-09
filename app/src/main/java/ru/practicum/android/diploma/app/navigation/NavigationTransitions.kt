package ru.practicum.android.diploma.app.navigation

import androidx.compose.animation.ContentTransform
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.togetherWith
import androidx.compose.animation.slideInHorizontally as composeSlideInHorizontally
import androidx.compose.animation.slideOutHorizontally as composeSlideOutHorizontally

object NavigationTransitions {

    private const val ANIM_DURATION = 300

    fun forward(): ContentTransform = slideInFromEnd() togetherWith slideOutToStart()
    fun back(): ContentTransform = slideInFromStart() togetherWith slideOutToEnd()
    fun predictiveBack(): ContentTransform = back()

    private fun slideInFromEnd(): EnterTransition =
        composeSlideInHorizontally(
            initialOffsetX = { it },
            animationSpec = tween(ANIM_DURATION)
        )

    private fun slideOutToStart(): ExitTransition =
        composeSlideOutHorizontally(
            targetOffsetX = { -it },
            animationSpec = tween(ANIM_DURATION)
        )

    private fun slideInFromStart(): EnterTransition =
        composeSlideInHorizontally(
            initialOffsetX = { -it },
            animationSpec = tween(ANIM_DURATION)
        )

    private fun slideOutToEnd(): ExitTransition =
        composeSlideOutHorizontally(
            targetOffsetX = { it },
            animationSpec = tween(ANIM_DURATION)
        )
}
