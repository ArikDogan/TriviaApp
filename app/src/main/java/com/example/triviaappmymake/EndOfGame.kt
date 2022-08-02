package com.example.triviaappmymake

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import com.example.triviaappmymake.databinding.ActivityEndOfGameBinding
import com.example.triviaappmymake.databinding.ActivityMainBinding

class EndOfGame : AppCompatActivity() {
    private lateinit var binding : ActivityEndOfGameBinding
    private val MESSAGE_ID = "message_prefs"
    private var highestScore = 0
    private var highestScoreData = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this@EndOfGame, R.layout.activity_end_of_game)

        val extra = intent.extras
        if(extra != null){
            highestScore = extra.getInt("Score")
            binding.trueTextView.text = extra.getInt("True").toString()
            binding.falseTextView.text = extra.getInt("False").toString()
            val percentage = calculatePercentage(extra.getInt("True"), extra.getInt("Total"))
            val text = "$percentage% of the questions answered correctly"
            binding.answerTextView.text = text
            binding.textViewCurrentScore.text = "Your Score : $highestScore"
            showText(percentage)
        }



        val getSharedData = getSharedPreferences(MESSAGE_ID, MODE_PRIVATE)
        highestScoreData = getSharedData.getInt("Score", -1)


        Log.d("score", "highestScoreData = $highestScoreData, highestScore = $highestScore")

        if(highestScoreData != -1){
            binding.bestScoreTextView.text = "Highest Score : $highestScoreData"
        }
        else{
            binding.bestScoreTextView.text = "Highest Score : 0"
        }

        binding.tryAgainButton.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                saveData(highestScoreData, highestScore)
                finish()
                val intent = Intent(this@EndOfGame, MainActivity::class.java)
                startActivity(intent)
            }
        })


    }

    private fun saveData(highestScore : Int, currentScore : Int ) {
        val sharedPreferences = getSharedPreferences(MESSAGE_ID, MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        if(currentScore > highestScore){
            editor.putInt("Score", currentScore)
            editor.apply()
        }
        else {
            editor.putInt("Score", highestScore)
            editor.apply()
        }

    }

    private fun showText(percentage: Float) {
        if(percentage < 25){
            binding.showText.text = applicationContext.resources.getString(R.string.under25)
            binding.showText.setTextColor(Color.RED)
        }
        else if(percentage < 50 ){
            binding.showText.text = applicationContext.resources.getString(R.string.under50)
            binding.showText.setTextColor(Color.YELLOW)
        }
        else if(percentage < 75){
            binding.showText.text = applicationContext.resources.getString(R.string.under75)
            binding.showText.setTextColor(Color.BLUE)
        }
        else{
            binding.showText.text = applicationContext.resources.getString(R.string.under100)
            binding.showText.setTextColor(Color.GREEN)
        }
    }

    private fun calculatePercentage(trueNum : Int, totalNum : Int) : Float{
        var percentage =  (trueNum).toFloat() / totalNum
        percentage *= 100
        return percentage
    }

    override fun onPause() {
        saveData(highestScoreData, highestScore)
        super.onPause()
    }


}