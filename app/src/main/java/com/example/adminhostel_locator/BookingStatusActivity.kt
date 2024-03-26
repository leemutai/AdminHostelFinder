package com.example.adminhostel_locator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.adminhostel_locator.adapter.BookingStatusAdapter
import com.example.adminhostel_locator.databinding.ActivityBookingStatusBinding

class BookingStatusActivity : AppCompatActivity() {
    private val binding: ActivityBookingStatusBinding by lazy {
        ActivityBookingStatusBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.backButton.setOnClickListener {
            finish()
        }

        val customerName = arrayListOf("Joh Doe", "Visha Kumar", "Stacy Perks")
        val bookingStatus = arrayListOf("Approved", "Pending", "Cancelled")
        val adapter = BookingStatusAdapter(customerName, bookingStatus)
        binding.bookingStatusRecyclerView.adapter = adapter
        binding.bookingStatusRecyclerView.layoutManager = LinearLayoutManager(this)
    }
}