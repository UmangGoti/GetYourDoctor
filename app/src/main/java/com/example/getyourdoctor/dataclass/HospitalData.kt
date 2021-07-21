package com.example.getyourdoctor.dataclass

class HospitalData {
    private var hName: String? = null
    private var hAdd: String? = null
    private var hId: String? = null
    private var hInfo: String? = null
    private var hLat: Double? = null
    private var hLong: Double? = null
    private var hTime1: String? = null
    private var hTime2: String? = null
    private var hPhone: String? = null

    fun gethId(): String? {
        return hId
    }

    fun sethId(hId: String?) {
        this.hId = hId
    }

    fun gethName(): String? {
        return hName
    }

    fun sethName(hName: String?) {
        this.hName = hName
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

    fun gethTime1(): String? {
        return hTime1
    }

    fun sethTime1(hTime1: String?) {
        this.hTime1 = hTime1
    }

    fun gethTime2(): String? {
        return hTime2
    }

    fun sethTime2(hTime2: String?) {
        this.hTime2 = hTime2
    }

    fun gethPhone(): String? {
        return hPhone
    }

    fun sethPhone(hPhone: String?) {
        this.hPhone = hPhone
    }
}
