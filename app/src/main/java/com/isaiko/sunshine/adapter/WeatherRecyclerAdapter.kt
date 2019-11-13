package com.isaiko.sunshine.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.isaiko.sunshine.R
import java.nio.file.WatchEvent

class WeatherRecyclerAdapter() : RecyclerView.Adapter<WeatherRecyclerAdapter.WeatherViewHolder>(){

    private lateinit var onWeatherClickListener: WeatherClickListener

    var weatherList:ArrayList<String> = arrayListOf()
    lateinit var context: Context
    constructor(context:Context, weatherList:ArrayList<String>):this(){
        this.context = context
        this.weatherList = weatherList
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        return WeatherViewHolder(LayoutInflater.from(context).inflate(R.layout.item_forecast,parent, false))
    }

    override fun getItemCount(): Int {
        return weatherList.size
    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        val weather = weatherList[position]
        holder.weatherTextView.text = weather
    }

    inner class WeatherViewHolder(itemView:View): RecyclerView.ViewHolder(itemView){
        var weatherTextView: TextView = itemView.findViewById(R.id.tv_text)
        init {
            itemView.setOnClickListener {
                onWeatherClickListener.onWeatherClick(weatherList[adapterPosition])
            }
        }
    }

    interface WeatherClickListener{
        fun onWeatherClick(weather:String)
    }

    fun setOnWeatherClickListener(listener:WeatherClickListener){
        this.onWeatherClickListener = listener
    }
}