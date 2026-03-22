package ru.practicum.android.diploma.core.data.network

import ru.practicum.android.diploma.core.domain.model.VacancyQuery

/**
 * Формирует параметры запроса для поиска вакансий.
 *
 * Используется для метода API:
 * VacancyApi.searchVacancies(@QueryMap params)
 *
 * В Map добавляются только те параметры, которые не равны null,
 * так как API возвращает ошибку, если передать пустые значения.
 *
 * Пример использования:
 *
 * class VacancyRepository(
 *     private val api: VacancyApi
 * ) {
 *      fun search(text,area,industry...): ResponseDto {
 *
 *      val params = buildVacancyQuery(
 *          text = "android",
 *          area = 1,
 *          industry = null,
 *          salary = 100000,
 *          page = 0,
 *          onlyWithSalary = true
 *   )
 *   return api.searchVacancies(params)
 *      }
 * }
 *
 *
 *
 * api.searchVacancies(params)
 */
fun buildVacancyQuery(
    query: VacancyQuery
): Map<String, String> {
    val params = mutableMapOf<String, String>()

    params["text"] = query.text

    query.area?.let { params["area"] = it.toString() }

    query.industry?.let { params["industry"] = it.toString() }

    query.salary?.let { params["salary"] = it.toString() }

    query.page?.let { params["page"] = it.toString() }

    if (query.onlyWithSalary == true) {
        params["only_with_salary"] = "true"
    }

    return params
}
