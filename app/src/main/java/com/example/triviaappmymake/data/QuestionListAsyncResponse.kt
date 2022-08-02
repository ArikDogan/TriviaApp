package com.example.triviaappmymake.data

import com.example.triviaappmymake.model.Question

interface QuestionListAsyncResponse {
    fun processFinished(questionArrayList : ArrayList<Question>)
}
