package com.example.getyourdoctor.dataclass

class HospitalData{
    private var hospName: String? = null
    private var hospTime: String? = null
    private var hospAddress: String? = null
    private var hospInformation: String? = null

    fun HospitalData() {}

    fun HospitalData(hospName: String?, Address: String?,Information: String?, Time: String?) {
        this.hospName = hospName
        this.hospTime = Time
        this.hospAddress = Address
        this.hospInformation = Information
    }

    fun gethospName(): String? {
        return hospName
    }

    fun sethospName(hospName: String?) {
        this.hospName = hospName
    }

    fun gethospTime(): String? {
        return hospTime
    }

    fun sethospTime(Time: String?) {
        this.hospTime = Time
    }

    fun gethospAddress(): String? {
        return hospAddress
    }

    fun setAddress(Address: String?) {
        this.hospAddress = Address
    }

    fun gethospInformation(): String? {
        return hospInformation
    }

    fun setInformation(Information: String?) {
        this.hospInformation = Information
    }
}
