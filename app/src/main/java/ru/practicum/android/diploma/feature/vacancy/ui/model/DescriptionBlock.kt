package ru.practicum.android.diploma.feature.vacancy.ui.model

sealed class DescriptionBlock {
    data class Header(val text: String) : DescriptionBlock()
    data class Paragraph(val text: String) : DescriptionBlock()
    data class ListBlock(val items: List<String>) : DescriptionBlock()
}
