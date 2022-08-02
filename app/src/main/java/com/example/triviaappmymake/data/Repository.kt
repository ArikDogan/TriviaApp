package com.example.triviaappmymake.data

import android.util.Log
import com.example.triviaappmymake.model.Question
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val TAG = "JSON"


class Repository  {
    var questionArrayList = ArrayList<Question>()

    fun getQuestions(callBack : QuestionListAsyncResponse) : List<Question>{
        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://raw.githubusercontent.com")
            .build()
            .create(ApiInterface::class.java)



        val retrofitData = retrofitBuilder.getQuestionData()

        retrofitData.enqueue(object : Callback<List<QuestionData>?> {
            override fun onResponse(
                call: Call<List<QuestionData>?>,
                response: Response<List<QuestionData>?>
            ) {
                for(i in 0 until (response.body()!!.size)){
                    val question = Question(response.body()!![i][0].toString(), response.body()!![i][1].toString().toBoolean())
                    questionArrayList.add(question)
                }
                if(null != callBack){callBack.processFinished(questionArrayList)}
            }

            override fun onFailure(call: Call<List<QuestionData>?>, t: Throwable) {
                Log.d(TAG, "onFailure: Failed to fetch data !!")
            }
        })


        return questionArrayList
    }

}