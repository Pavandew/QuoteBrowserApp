package com.example.quotesbrowserapp.repository

import com.example.quotesbrowserapp.model.PicsumItem
import com.example.quotesbrowserapp.services.ApiService

class QuoteRepository(private val api: ApiService) {

    suspend fun getPicsList(page: Int = 1, limit: Int = 10): List<PicsumItem> {
        return api.getPicsSumList(page, limit)
    }
}