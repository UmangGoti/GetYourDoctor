package com.example.getyourdoctor.dataclass

class MedicalData {
    private var mName: String? = null
    private var mAdd: String? = null
    private var mLat: Double? = null
    private var mLong: Double? = null
    private var mTime1: String? = null
    private var mTime2: String? = null
    private  var mPhone: String? =  null

    fun getmName(): String? {
        return mName
    }

    fun setmName(mName: String?) {
        this.mName = mName
    }

    fun getmAdd(): String? {
        return mAdd
    }

    fun setmAdd(mAdd: String?) {
        this.mAdd = mAdd
    }

    fun getmLat(): Double? {
        return mLat
    }

    fun setmLat(mLat: Double?) {
        this.mLat = mLat
    }

    fun getmLong(): Double? {
        return mLong
    }

    fun setmLong(mLong: Double?) {
        this.mLong = mLong
    }

    fun getmTime1(): String? {
        return mTime1
    }

    fun setmTime1(mTime1: String?) {
        this.mTime1 = mTime1
    }

    fun getmTime2(): String? {
        return mTime2
    }

    fun setmTime2(mTime2: String?) {
        this.mTime2 = mTime2
    }

    fun getmPhone(): String? {
        return mPhone
    }

    fun setmPhone(mPhone: String?) {
        this.mPhone = mPhone
    }
}