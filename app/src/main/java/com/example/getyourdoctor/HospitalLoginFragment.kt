package com.example.getyourdoctor

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.getyourdoctor.adapter.HospitalAppointmentAdapter
import com.example.getyourdoctor.adapter.TransactionAdapter
import com.example.getyourdoctor.dataclass.HospitalAppointmentData
import com.example.getyourdoctor.dataclass.TransactionData
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_hospital_login.*
import kotlinx.android.synthetic.main.fragment_hospital_login.view.*
import kotlinx.android.synthetic.main.fragment_login.view.*

class HospitalLoginFragment : Fragment() {
    lateinit var databaseReference: DatabaseReference
    var hospitalAppointmentDataList: ArrayList<HospitalAppointmentData>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.fragment_hospital_login, container, false)
        var mRecyclerView: RecyclerView = view.findViewById(R.id.hospitalAppointmentRecycleview)
        mRecyclerView.layoutManager = LinearLayoutManager(context)
        hospitalAppointmentDataList = ArrayList()
        databaseReference = FirebaseDatabase.getInstance("https://getyourdoctor-acf57-default-rtdb.firebaseio.com/").getReference("Transaction")
        view.hospitalLogin.setOnClickListener {
            if(view.hloginEt.text.toString().isNotEmpty() && view.hloginEt.text.toString().isNotBlank()){
                databaseReference.addValueEventListener(object : ValueEventListener{
                    override fun onDataChange(p0: DataSnapshot) {
                        if(p0.childrenCount>0) {
                            view.hospitalLogin.visibility = View.GONE
                            view.hloginEt.visibility = View.GONE
                            view.hospitalAppointmentRecycleview.visibility = View.VISIBLE
                            view.logoutIcon.visibility = View.VISIBLE
                            hospitalAppointmentDataList!!.clear()
                            for (datasnapshot: DataSnapshot in p0.children) {
                                Log.i("Main Info", datasnapshot.toString())
                                var hospitalAppointmentData = datasnapshot.getValue(HospitalAppointmentData::class.java)
                                if (view.hloginEt.text.toString().equals(hospitalAppointmentData!!.gettHospitalID()) && hospitalAppointmentData!!.gettPaymentStatus().equals("Authorized") ){
                                    (hospitalAppointmentDataList as ArrayList<HospitalAppointmentData>).add(hospitalAppointmentData!! as HospitalAppointmentData)
                                }
                            }
                            val hospitalAppointmentAdapter = context?.let { it1 ->
                                HospitalAppointmentAdapter(
                                    it1, hospitalAppointmentDataList as ArrayList<HospitalAppointmentData>
                                )
                            }
                            view.hospitalAppointmentRecycleview.adapter = hospitalAppointmentAdapter
                            hospitalAppointmentAdapter?.notifyDataSetChanged()
                            view.logoutIcon.setOnClickListener {
                                view.hospitalLogin.visibility = View.VISIBLE
                                view.hloginEt.visibility = View.VISIBLE
                                view.hospitalAppointmentRecycleview.visibility = View.GONE
                                view.logoutIcon.visibility = View.GONE
                            }
                        }else{
                            view.hospitalLogin.visibility = View.VISIBLE
                            view.hloginEt.visibility = View.VISIBLE
                            view.hospitalAppointmentRecycleview.visibility = View.GONE
                            view.logoutIcon.visibility = View.GONE
                            Toast.makeText(context,"No Appointment",Toast.LENGTH_SHORT)
                        }
                    }

                    override fun onCancelled(p0: DatabaseError) {

                    }
                })
            }else{
                Toast.makeText(context,"Enter Hospital Id",Toast.LENGTH_SHORT)
            }

            databaseReference.keepSynced(true)
        }

        return view
    }
}