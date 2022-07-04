package com.example.dummyproject.opentdb

import com.example.dummyproject.model.*
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface OpenTDB {
    @GET("api_category.php")
    suspend fun getCategories(): Response<CategoriesResponse>

    @GET("api_token.php?command=request")
    suspend fun getNewTriviaToken(): Response<TriviaToken>

    @GET("api_count.php?")
    suspend fun getCategoryInfo(
        @Query("category") id: Int
    ): Response<CategoryInfoResponse>

//    @GET("api.php?amount={amount}&category={category}&difficulty={difficulty}&token={token}")

    @GET("api.php?")
    suspend fun getQuestions(
        @Query("amount") amount: Int,
        @Query("category") category: Int,
        @Query("difficulty") difficulty: String,
        @Query("token") token: String
    ): Response<QuestionsResponse>
}