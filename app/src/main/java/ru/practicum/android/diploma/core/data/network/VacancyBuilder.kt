package ru.practicum.android.diploma.core.data.network
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
    text: String,
    area: Int?,
    industry: Int?,
    salary: Int?,
    page: Int?,
    onlyWithSalary: Boolean?
): Map<String, String> {
    val params = mutableMapOf<String, String>()

    params["text"] = text

    area?.let { params["area"] = it.toString() }

    industry?.let { params["industry"] = it.toString() }

    salary?.let { params["salary"] = it.toString() }

    page?.let { params["page"] = it.toString() }

    if (onlyWithSalary == true) {
        params["only_with_salary"] = "true"
    }

    return params
}
