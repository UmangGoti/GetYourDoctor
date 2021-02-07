package com.example.getyourdoctor.dataclass

class HospitalData{
    private var hName: String? = null
    private var hTime: String? = null
    private var hAdd: String? = null
    private var hInfo: String? = null

    fun HospitalData(hName: String?, hAdd: String?,hInfo: String?, hTime: String?) {
        this.hName = hName
        this.hTime = hTime
        this.hAdd = hAdd
        this.hInfo = hInfo
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
}
