package com.example.dummyproject.model

data class QuestionsResponse(
    val response_code: Int,
    val results: List<Question>
)