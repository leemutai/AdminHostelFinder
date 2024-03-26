package com.example.adminhostel_locator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.adminhostel_locator.adapter.BookingStatusAdapter
import com.example.adminhostel_locator.adapter.PendingBookingAdapter
import com.example.adminhostel_locator.databinding.ActivityPendingBookingBinding
import com.example.adminhostel_locator.databinding.PendingBookingPropertyBinding

class PendingBookingActivity : AppCompatActivity() {
    private lateinit var binding:ActivityPendingBookingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPendingBookingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backButton.setOnClickListener {
            finish()
        }

        val bookedCustomerName = arrayListOf("Joh Doe", "Visha Kumar", "Stacy Perks")
        val bookedListingPrice = arrayListOf("Ksh10000", "Ksh5000", "Ksh6500")
        val bookedListingImage = arrayListOf(R.drawable.hostel,R.drawable.hostel2,R.drawable.hostel)
        val adapter = PendingBookingAdapter(bookedCustomerName, bookedListingPrice,bookedListingImage, this)
        binding.pendingBookingRecyclerView.adapter = adapter
        binding.pendingBookingRecyclerView.layoutManager = LinearLayoutManager(this)
    }
}