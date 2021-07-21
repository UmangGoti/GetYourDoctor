package com.example.getyourdoctor

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.getyourdoctor.adapter.HospitalAdapter
import com.example.getyourdoctor.dataclass.HospitalData
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_city_hospital_list_menu.*
import kotlinx.android.synthetic.main.fragment_city_hospital_list_menu.view.*


class CityHospitalListMenu : Fragment() {
    lateinit var databaseRefrence: DatabaseReference
    var hospitalDataList: ArrayList<HospitalData>? = null
    var spinnerDataList: ArrayList<String>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View =
            inflater.inflate(R.layout.fragment_city_hospital_list_menu, container, false)
        spinnerDataList = ArrayList<String>()
        databaseRefrence =
            FirebaseDatabase.getInstance("https://getyourdoctor-acf57-default-rtdb.firebaseio.com/")
                .getReference(
                    "/Hospitals/City"
                )
        databaseRefrence.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                spinnerDataList!!.clear()
                for (datasnapshot: DataSnapshot in p0.children) {
                    datasnapshot.key?.let { spinnerDataList?.add(it) }
                }
                var cityAdapter = context?.let {
                    ArrayAdapter<String>(
                        it, R.layout.support_simple_spinner_dropdown_item,
                        spinnerDataList!!
                    )
                }
                citySpinner?.adapter = cityAdapter
                cityAdapter?.notifyDataSetChanged()
            }

            override fun onCancelled(p0: DatabaseError) {
                Log.e("ERROR", p0.toString())
            }

        })
        databaseRefrence.keepSynced(true)

        var recyclerView: RecyclerView = view.findViewById(R.id.hospitalRecycleview)
        recyclerView.layoutManager = LinearLayoutManager(context)
        view.citySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener,
            AdapterView.OnItemClickListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                hospitalDataList = ArrayList()
                var databaseRefrence1 =
                    FirebaseDatabase.getInstance("https://getyourdoctor-acf57-default-rtdb.firebaseio.com/")
                        .getReference(
                            "/Hospitals/City"
                        ).child(spinnerDataList!![p2])
                databaseRefrence1.addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(p0: DataSnapshot) {
                        hospitalDataList!!.clear()
                        for (datasnapshot: DataSnapshot in p0.children) {
                            var hospitaldata = datasnapshot.getValue(HospitalData::class.java)
                            (hospitalDataList as ArrayList<HospitalData>).add(hospitaldata as HospitalData)
                        }
                        val hospitalAdapter = context?.let {
                            HospitalAdapter(
                                context = it,
                                hospitalDataList = hospitalDataList as ArrayList<HospitalData>
                            )
                        }
                        recyclerView.adapter = hospitalAdapter
                        hospitalAdapter?.notifyDataSetChanged()
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
