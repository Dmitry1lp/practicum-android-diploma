package ru.practicum.android.diploma.core.utils

/**
 * Фильтрует элементы списка по текстовому запросу [text].
 *
 * Если переданная строка [text] пустая или состоит только из пробелов,
 * возвращается исходный список без изменений.
 *
 * @param text текстовый запрос для фильтрации.
 * @param selector строковый параметр элемента, который будет использован для фильтрации.
 *
 * @return новый список элементов, удовлетворяющих условию поиска.
 */
inline fun <T> List<T>.queryFilter(
    text: String,
    crossinline selector: (T) -> String
): List<T> {
    if (text.isBlank()) return this

    return filter { item ->
        selector(item).contains(text, ignoreCase = true)
    }
}
