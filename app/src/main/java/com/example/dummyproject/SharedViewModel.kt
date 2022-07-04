package com.example.dummyproject

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dummyproject.model.*
import com.example.dummyproject.opentdb.Repository
import kotlinx.coroutines.launch
import retrofit2.Response

class SharedViewModel() : ViewModel() {
    val categories: MutableLiveData<Response<CategoriesResponse>> = MutableLiveData()
    val categoryInfo: MutableLiveData<Response<CategoryInfoResponse>> = MutableLiveData()
    val triviaToken: MutableLiveData<Response<TriviaToken>> = MutableLiveData()
    val questions: MutableLiveData<Response<QuestionsResponse>> = MutableLiveData()

    val triviaSession: MutableLiveData<TriviaSession> = MutableLiveData()

    fun getCategories() {
        viewModelScope.launch {
            categories.value = Repository().getCategories()
        }
    }

    fun getCategoryInfo(id: Int) {
        viewModelScope.launch {
            categoryInfo.value = Repository().getCategoryInfo(id)
        }
    }

    fun getTriviaToken() {
        viewModelScope.launch {
            triviaToken.value = Repository().getNewTriviaToken()
        }
    }

    fun getQuestions(
        amount: Int,
        category: Int,
        difficulty: String,
        token: String
    ) {
        viewModelScope.launch {
            questions.value = Repository().getQuestions(amount, category, difficulty, token)
        }
    }
}