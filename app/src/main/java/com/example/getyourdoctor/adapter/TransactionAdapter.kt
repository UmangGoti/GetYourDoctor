package com.example.getyourdoctor.adapter

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.getyourdoctor.R
import com.example.getyourdoctor.dataclass.TransactionData

class TransactionAdapter (
    val context: Context, transactionDataList: ArrayList<TransactionData>) : RecyclerView.Adapter<TransactionAdapter.TransactionViewholderClass>()
    {

        private var transactionDataList: ArrayList<TransactionData>? = null

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionAdapter.TransactionViewholderClass {
            val view: View = LayoutInflater.from(parent.context).inflate(
                R.layout.transaction_info,
                parent,
                false
            )
            return TransactionViewholderClass(view)
        }

        override fun getItemCount(): Int {
            return transactionDataList?.size!!
        }

        override fun onBindViewHolder(holder: TransactionAdapter.TransactionViewholderClass, position: Int) {
            val transactionViewholderClass = holder
            val listPosition = transactionDataList!![position]
            transactionViewholderClass.hId.text =listPosition.gettHospitalID()
            transactionViewholderClass.tAt.text = listPosition.gettAppointmentDate()
            transactionViewholderClass.tBt.text = listPosition.gettBookingDate()

            if (listPosition.gettPaymentStatus().equals("Authorized")){
                transactionViewholderClass.tStatus.text = "Authorized"
                transactionViewholderClass.tStatus.setTextColor(Color.GREEN)
            }else{
                transactionViewholderClass.tStatus.text = "Failed"
                transactionViewholderClass.tStatus.setTextColor(Color.RED)
            }

        }

        inner class TransactionViewholderClass(itemView: View) : RecyclerView.ViewHolder(itemView) {
            var hId: TextView
            var tAt: TextView
            var tBt: TextView
            var tStatus: TextView
            init {
                hId = itemView.findViewById(R.id.hospitalName)
                tAt = itemView.findViewById(R.id.tAppointmentDate)
                tBt = itemView.findViewById(R.id.tBookingDate)
                tStatus = itemView.findViewById(R.id.tStatus)
            }
        }
        init {
            this.transactionDataList = transactionDataList
        }
    }