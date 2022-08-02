package com.example.triviaappmymake.data

import retrofit2.Call
import retrofit2.http.GET

interface ApiInterface {

    @GET("/curiousily/simple-quiz/master/script/statements-data.json")
    fun getQuestionData() : Call<List<QuestionData>>

}