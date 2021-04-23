package com.example.getyourdoctor.adapter

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.IntegerRes
import androidx.recyclerview.widget.RecyclerView
import com.example.getyourdoctor.HospitalInfo
import com.example.getyourdoctor.HospitalLocationActivity
import com.example.getyourdoctor.R
import com.example.getyourdoctor.dataclass.HospitalData
import kotlinx.android.synthetic.main.hospital_info.*
import kotlinx.android.synthetic.main.hospital_info.view.*
import java.text.SimpleDateFormat
import java.util.*

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
        hospitalViewholderClass.hospitalName.setSingleLine()
        hospitalViewholderClass.hospitalName.isSingleLine = true
        val cal = Calendar.getInstance()
        val sdfHour = SimpleDateFormat("HH:mm")
        val hour: String = sdfHour.format(cal.time)
        var flag: String = "0"
        if (listPosition.gethTime1()?.compareTo(hour)!! < 0 && (listPosition.gethTime2()?.compareTo(hour)!! > 0)){
            hospitalViewholderClass.hospitalTime.text = "Open"
            flag = "0"
            hospitalViewholderClass.hospitalTime.setTextColor(Color.GREEN)
        }else{
            hospitalViewholderClass.hospitalTime.text = "Close"
            flag = "1"
            hospitalViewholderClass.hospitalTime.setTextColor(Color.RED)
        }

        holder.itemView.setOnClickListener {
            var intent = Intent(context, HospitalInfo::class.java)
            intent.putExtra("HospName", listPosition.gethName())
            intent.putExtra("HospAddress", listPosition.gethAdd())
            intent.putExtra("HospInformation", listPosition.gethInfo())
            intent.putExtra("HospTime",listPosition.gethTime1()+" to "+listPosition.gethTime2())
            intent.putExtra("HospContact",listPosition.gethPhone().toString())
            intent.putExtra("HospName1", listPosition.gethName())
            intent.putExtra("HospLat1", listPosition.gethLat())
            intent.putExtra("HospLong1", listPosition.gethLong())
            intent.putExtra("HospId1",listPosition.gethId())
            intent.putExtra("HospStatus",flag)
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

