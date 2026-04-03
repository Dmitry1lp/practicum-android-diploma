package ru.practicum.android.diploma.core.domain.model

sealed class GeoArea {
    abstract val id: Int
    abstract val name: String

    /**
     * Модель страны (корневой элемент иерархии).
     *
     * Страна не имеет родителя и может содержать список регионов.
     * Пример: Россия, США, Германия
     *
     * @property id Уникальный идентификатор страны
     * @property name Название страны
     * @property regions Список регионов внутри страны
     */
    data class Country(
        override val id: Int,
        override val name: String,
        val regions: List<Region>
    ) : GeoArea()

    /**
     * Модель региона (область, республика, край, город федерального значения).
     *
     * Регион всегда принадлежит какой-либо стране и не содержит вложенных областей.
     * Пример: Республика Татарстан, Московская область, г. Санкт-Петербург
     *
     * @property id Уникальный идентификатор региона
     * @property name Название региона
     * @property countryId Идентификатор страны, в которой находится регион
     */
    data class Region(
        override val id: Int,
        override val name: String,
        val countryId: Int
    ) : GeoArea()
}
