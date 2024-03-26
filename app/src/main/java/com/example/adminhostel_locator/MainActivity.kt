package com.example.adminhostel_locator

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.adminhostel_locator.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.addListings.setOnClickListener {
            val intent = Intent(this, AddPropertyActivity::class.java)
            startActivity(intent)
        }
        binding.allPropertyListing.setOnClickListener {
            val intent = Intent(this, AllPropertyActivity::class.java)
            startActivity(intent)
        }
        binding.bookingStatusButton.setOnClickListener {
            val intent = Intent(this,BookingStatusActivity::class.java)
            startActivity(intent)

        }
        binding.profile.setOnClickListener {
            val intent = Intent(this,AdminProfileActivity::class.java)
            startActivity(intent)

        }
        binding.createUser.setOnClickListener {
            val intent = Intent(this,CreateUserActivity::class.java)
            startActivity(intent)
        }
        binding.pendingBookingTextView.setOnClickListener {
            val intent = Intent(this,PendingBookingActivity::class.java)
            startActivity(intent)
        }
    }
}