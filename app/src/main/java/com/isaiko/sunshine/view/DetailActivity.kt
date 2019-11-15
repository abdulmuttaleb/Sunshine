package com.isaiko.sunshine.view

import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.isaiko.sunshine.R

class DetailActivity : AppCompatActivity(){

    lateinit var detailsTextView:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        detailsTextView = findViewById(R.id.tv_weather_detail)

        val passedWeatherDetail:String? = intent.getStringExtra("text")

        if(!passedWeatherDetail.isNullOrEmpty()){
            detailsTextView.text = passedWeatherDetail
        }

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when(item!!.itemId){
            android.R.id.home ->{
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item);
        }
    }
}