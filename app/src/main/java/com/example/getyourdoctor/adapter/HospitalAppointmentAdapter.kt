package com.example.getyourdoctor.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.getyourdoctor.R
import com.example.getyourdoctor.dataclass.HospitalAppointmentData

class HospitalAppointmentAdapter(val context: Context, hospitalAppointmentDataList: ArrayList<HospitalAppointmentData>) : RecyclerView.Adapter<HospitalAppointmentAdapter.HospitalAppointmentViewholderClass>() {
    private var hospitalAppointmentDataList: ArrayList<HospitalAppointmentData>? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HospitalAppointmentAdapter.HospitalAppointmentViewholderClass  {
        val view: View = LayoutInflater.from(parent.context).inflate(
            R.layout.appointment_info,
            parent,
            false
        )
        return HospitalAppointmentViewholderClass(view)
    }

    override fun onBindViewHolder(
        holder: HospitalAppointmentAdapter.HospitalAppointmentViewholderClass,
        position: Int
    ) {
        val hospitalAppointmentViewholderClass = holder
        val listPosition = hospitalAppointmentDataList!![position]
        hospitalAppointmentViewholderClass.pname.text = listPosition.gettName()
        hospitalAppointmentViewholderClass.page.text = listPosition.gettAge()
        hospitalAppointmentViewholderClass.pcontact.text = listPosition.gettContact()
        hospitalAppointmentViewholderClass.pbookingdate.text = listPosition.gettBookingDate()
        hospitalAppointmentViewholderClass.pappointmentdate.text = listPosition.gettAppointmentDate()
    }

    override fun getItemCount(): Int {
        return  hospitalAppointmentDataList?.size!!
    }

    inner class HospitalAppointmentViewholderClass(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var pname: TextView
        var page: TextView
        var pcontact: TextView
        var pbookingdate: TextView
        var pappointmentdate: TextView
        init {
            pname = itemView.findViewById(R.id.hPName)
            pname.setSingleLine()
            pname.isSelected = true

            page = itemView.findViewById(R.id.hPAge)
            pcontact = itemView.findViewById(R.id.hPContact)
            pbookingdate = itemView.findViewById(R.id.hbookingDate)
            pappointmentdate = itemView.findViewById(R.id.hAppoDate)
        }
    }

    init {
        this.hospitalAppointmentDataList = hospitalAppointmentDataList
    }
}