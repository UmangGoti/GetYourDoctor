package com.example.getyourdoctor.dataclass

class HospitalAppointmentData {
    private var tAge: String? = null
    private var tAppointmentDate: String? = null
    private var tBookingDate: String? = null
    private var tContact: String? = null
    private var tHospitalID: String? = null
    private var tName: String? = null
    private var tPaymentStatus: String? = null
    private var tUserID: String? = null
    private var tHospitalName: String? = null

    fun gettAge(): String? {
        return tAge
    }

    fun settAge(tAge: String?) {
        this.tAge = tAge
    }

    fun gettAppointmentDate(): String? {
        return tAppointmentDate
    }

    fun settAppointmentDate(tAppointmentDate: String?) {
        this.tAppointmentDate = tAppointmentDate
    }

    fun gettBookingDate(): String? {
        return tBookingDate
    }

    fun settBookingDate(tBookingDate: String?) {
        this.tBookingDate = tBookingDate
    }

    fun gettContact(): String? {
        return tContact
    }

    fun settContact(tContact: String?) {
        this.tContact = tContact
    }

    fun gettHospitalID(): String? {
        return tHospitalID
    }

    fun settHospitalID(tHospitalID: String?) {
        this.tHospitalID = tHospitalID
    }

    fun gettName(): String? {
        return tName
    }

    fun settName(tName: String?) {
        this.tName = tName
    }

    fun gettPaymentStatus(): String? {
        return tPaymentStatus
    }

    fun settPaymentStatus(tPaymentStatus: String?) {
        this.tPaymentStatus = tPaymentStatus
    }

    fun gettUserID(): String? {
        return tUserID
    }

    fun settUserID(tUserID: String?) {
        this.tUserID = tUserID
    }

    fun gettHospitalName(): String? {
        return tHospitalName
    }

    fun settHospitalName(tHospitalName: String?) {
        this.tHospitalName = tHospitalName
    }
}