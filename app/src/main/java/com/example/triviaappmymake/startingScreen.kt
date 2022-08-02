package com.example.triviaappmymake

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.example.triviaappmymake.databinding.ActivityStartingScreenBinding

class startingScreen : AppCompatActivity() {

    private lateinit var binding : ActivityStartingScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this@startingScreen, R.layout.activity_starting_screen)


        binding.startButton.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                val intent = Intent(this@startingScreen, MainActivity::class.java)
                startActivity(intent)
            }
        })

    }
}