package com.example.weather.Adapter

import android.content.Intent
import android.icu.text.Transliterator.Position
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.weather.Activity.MainActivity
import com.example.weather.databinding.CityViewholderBinding
import com.example.weather.databinding.ForecastViewholderBinding
import com.example.weather.model.CityResponseApi
import com.example.weather.model.ForcastResponseApi
import java.text.SimpleDateFormat
import java.util.Calendar

class CityAdapter : RecyclerView.Adapter<CityAdapter.ViewHolder>() {
    private lateinit var binding: CityViewholderBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding = CityViewholderBinding.inflate(inflater, parent, false)
        return ViewHolder()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var binding = CityViewholderBinding.bind(holder.itemView)
        binding.cityTxt.text = differ.currentList[position].name
        binding.root.setOnClickListener {
            val intent = Intent(binding.root.context, MainActivity::class.java)
            intent.putExtra("lat", differ.currentList[position].lat)
            intent.putExtra("log", differ.currentList[position].lon)
            intent.putExtra("name", differ.currentList[position].name)

            binding.root.context.startActivity(intent)
        }
    }

    inner class ViewHolder : RecyclerView.ViewHolder(binding.root)

    override fun getItemCount() = differ.currentList.size

    private val differCallback =
        object : DiffUtil.ItemCallback<CityResponseApi.CityResponseApiItem>() {
            override fun areItemsTheSame(
                oldItem: CityResponseApi.CityResponseApiItem,
                newItem: CityResponseApi.CityResponseApiItem
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                p0: CityResponseApi.CityResponseApiItem, p1: CityResponseApi.CityResponseApiItem
            ): Boolean {
                return p0 == p1
            }
        }

    val differ = AsyncListDiffer(this, differCallback)
}