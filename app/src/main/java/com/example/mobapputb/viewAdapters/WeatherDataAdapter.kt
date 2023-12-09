package com.example.mobapputb.viewAdapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mobapputb.databinding.ItemWeatherDataBinding
import com.example.mobapputb.models.WeatherDataAdapterModel

class WeatherDataAdapter(private val dataList: List<WeatherDataAdapterModel>) : RecyclerView.Adapter<WeatherDataAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemWeatherDataBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = dataList[position]
        holder.timestampTextView.text = data.timestamp
        holder.temperatureTextView.text = data.temperature
        holder.precipProbTextView.text = data.precipProb
        holder.visibilityTextView.text = data.visibility
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    inner class ViewHolder(binding: ItemWeatherDataBinding) : RecyclerView.ViewHolder(binding.root) {
        val timestampTextView: TextView = binding.labelTimestamp
        val temperatureTextView: TextView = binding.labelTemperature
        val precipProbTextView: TextView = binding.labelPrecipProb
        val visibilityTextView: TextView = binding.labelVisibility
    }
}