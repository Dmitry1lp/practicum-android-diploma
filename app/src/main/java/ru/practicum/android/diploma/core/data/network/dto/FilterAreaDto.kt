package ru.practicum.android.diploma.core.data.network.dto

import ru.practicum.android.diploma.core.domain.model.FilterArea

data class FilterAreaDto(
    val id: Int,
    val name: String,
    val parentId: Int?,
    val areas: List<FilterAreaDto>
)

fun FilterAreaDto.toDomain(): FilterArea {
    return FilterArea(
        id = id,
        name = name
    )
}

/**
 * Преобразует иерархическое дерево регионов в список для отображения в UI.
 *
 * Функция обходит дерево регионов, полученное из API (/areas),
 * и собирает только те элементы, которые нужны
 *
 *
 * @param areas корневой список регионов
 * @return отсортированный по имени список для отображения регионов
 */

fun flattenAreas(areas: List<FilterAreaDto>): List<FilterAreaDto> {
    val result = mutableListOf<FilterAreaDto>()

    fun traverse(area: FilterAreaDto) {
        val isRegion = area.parentId != null && area.areas.isNotEmpty()
        val isCity = area.areas.isEmpty()

        if (isRegion || isCity) {
            result.add(area)
        }

        area.areas.forEach {
            traverse(it)
        }
    }

    areas.forEach {
        traverse(it)
    }

    return result.sortedBy { it.name }
}
