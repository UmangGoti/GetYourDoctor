package com.example.getyourdoctor.dataclass

class MedicalData {
    private var mName: String? = null
    private var mTime: String? = null


    fun getmName(): String? {
        return mName
    }

    fun setmName(mName: String?) {
        this.mName = mName
    }

    fun getmTime(): String? {
        return mTime
    }

    fun setmTime(Time: String?) {
        this.mTime = Time
    }
}