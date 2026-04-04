package ru.practicum.android.diploma.core.utils

internal fun String?.fixImageSize(): String? {
    if (this == null) return null

    return this
        .replace("/1024px-", "/1280px-")
        .replace("/1200px-", "/1280px-")
}
