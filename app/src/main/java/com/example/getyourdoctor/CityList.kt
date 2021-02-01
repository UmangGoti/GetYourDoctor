package com.example.getyourdoctor

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.getyourdoctor.adapter.CityAdapter
import com.google.firebase.database.*


class CityList : AppCompatActivity() {
    lateinit var firebaseDatabase:FirebaseDatabase
    lateinit var databaseRefrence: DatabaseReference
    lateinit var cityList:ArrayList<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_city_list)
        var recyclerView:RecyclerView = findViewById(R.id.cityRecycleview)
        recyclerView.layoutManager = LinearLayoutManager(this.applicationContext)
        cityList = ArrayList()
        databaseRefrence = FirebaseDatabase.getInstance("https://getyourdoctor-fc659-default-rtdb.firebaseio.com/").getReference(
            "/Hospitals/City"
        )
        databaseRefrence.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                cityList.clear()
                for (dataSnapshot: DataSnapshot in p0.children) {
                    cityList.add(dataSnapshot.key as String)
                }
                val cityAdapter = applicationContext?.let {
                    CityAdapter(
                        context = it,
                        cityList = cityList

                    )
                }
                recyclerView.adapter = cityAdapter
                recyclerView.layoutManager = LinearLayoutManager(this@CityList)
                var dividerItemDecoration:DividerItemDecoration = DividerItemDecoration(this@CityList,DividerItemDecoration.VERTICAL)
                dividerItemDecoration.setDrawable(resources.getDrawable(R.drawable.recyclerview_devider))
                recyclerView.addItemDecoration(dividerItemDecoration)
                cityAdapter?.notifyDataSetChanged()
            }

            override fun onCancelled(p0: DatabaseError) {
                Log.e("ERROR", p0.toString())
            }

        })
    }
}