package com.example.getyourdoctor.adapter

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.getyourdoctor.HospitalInfo
import com.example.getyourdoctor.HospitalLocationActivity
import com.example.getyourdoctor.R
import com.example.getyourdoctor.dataclass.HospitalData
import kotlinx.android.synthetic.main.hospital_info.*
import kotlinx.android.synthetic.main.hospital_info.view.*
import java.util.*
import kotlin.text.toUpperCase as toUpperCase

class HospitalAdapter(val context: Context, hospitalDataList: ArrayList<HospitalData>) : RecyclerView.Adapter<HospitalAdapter.HospitalViewholderClass>() {

    private var hospitalDataList: ArrayList<HospitalData>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HospitalViewholderClass {
        val view: View = LayoutInflater.from(parent.context).inflate(
            R.layout.hospital_info,
            parent,
            false
        )
        return HospitalViewholderClass(view)
    }

    override fun getItemCount(): Int {
        return hospitalDataList?.size!!
    }

    override fun onBindViewHolder(holder: HospitalViewholderClass, position: Int) {
        val hospitalViewholderClass = holder
        val listPosition = hospitalDataList!![position]
        hospitalViewholderClass.hospitalName.text = listPosition.gethName()
        if (listPosition.gethTime().equals("Open",ignoreCase = true)){
            listPosition.gethTime()?.toUpperCase(locale = Locale.ROOT)
                .also { hospitalViewholderClass.hospitalTime.text = it }
            hospitalViewholderClass.hospitalTime.setTextColor(Color.GREEN)
        }else if(listPosition.gethTime().equals("Close",ignoreCase = true)){
            val also = listPosition.gethTime()?.toUpperCase(locale = Locale.ROOT)
                .also { hospitalViewholderClass.hospitalTime.text = it }
            hospitalViewholderClass.hospitalTime.setTextColor(Color.RED)
        }
        holder.itemView.setOnClickListener {
            var intent = Intent(context,HospitalInfo::class.java)
            intent.putExtra("HospName",listPosition.gethName())
            intent.putExtra("HospAddress",listPosition.gethAdd())
            intent.putExtra("HospInformation",listPosition.gethInfo())
            context.startActivity(intent)
        }
        holder.itemView.locationBtn.setOnClickListener {
            var intent = Intent(context, HospitalLocationActivity::class.java)
            context.startActivity(intent)
        }
    }

    inner class HospitalViewholderClass(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var hospitalName: TextView
        var hospitalTime: TextView
        init {
            hospitalName = itemView.findViewById(R.id.hName)
            hospitalName.setSingleLine()
            hospitalName.isSelected = true
            hospitalTime = itemView.findViewById(R.id.hTime)
        }
    }
    init {
        this.hospitalDataList = hospitalDataList
    }
}

