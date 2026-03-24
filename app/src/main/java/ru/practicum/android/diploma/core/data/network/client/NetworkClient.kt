package ru.practicum.android.diploma.core.data.network.client

import ru.practicum.android.diploma.core.data.network.dto.Request
import ru.practicum.android.diploma.core.data.network.dto.Response

interface NetworkClient {
    suspend fun doRequest(request: Request): Response
}
