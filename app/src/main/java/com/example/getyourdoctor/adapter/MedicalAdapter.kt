package com.example.getyourdoctor.adapter

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.getyourdoctor.MedicalInfo
import com.example.getyourdoctor.R
import com.example.getyourdoctor.dataclass.MedicalData
import java.text.SimpleDateFormat
import java.util.*

class MedicalAdapter(
    val context: Context, medicalDataList: ArrayList<MedicalData>
) : RecyclerView.Adapter<MedicalAdapter.MedicalViewholderClass>() {

    private var medicalDataList: ArrayList<MedicalData>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MedicalViewholderClass {
        val view: View = LayoutInflater.from(parent.context).inflate(
            R.layout.medical_info,
            parent,
            false
        )
        return MedicalViewholderClass(view)
    }

    override fun getItemCount(): Int {
        return medicalDataList?.size!!
    }

    override fun onBindViewHolder(holder: MedicalViewholderClass, position: Int) {
        val mediaclViewholderClass = holder
        val listPosition = medicalDataList!![position]

        mediaclViewholderClass.medicalName.text = listPosition.getmName()
        mediaclViewholderClass.medicalName.setSingleLine()
        mediaclViewholderClass.medicalName.isSingleLine = true

        val cal = Calendar.getInstance()
        val sdfHour = SimpleDateFormat("HH:mm")
        val hour: String = sdfHour.format(cal.time)

        if (listPosition.getmTime1()?.compareTo(hour)!! < 0 && (listPosition.getmTime2()
                ?.compareTo(hour)!! > 0)
        ) {
            mediaclViewholderClass.medicalTime.text = "Open"
            mediaclViewholderClass.medicalTime.setTextColor(Color.GREEN)
        } else {
            mediaclViewholderClass.medicalTime.text = "Close"
            mediaclViewholderClass.medicalTime.setTextColor(Color.RED)
        }

        holder.itemView.setOnClickListener {
            var intent = Intent(context, MedicalInfo::class.java)
            intent.putExtra("MclName", listPosition.getmName())
            intent.putExtra("MclAddress", listPosition.getmAdd())
            intent.putExtra("MclTime", listPosition.getmTime1() + " to " + listPosition.getmTime2())
            intent.putExtra("MclContact", listPosition.getmPhone().toString())
            intent.putExtra("MclName1", listPosition.getmName())
            intent.putExtra("MclLat1", listPosition.getmLat())
            intent.putExtra("MclLong1", listPosition.getmLong())

            context.startActivity(intent)
        }
    }

    inner class MedicalViewholderClass(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var medicalName: TextView
        var medicalTime: TextView

        init {
            medicalName = itemView.findViewById(R.id.mName)
            medicalName.setSingleLine()
            medicalName.isSelected = true
            medicalTime = itemView.findViewById(R.id.mTime)
        }
    }

    init {
        this.medicalDataList = medicalDataList
    }
}