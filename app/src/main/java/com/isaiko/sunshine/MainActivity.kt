/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.isaiko.sunshine

import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import com.isaiko.sunshine.utilities.NetworkUtils
import com.isaiko.sunshine.utilities.OpenWeatherJsonUtils
import java.lang.Exception
import com.isaiko.sunshine.data.SunshinePreferences
import android.support.v4.app.SupportActivity
import android.support.v4.app.SupportActivity.ExtraData
import android.support.v4.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast


class MainActivity : AppCompatActivity() {
    lateinit var mWeatherTextView: TextView
    lateinit var errorTextView: TextView
    lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forecast)

        mWeatherTextView = findViewById(R.id.tv_weather_data)
        errorTextView = findViewById(R.id.tv_loading_error)
        progressBar = findViewById(R.id.pb_main)

        loadWeatherData()
    }

    fun loadWeatherData(){
        val location = SunshinePreferences.getPreferredWeatherLocation(this)
        mWeatherTextView.text = ""
        FetchWeatherAsyncTask().execute(location)
    }

    inner class FetchWeatherAsyncTask:AsyncTask<String, Void, ArrayList<String>?>(){

        override fun onPreExecute() {
            super.onPreExecute()
            progressBar.visibility = View.VISIBLE
        }
        override fun doInBackground(vararg params: String?): ArrayList<String>? {
            if(params.isEmpty()){
                return null
            }

            val location = params[0]
            val weatherUrl = NetworkUtils.buildUrl(location!!)

            return try {
                val jsonWeatherResponse = NetworkUtils.getResponseFromHttpUrl(weatherUrl!!)

                val simpleJsonWeatherData = OpenWeatherJsonUtils.getSimpleWeatherStringsFromJson(this@MainActivity, jsonWeatherResponse!!)

                simpleJsonWeatherData
            }catch (e:Exception){
                e.printStackTrace()
                errorTextView.visibility = View.VISIBLE
                null
            }
        }

        override fun onPostExecute(result: ArrayList<String>?) {
            if(result!=null){
                for(weather in result){
                    errorTextView.visibility = View.GONE
                    mWeatherTextView.append((weather) + "\n\n\n");
                }
            }

            progressBar.visibility = View.GONE
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.forecast,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when(item!!.itemId){
            R.id.refresh ->{
                Toast.makeText(this, "Refresh",Toast.LENGTH_SHORT).show()
                loadWeatherData()
                true
            }
            else ->{
                super.onOptionsItemSelected(item)
            }
        }
    }
}