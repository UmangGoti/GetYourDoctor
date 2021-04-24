package com.example.getyourdoctor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.getyourdoctor.adapter.TransactionAdapter
import com.example.getyourdoctor.dataclass.TransactionData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class UserTransectionActivity : AppCompatActivity() {

    lateinit var mDatabase: DatabaseReference
    lateinit var mHospital: DatabaseReference
    var transactionDataList: ArrayList<TransactionData>? = null
    val uid = FirebaseAuth.getInstance().currentUser!!.getUid()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_transection)
        mDatabase = FirebaseDatabase.getInstance("https://getyourdoctor-acf57-default-rtdb.firebaseio.com/").getReference("Transaction")
        mHospital = FirebaseDatabase.getInstance("https://getyourdoctor-acf57-default-rtdb.firebaseio.com/").getReference()
        var mRecyclerView: RecyclerView = findViewById(R.id.transactionRecycleview)
        mRecyclerView.layoutManager = LinearLayoutManager(applicationContext)
        transactionDataList = ArrayList()
        mDatabase.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(p0: DataSnapshot) {
                transactionDataList!!.clear()
                for (datasnapshot: DataSnapshot in p0.children) {
                    var transactionData = datasnapshot.getValue(TransactionData::class.java)
                    if(transactionData!!.gettUserID().equals(uid)) {
                        (transactionDataList as ArrayList<TransactionData>).add(transactionData!! as TransactionData)
                    }
                }
                val transactionAdapter = applicationContext.let {
                    TransactionAdapter(
                        context = it,
                        transactionDataList = transactionDataList as ArrayList<TransactionData>
                    )
                }
                mRecyclerView.adapter = transactionAdapter
                transactionAdapter?.notifyDataSetChanged()
            }
            override fun onCancelled(p0: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
        mDatabase.keepSynced(true)
    }
}