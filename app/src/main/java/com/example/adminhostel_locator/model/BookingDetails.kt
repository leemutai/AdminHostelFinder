package com.example.adminhostel_locator.model

import android.os.Parcel
import android.os.Parcelable

class BookingDetails():Parcelable {
    var userUid:String? = null
    var userName:String? = null
    var listingNames:MutableList<String>? = null
    var listingPrices:MutableList<String>? = null
    var listingRatings:MutableList<String>? = null
    var listingLocations:MutableList<String>? = null
    var listingHseTypes:MutableList<String>? = null
    var listingBedsizes:MutableList<String>? = null
    var listingImages:MutableList<String>? = null
    var address : String? = null
    var totalPrice : String? = null
    var phoneNumber : String? = null
    var bookingAccepted : Boolean = false
    var paymentReceived : Boolean = false
    var listingPushKey : String? = null
    var currentTime : Long = 0

    constructor(parcel: Parcel) : this() {
        userUid = parcel.readString()
        userName = parcel.readString()
        address = parcel.readString()
        totalPrice = parcel.readString()
        phoneNumber = parcel.readString()
        bookingAccepted = parcel.readByte() != 0.toByte()
        paymentReceived = parcel.readByte() != 0.toByte()
        listingPushKey = parcel.readString()
        currentTime = parcel.readLong()
    }

    override fun describeContents(): Int {
        TODO("Not yet implemented")
    }

    override fun writeToParcel(p0: Parcel, p1: Int) {
        TODO("Not yet implemented")
    }

    companion object CREATOR : Parcelable.Creator<BookingDetails> {
        override fun createFromParcel(parcel: Parcel): BookingDetails {
            return BookingDetails(parcel)

        }

        override fun newArray(size: Int): Array<BookingDetails?> {
            return arrayOfNulls(size)
        }
    }
}