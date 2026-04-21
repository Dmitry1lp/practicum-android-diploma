package ru.practicum.android.diploma.feature.vacancy.utils

import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import ru.practicum.android.diploma.feature.vacancy.ui.model.DescriptionBlock

fun parseElement(element: Element, result: MutableList<DescriptionBlock>) {
    when (element.tagName()) {

        "h2", "h3" -> {
            result.add(DescriptionBlock.Header(element.text()))
        }

        "p" -> {
            result.add(DescriptionBlock.Paragraph(element.text()))
        }

        "ul" -> {
            val items = element.select("li").map { it.text() }
            result.add(DescriptionBlock.ListBlock(items))
        }
    }

    element.children().forEach {
        parseElement(it, result)
    }
}

fun parseHtml(html: String): List<DescriptionBlock> {
    val doc = Jsoup.parse(html)
    val result = mutableListOf<DescriptionBlock>()

    parseElement(doc.body(), result)

    return result
}
