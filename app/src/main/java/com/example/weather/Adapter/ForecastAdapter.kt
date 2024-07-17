package com.example.weather.Adapter

import android.icu.text.Transliterator.Position
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.weather.databinding.ForecastViewholderBinding
import com.example.weather.model.ForcastResponseApi
import java.text.SimpleDateFormat
import java.util.Calendar

class ForecastAdapter: RecyclerView.Adapter<ForecastAdapter.ViewHolder>() {
    private lateinit var binding: ForecastViewholderBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding = ForecastViewholderBinding.inflate(inflater, parent, false )
        return ViewHolder()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var binding = ForecastViewholderBinding.bind(holder.itemView)
        val date = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(differ.currentList[position].dtTxt)
        val calender = Calendar.getInstance()
        calender.time = date

        val dayOfWeekName = when(calender.get(Calendar.DAY_OF_WEEK)){
            1 -> "Sun"
            2 -> "Mon"
            3 -> "Tues"
            4 -> "Wed"
            5 -> "Thur"
            6 -> "Fri"
            7 -> "Sat"
            else -> "-"
        }

        binding.nameDayTxt.text = dayOfWeekName

        val hour = calender.get(Calendar.HOUR_OF_DAY)
        val anPm = if (hour < 12)"am" else "pm"
        val hour12 = calender.get(Calendar.HOUR)
        binding.hourTxt.text = hour12.toString() + anPm
        binding.tempTxt.text = differ.currentList[position].main?.temp?.let { Math.round(it).toString() } + "°"

        val icon = when(differ.currentList[position].weather?.get(0)?.icon.toString()){
            "01d", "01n" -> "sunny"
            "02d", "02n" -> "cloudy_sunny"
            "03d", "03n" -> "cloudy_sunny"
            "04d", "04n" -> "cloudy"
            "09d", "09n" -> "rainy"
            "10d", "10n" -> "rainy"
            "11d", "11n" -> "strom"
            "13d", "13n" -> "snowy"
            "50d", "50n" -> "windy"
            else -> "sunny"
        }

        val drawableResourceId: Int = binding.root.resources.getIdentifier(icon, "drawable", binding.root.context.packageName)

        Glide.with(binding.root.context)
            .load(drawableResourceId)
            .into(binding.imageView)
    }

    inner class ViewHolder:RecyclerView.ViewHolder(binding.root)

    override fun getItemCount() = differ.currentList.size

    private val differCallback = object : DiffUtil.ItemCallback<ForcastResponseApi.data>(){
        override fun areItemsTheSame(
            oldItem: ForcastResponseApi.data,
            newItem: ForcastResponseApi.data
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            p0: ForcastResponseApi.data,
            p1: ForcastResponseApi.data
        ): Boolean {
            return p0 == p1
        }
    }

    val differ = AsyncListDiffer(this, differCallback)
}