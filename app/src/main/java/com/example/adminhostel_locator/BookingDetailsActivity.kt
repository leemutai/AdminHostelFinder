package com.example.adminhostel_locator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.adminhostel_locator.adapter.BookingDetailsAdapter
import com.example.adminhostel_locator.databinding.ActivityBookingDetailsBinding
import com.example.adminhostel_locator.model.BookingDetails

class BookingDetailsActivity : AppCompatActivity() {
    private val binding: ActivityBookingDetailsBinding by lazy {
        ActivityBookingDetailsBinding.inflate(layoutInflater)
    }
    private var username :String? =null
    private var address: String?= null
    private var phoneNumber: String?= null
    private var totalPrice: String?= null

    private  var listingNames: ArrayList<String> = arrayListOf()
    private  var listingPrices: ArrayList<String> = arrayListOf()
    private  var listingImages: ArrayList<String> = arrayListOf()
     override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

         binding.backButton.setOnClickListener {
             finish()
         }
         getDataFromIntent()
    }

    private fun getDataFromIntent() {
        val receivedBookingDetails = intent.getSerializableExtra("UserBookingDetails") as BookingDetails
        receivedBookingDetails?.let {BookingDetails ->
                username = receivedBookingDetails.userName
                listingNames = receivedBookingDetails.listingNames as ArrayList<String>
                listingImages = receivedBookingDetails.listingImages as ArrayList<String>
                address = receivedBookingDetails.address
                phoneNumber = receivedBookingDetails.phoneNumber
                listingPrices = receivedBookingDetails.listingPrices as ArrayList<String>
                totalPrice = receivedBookingDetails.totalPrice


                setUserDetails()
                setAdapter()

        }

    }



    private fun setUserDetails() {
        binding.name.text = username
        binding.address.text = address
        binding.phone.text = phoneNumber
        binding.totalPay.text = totalPrice
    }

    private fun setAdapter() {
        binding.bookingDetailRecyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = BookingDetailsAdapter(this,listingNames,listingImages,listingPrices,)
        binding.bookingDetailRecyclerView.adapter = adapter
    }
}