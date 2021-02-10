package com.example.getyourdoctor.dataclass

class HospitalData{
    private var hName: String? = null
    private var hTime: String? = null
    private var hAdd: String? = null
    private var hInfo: String? = null
    private var hLat: Double? = null
    private var hLong: Double? = null

    fun HospitalData(hName: String?, hAdd: String?,hInfo: String?, hTime: String?, hLat: Double?, hLong: Double?) {
        this.hName = hName
        this.hTime = hTime
        this.hAdd = hAdd
        this.hInfo = hInfo
        this.hLat = hLat
        this.hLong = hLong
    }

    fun gethName(): String? {
        return hName
    }

    fun sethName(hName: String?) {
        this.hName = hName
    }

    fun gethTime(): String? {
        return hTime
    }

    fun sethTime(hTime: String?) {
        this.hTime = hTime
    }

    fun gethAdd(): String? {
        return hAdd
    }

    fun sethAdd(hAdd: String?) {
        this.hAdd = hAdd
    }

    fun gethInfo(): String? {
        return hInfo
    }

    fun sethInfo(hInfo: String?) {
        this.hInfo = hInfo
    }

    fun gethLat(): Double? {
        return hLat
    }

    fun sethLat(hLat: Double?) {
        this.hLat = hLat
    }

    fun gethLong(): Double? {
        return hLong
    }

    fun sethLong(hLong: Double?) {
        this.hLong = hLong
    }
}
