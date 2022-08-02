package com.example.triviaappmymake

import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings.Global.getString
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import com.example.triviaappmymake.data.QuestionListAsyncResponse
import com.example.triviaappmymake.data.Repository
import com.example.triviaappmymake.databinding.ActivityMainBinding
import com.example.triviaappmymake.model.Question
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private lateinit var questionArrayList : List<Question>
    private var questionCounterVar = 0
    private var trueCounter = 0
    private var falseCounter = 0
    private var score = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this@MainActivity, R.layout.activity_main)
        binding.textViewScore.text = "Score : $score"

        questionArrayList = Repository().getQuestions(object : QuestionListAsyncResponse {
            override fun processFinished(questionArrayList: ArrayList<Question>) {
                binding.textViewQuestionContent.text = questionArrayList[questionCounterVar].getQuestion()
                questionCounter()
            }
        })

        binding.nextButton.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                questionCounterVar += 1

                if(questionCounterVar == questionArrayList.size){
                    questionCounterVar -= 1
                    binding.finishButton.visibility = View.VISIBLE
                }
                else{
                    binding.textViewQuestionContent.text = questionArrayList[questionCounterVar].getQuestion()
                    questionCounter()
                    setPhoto()
                }

            }
        })

        binding.prevButton.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                questionCounterVar -= 1

                if(questionCounterVar < 0){
                    questionCounterVar = 0
                }
                else{
                    binding.textViewQuestionContent.text = questionArrayList[questionCounterVar].getQuestion()
                    questionCounter()
                    setPhoto()
                }
            }
        })

        binding.trueButton.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                if(!questionArrayList[questionCounterVar].getIsSolved()){
                    isTrue(true, p0!!)
                    questionArrayList[questionCounterVar].setIsSolved(true)
                }
            }
        })

        binding.falseButton.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                if(!questionArrayList[questionCounterVar].getIsSolved()){
                    isTrue(false, p0!!)
                    questionArrayList[questionCounterVar].setIsSolved(true)
                }

            }
        })


        binding.goToEnd.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                questionCounterVar = questionArrayList.size - 1
                binding.textViewQuestionContent.text = questionArrayList[questionCounterVar].getQuestion()
                questionCounter()
                setPhoto()
            }
        })

        binding.finishButton.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                val intent = Intent(this@MainActivity, EndOfGame::class.java)
                intent.putExtra("Score", score)
                intent.putExtra("True", trueCounter)
                intent.putExtra("False", falseCounter)
                intent.putExtra("Total", questionArrayList.size)
                startActivity(intent)
            }
        })


    }

    private fun setPhoto() {
        if(questionArrayList[questionCounterVar].getIsSolved()){
            if(questionArrayList[questionCounterVar].getGivenAnswer() == 1){
                binding.imageViewSolved.setImageResource(R.drawable.true_image)
            }
            else{
                binding.imageViewSolved.setImageResource(R.drawable.false_image)
            }
        }
        else{
            binding.imageViewSolved.setImageResource(R.drawable.trivia_app_background)
        }
    }

    private fun blinkAnimation(view : View) {
        val blink = AnimationUtils.loadAnimation(this@MainActivity, R.anim.blink_animation)
        view.animation = blink
        view.startAnimation(blink)

        blink.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(p0: Animation?) {
                view.setBackgroundColor(Color.GREEN)
            }

            override fun onAnimationEnd(p0: Animation?) {
                view.setBackgroundColor(Color.parseColor("#3B444C"))
            }

            override fun onAnimationRepeat(p0: Animation?) {
                //Empty
            }
        })
    }

    private fun isTrue(answer : Boolean, view : View) {
        if(answer == questionArrayList[questionCounterVar].getAnswer()){
            questionArrayList[questionCounterVar].setGivenAnswer(1)
            blinkAnimation(view)
            updateScore(true)
            trueCounter += 1
        }
        else{
            questionArrayList[questionCounterVar].setGivenAnswer(-1)
            slideDownAnimation(view)
            updateScore(false)
            falseCounter +=1
        }
    }

    private fun slideDownAnimation(view : View) {
        val slideDown = AnimationUtils.loadAnimation(this@MainActivity, R.anim.slide_down)
        view.animation = slideDown
        view.startAnimation(slideDown)

        slideDown.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(p0: Animation?) {

                view.setBackgroundColor(Color.RED)
            }

            override fun onAnimationEnd(p0: Animation?) {
                view.setBackgroundColor(Color.parseColor("#3B444C"))
            }

            override fun onAnimationRepeat(p0: Animation?) {
                //Empty
            }
        })
    }

    private fun questionCounter() {
        val text = "Question: ${questionCounterVar + 1}/${questionArrayList.size}"
        binding.textViewQuestionCounter.text = text
    }

    private fun updateScore(result : Boolean){
        if(result){
            score += 100
        }
        else{
            if(score != 0){
                score -= 50
            }
        }
        binding.textViewScore.text = "Score : $score"
    }

}