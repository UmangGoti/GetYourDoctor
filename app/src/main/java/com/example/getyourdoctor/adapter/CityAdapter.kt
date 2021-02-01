package com.example.getyourdoctor.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.getyourdoctor.CityHospitalListMenu
import com.example.getyourdoctor.R

class CityAdapter(val context: Context, cityList: ArrayList<String>) :
    Adapter<CityAdapter.CityViewholderClass>() {
    private var cityList: ArrayList<String>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewholderClass {
        val view: View = LayoutInflater.from(parent.context).inflate(
            R.layout.city_info,
            parent,
            false
        )
        return CityViewholderClass(view)
    }

    override fun onBindViewHolder(holder: CityViewholderClass, position: Int) {
        var cityViewholderclass:CityViewholderClass = holder
        val listPosition:String = cityList!![position]
        cityViewholderclass.city.text = listPosition
    }

    override fun getItemCount(): Int {
        return cityList?.size!!
    }

    inner class CityViewholderClass(itemView: View) : RecyclerView.ViewHolder(itemView){
        var city: TextView
        init {
            city = itemView.findViewById(R.id.cityName)
        }
    }
    init {
        this.cityList = cityList
    }
}