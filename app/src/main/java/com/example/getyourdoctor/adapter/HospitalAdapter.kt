package com.example.getyourdoctor.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.getyourdoctor.R
import com.example.getyourdoctor.dataclass.HospitalData
import java.util.*
import kotlin.collections.ArrayList

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
        val hospitalviewolderClass = holder
        val listPosition:HospitalData = hospitalDataList!![position]
        hospitalviewolderClass.hospitalName.text = listPosition.gethospName()
        if (listPosition.gethospTime().equals("Open")){
            hospitalviewolderClass.hospitalTime.text = listPosition.gethospTime()
            hospitalviewolderClass.hospitalTime.setTextColor(Color.GREEN)
        }else{
            hospitalviewolderClass.hospitalTime.text = listPosition.gethospTime()
            hospitalviewolderClass.hospitalTime.setTextColor(Color.RED)
        }
//        hospitalviewolderClass.hospitalAddress.text = listPosition.gethospAddress()
//        hospitalviewolderClass.hospitalInformation.text = listPosition.gethospInformation()
    }

    inner class HospitalViewholderClass(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var hospitalName: TextView
        var hospitalTime: TextView
//        var hospitalAddress: TextView
//        var hospitalInformation: TextView
        init {
            hospitalName = itemView.findViewById(R.id.hName)
            hospitalName.setSingleLine()
            hospitalName.isSelected = true
            hospitalTime = itemView.findViewById(R.id.hTime)
//            hospitalAddress = itemView.findViewById(R.id.id3)
//            hospitalInformation = itemView.findViewById(R.id.id4)
        }
    }
    init {
        this.hospitalDataList = hospitalDataList
    }
}

