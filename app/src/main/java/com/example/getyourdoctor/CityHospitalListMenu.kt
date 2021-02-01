package com.example.getyourdoctor

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.getyourdoctor.adapter.HospitalAdapter
import com.example.getyourdoctor.dataclass.HospitalData
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_city_hospital_list_menu.view.*
import kotlin.collections.ArrayList


class CityHospitalListMenu : Fragment() {
    lateinit var databaseRefrence:DatabaseReference
    var city:String = "Bhavnagar"
    lateinit var i:Intent
    var hospitalDataList: ArrayList<HospitalData>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_city_hospital_list_menu, container, false)
        var recyclerView: RecyclerView = view.findViewById(R.id.hospitalRecycleview)
        recyclerView.layoutManager = LinearLayoutManager(this.context)
        hospitalDataList = ArrayList()
        databaseRefrence = FirebaseDatabase.getInstance("https://getyourdoctor-fc659-default-rtdb.firebaseio.com/").getReference(
            "/Hospitals/City"
        ).child(city)


        databaseRefrence.addValueEventListener(object : ValueEventListener {
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
        databaseRefrence.keepSynced(true)
        view.locationFab.text = databaseRefrence.key.toString()
        view.locationFab.setOnClickListener {
            i = Intent(view.context, CityList::class.java)
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(i)
        }
        return view
    }
}
