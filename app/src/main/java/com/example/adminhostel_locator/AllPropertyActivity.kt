package com.example.adminhostel_locator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.adminhostel_locator.adapter.AddListingsAdapter
import com.example.adminhostel_locator.databinding.ActivityAllPropertyBinding

class AllPropertyActivity : AppCompatActivity() {
    private val binding: ActivityAllPropertyBinding by lazy {
        ActivityAllPropertyBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val listingItemName = listOf("Baraka", "Nyayo", "Phase", "Wema")
        val listingItemPrice = listOf("Ksh10000", "Ksh4500", "Ksh7500", "Ksh8000")
        val listingRating = listOf("4.9", "3.5", "3.3", "2.0")
        val listingLocation = listOf("Kahawa", "Langata", "Westy", "Imara")
        val listingHseType = listOf("Apartment", "Studio", "2bd", "3bd")
        val listingBed = listOf("3.0", "1.0", "2.0", "3.0")
        val listingImage = listOf(
            R.drawable.hostel,
            R.drawable.hostel2,
            R.drawable.hostel,
            R.drawable.hostel2,
        )
        binding.backButton.setOnClickListener {
            finish()
        }
        val adapter = AddListingsAdapter(
           ArrayList(listingItemName) ,
            ArrayList(listingItemPrice),
            ArrayList(listingRating),
            ArrayList(listingLocation),
            ArrayList(listingHseType),
            ArrayList(listingBed),
            ArrayList(listingImage)
        )
        binding.ListingsRecylerView.layoutManager = LinearLayoutManager(this)
        binding.ListingsRecylerView.adapter = adapter
    }
}