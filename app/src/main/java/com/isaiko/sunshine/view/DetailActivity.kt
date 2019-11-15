package com.isaiko.sunshine.view

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ShareCompat
import com.isaiko.sunshine.R

class DetailActivity : AppCompatActivity(){

    lateinit var detailsTextView:TextView
    var passedWeatherDetail:String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        detailsTextView = findViewById(R.id.tv_weather_detail)

         passedWeatherDetail = intent.getStringExtra("text")

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

            R.id.action_share ->{
                if(!passedWeatherDetail.isNullOrEmpty()){
                    shareText(passedWeatherDetail!!)
                }else{
                    Toast.makeText(this, "Invalid data",Toast.LENGTH_SHORT).show()
                }
                true
            }
            else -> super.onOptionsItemSelected(item);
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.details, menu)
        return true
    }

    fun shareText(text:String){
        val mimeType = "text/plain"
        val title = "Share"

        val shareIntent = ShareCompat.IntentBuilder.from(this)
                .setType(mimeType)
                .setChooserTitle(title)
                .setText(text)
                .intent

        if(shareIntent.resolveActivity(packageManager) != null){
            startActivity(shareIntent)
        }
    }
}