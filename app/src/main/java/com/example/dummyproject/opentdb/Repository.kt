package com.example.dummyproject.opentdb

import com.example.dummyproject.model.*
import retrofit2.Response

class Repository {
    suspend fun getCategories(): Response<CategoriesResponse> {
        return RetrofitInstance.openTDB.getCategories()
    }

    suspend fun getCategoryInfo(id: Int): Response<CategoryInfoResponse> {
        return RetrofitInstance.openTDB.getCategoryInfo(id)
    }

    suspend fun getNewTriviaToken(): Response<TriviaToken> {
        return RetrofitInstance.openTDB.getNewTriviaToken()
    }

    suspend fun getQuestions(
        amount: Int,
        category: Int,
        difficulty: String,
        token: String
    ): Response<QuestionsResponse> {
        return RetrofitInstance.openTDB.getQuestions(amount, category, difficulty, token)
    }

}