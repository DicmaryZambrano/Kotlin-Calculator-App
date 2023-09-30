package com.example.kotlincalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Declaration of component from xml

        val sum = 66+5
        val calDisplay: TextView = findViewById(R.id.calDisplay)
        calDisplay.text = sum.toString()
    }
}