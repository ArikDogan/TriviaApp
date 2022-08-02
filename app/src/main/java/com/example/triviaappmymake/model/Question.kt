package com.example.triviaappmymake.model

class Question (private var question : String, private var answer : Boolean, private var isSolved : Boolean = false, private var givenAnswer : Int = 0) {

    fun getQuestion() : String { return this.question }
    fun getAnswer() : Boolean { return this.answer }
    fun getIsSolved() : Boolean { return this.isSolved }
    fun getGivenAnswer() : Int { return this.givenAnswer }

    fun setQuestion(question : String) { this.question = question}
    fun setAnswer(answer : Boolean) { this.answer = answer }
    fun setIsSolved(isSolved : Boolean) { this.isSolved = isSolved}
    fun setGivenAnswer(givenAnswer : Int) {this.givenAnswer = givenAnswer}
}