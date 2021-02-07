package com.example.getyourdoctor

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.getyourdoctor.adapter.MedicalAdapter
import com.example.getyourdoctor.dataclass.MedicalData
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_city_hospital_list_menu.*
import kotlinx.android.synthetic.main.fragment_city_hospital_list_menu.view.*

class MedicalListMenu : Fragment() {
    lateinit var databaseRefrence: DatabaseReference
    lateinit var i: Intent
    var medicalDataList: ArrayList<MedicalData>? = null
    var spinnerDataList:ArrayList<String>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_medical_list_menu, container, false)
        spinnerDataList = ArrayList<String>()
        databaseRefrence = FirebaseDatabase.getInstance("https://getyourdoctor-fc659-default-rtdb.firebaseio.com/").getReference(
            "/Medicals/City"
        )
        databaseRefrence.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                spinnerDataList!!.clear()
                for (datasnapshot: DataSnapshot in p0.children) {
                    datasnapshot.key?.let { spinnerDataList?.add(it) }
                }
                var cityAdapter = context?.let { ArrayAdapter<String>(it,R.layout.support_simple_spinner_dropdown_item,
                    spinnerDataList!!
                ) }
                citySpinner?.adapter = cityAdapter
                cityAdapter?.notifyDataSetChanged()
            }
            override fun onCancelled(p0: DatabaseError) {
                Log.e("ERROR", p0.toString())
            }

        })
        databaseRefrence.keepSynced(true)

        var recyclerView: RecyclerView = view.findViewById(R.id.medicalRecycleview)
        recyclerView.layoutManager = LinearLayoutManager(context)
        view.citySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener,
            AdapterView.OnItemClickListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                medicalDataList = ArrayList()
                var databaseRefrence1 = FirebaseDatabase.getInstance("https://getyourdoctor-fc659-default-rtdb.firebaseio.com/").getReference(
                    "/Medicals/City"
                ).child(spinnerDataList!![p2])
                databaseRefrence1.addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(p0: DataSnapshot) {
                        medicalDataList!!.clear()
                        for (datasnapshot: DataSnapshot in p0.children) {
                            var medicaldata = datasnapshot.getValue(MedicalData::class.java)
                            (medicalDataList as ArrayList<MedicalData>).add(medicaldata as MedicalData)
                        }
                        val medicalAdapter = context?.let {
                            MedicalAdapter(
                                context = it,
                                medicalDataList = medicalDataList as ArrayList<MedicalData>
                            )
                        }
                        recyclerView.adapter = medicalAdapter
                        medicalAdapter?.notifyDataSetChanged()
                    }

                    override fun onCancelled(p0: DatabaseError) {
                        Log.e("ERROR", p0.toString())
                    }

                })
                databaseRefrence1.keepSynced(true)
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

            override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                TODO("Not yet implemented")
            }

        }
        return view
    }

}