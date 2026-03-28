package ru.practicum.android.diploma.core.utils

private const val GROUPING_SIZE = 3

fun Int.formatWithSpaces(): String = toString()
    .reversed()
    .chunked(GROUPING_SIZE)
    .joinToString(" ")
    .reversed()
