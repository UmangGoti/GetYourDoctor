package com.example.getyourdoctor.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.getyourdoctor.R
import com.example.getyourdoctor.dataclass.TransactionData

class TransactionAdapter (val context: Context, transactionDataList: ArrayList<TransactionData>) : RecyclerView.Adapter<TransactionAdapter.TransactionViewholderClass>()
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
            transactionViewholderClass.hId.text =listPosition.gettHospitalName()
            transactionViewholderClass.tAt.text = listPosition.gettAppointmentDate()
            transactionViewholderClass.tBt.text = listPosition.gettBookingDate()

            if (listPosition.gettPaymentStatus().equals("Authorized")){
                transactionViewholderClass.tstatus.text = "Authorized"
                transactionViewholderClass.tstatus.setTextColor(Color.GREEN)
            }else{
                transactionViewholderClass.tstatus.text = "Failed"
                transactionViewholderClass.tstatus.setTextColor(Color.RED)
            }

        }

        inner class TransactionViewholderClass(itemView: View) : RecyclerView.ViewHolder(itemView) {
            var hId: TextView
            var tAt: TextView
            var tBt: TextView
            var tstatus: TextView
            init {
                hId = itemView.findViewById(R.id.thospName)
                tAt = itemView.findViewById(R.id.tAppoDate)
                tBt = itemView.findViewById(R.id.tbookingDate)
                tstatus = itemView.findViewById(R.id.tstatusInfo)
            }
        }
        init {
            this.transactionDataList = transactionDataList
        }
    }