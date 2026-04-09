package ru.practicum.android.diploma.app.navigation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.navigation3.runtime.NavKey

/**
 * Класс, реализующий управление навигационным стеком верхнего уровня
 * для экранов приложения.
 *
 * Основная задача класса — поддержка **нескольких независимых стеков навигации**
 * для разных корневых разделов интерфейса (например, элементов Bottom Navigation).
 *
 * Каждый экран верхнего уровня имеет собственный стек экранов. При переключении между
 * такими разделами текущий стек сохраняется, а затем восстанавливается при
 * возврате к этому разделу.
 *
 * Например:
 * - Search: Search -> Vacancy -> VacancyDetails
 * - Favorites: Favorites -> Vacancy
 *
 * При переключении между Search и Favorites каждый стек остаётся независимым.
 *
 * Внутри класс хранит:
 * - карту стеков для каждого ключа верхнего уровня [topLevelBackStacks]
 * - текущий активный ключ верхнего уровня [topLevelKey]
 * - агрегированный стек [backStack], который передаётся в [androidx.navigation3.ui.NavDisplay]
 *
 * @param T тип ключа навигации. Должен реализовывать интерфейс [NavKey].
 * @param startKey ключ стартового экрана верхнего уровня в приложении.
 */
class TopLevelBackStack<T : NavKey>(
    private val startKey: T,
    private val topLevelOrder: List<T>
) {
    var transitionDirection by mutableStateOf(HorizontalDirection.Forward)
        private set

    /**
     * Хранилище стеков навигации для каждого ключа верхнего уровня.
     *
     * Хранит пары ключ-значение, где:
     * - ключ — это экран верхнего уровня (первый экран в стеке),
     * - значение — это стек экранов внутри этого раздела.
     */
    private var topLevelBackStacks = mutableStateMapOf(
        startKey to mutableStateListOf(startKey)
    )

    /**
     * Текущий активный ключ верхнего уровня.
     *
     * Определяет, какой из стеков сейчас используется для отображения.
     */
    var topLevelKey by mutableStateOf(startKey)
        private set

    /**
     * Итоговый стек навигации, который передаётся в [androidx.navigation3.ui.NavDisplay].
     *
     * Этот стек формируется из:
     * - стартового стека,
     * - текущего активного стека верхнего уровня.
     *
     * Итоговый стек пересобирается на основе текущего состояния [topLevelBackStacks] и [topLevelKey].
     */
    val backStack: List<T>
        get() {
            return when {
                topLevelKey == startKey -> currentStack

                else -> {
                    val startStack = topLevelBackStacks[startKey] ?: emptyList()
                    startStack + currentStack
                }
            }
        }

    private val currentStack: SnapshotStateList<T>
        get() = topLevelBackStacks[topLevelKey] ?: SnapshotStateList()

    /**
     * Переключает активный стек верхнего уровня.
     *
     * Используется при нажатии на элементы Bottom Navigation.
     *
     * Если стек для указанного ключа ещё не создан,
     * он инициализируется с одним элементом (самим ключом).
     *
     * @param key ключ экрана верхнего уровня, на который происходит переключение.
     */
    fun switchTopLevel(key: T) {
        val fromIndex = topLevelOrder.indexOf(topLevelKey)
        val toIndex = topLevelOrder.indexOf(key)

        transitionDirection = when {
            fromIndex < toIndex -> HorizontalDirection.Forward
            else -> HorizontalDirection.Backward
        }

        topLevelBackStacks.getOrPut(key) { mutableStateListOf(key) }
        topLevelKey = key
    }

    /**
     * Добавляет новый экран в стек текущего раздела верхнего уровня.
     *
     * Используется для навигации "вглубь" внутри раздела.
     *
     * @param key ключ нового экрана, который нужно добавить в стек.
     */
    fun add(key: T) {
        currentStack.add(key)
    }

    /**
     * Удаляет последний экран из текущего стека.
     *
     * Поведение зависит от размера стека:
     *
     * 1. Если в текущем стеке больше одного элемента —
     *    удаляется последний экран (обычная навигация "назад").
     *
     * 2. Если в стеке остался только корневой экран и текущий
     *    раздел верхнего уровня не является стартовым —
     *    происходит переключение на стартовый раздел.
     */
    fun removeLast() {
        when {
            currentStack.size > 1 -> currentStack.removeLastOrNull()
            topLevelKey != startKey -> topLevelKey = startKey
        }
    }

    /**
     * Полностью заменяет стек текущего раздела верхнего уровня.
     *
     * Используется в случаях, когда необходимо задать
     * новый стек экранов.
     *
     * @param keys новый набор экранов, который станет стеком текущего раздела.
     */
    fun replaceStack(vararg keys: T) {
        require(keys.isNotEmpty())

        topLevelBackStacks[topLevelKey] = mutableStateListOf(*keys)
    }

    enum class HorizontalDirection {
        Forward,
        Backward
    }

}
