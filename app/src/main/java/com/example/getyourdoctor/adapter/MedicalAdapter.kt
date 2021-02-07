package com.example.getyourdoctor.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.getyourdoctor.R
import com.example.getyourdoctor.dataclass.MedicalData
import java.util.*

class MedicalAdapter(
    val context: Context, medicalDataList: ArrayList<MedicalData>) : RecyclerView.Adapter<MedicalAdapter.MedicalViewholderClass>()
    {

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
            mediaclViewholderClass.mName.text = listPosition.getmName()
            if (listPosition.getmTime().equals("Open", ignoreCase = true)) {
                listPosition.getmTime()?.toUpperCase(locale = Locale.ROOT)
                    .also { mediaclViewholderClass.mTime.text = it }
                mediaclViewholderClass.mTime.setTextColor(Color.GREEN)
            } else if (listPosition.getmTime().equals("Close", ignoreCase = true)) {
                val also = listPosition.getmTime()?.toUpperCase(locale = Locale.ROOT)
                    .also { mediaclViewholderClass.mTime.text = it }
                mediaclViewholderClass.mTime.setTextColor(Color.RED)
            }
        }

        inner class MedicalViewholderClass(itemView: View) : RecyclerView.ViewHolder(itemView) {
            var mName: TextView
            var mTime: TextView
            init {
                mName = itemView.findViewById(R.id.medicalName)
                mName.setSingleLine()
                mName.isSelected = true
                mTime = itemView.findViewById(R.id.medicalTime)
            }
        }
        init {
            this.medicalDataList = medicalDataList
        }
}