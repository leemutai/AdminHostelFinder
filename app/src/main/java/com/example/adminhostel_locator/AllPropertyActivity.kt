package com.example.adminhostel_locator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.adminhostel_locator.adapter.ListingsPropertyAdapter
import com.example.adminhostel_locator.databinding.ActivityAllPropertyBinding
import com.example.adminhostel_locator.model.AllListings
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class AllPropertyActivity : AppCompatActivity() {
    private lateinit var databaseReference: DatabaseReference
    private lateinit var database: FirebaseDatabase
    private   var listingsPropertys: ArrayList<AllListings> = ArrayList()
    private val binding: ActivityAllPropertyBinding by lazy {
        ActivityAllPropertyBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        databaseReference = FirebaseDatabase.getInstance().reference
        retrieveListingsProperty()

//        val listingItemName = listOf("Baraka", "Nyayo", "Phase", "Wema")
//        val listingItemPrice = listOf("Ksh10000", "Ksh4500", "Ksh7500", "Ksh8000")
//        val listingRating = listOf("4.9", "3.5", "3.3", "2.0")
//        val listingLocation = listOf("Kahawa", "Langata", "Westy", "Imara")
//        val listingHseType = listOf("Apartment", "Studio", "2bd", "3bd")
//        val listingBed = listOf("3.0", "1.0", "2.0", "3.0")
//        val listingImage = listOf(
//            R.drawable.hostel,
//            R.drawable.hostel2,
//            R.drawable.hostel,
//            R.drawable.hostel2,
//        )
        binding.backButton.setOnClickListener {
            finish()
        }

    }

    private fun retrieveListingsProperty() {
        database = FirebaseDatabase.getInstance()
        val listingRef: DatabaseReference = database.reference.child("listing")

        //fetch data from database
        listingRef.addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                //clear existing data before populating
                listingsPropertys.clear()
                //loop for each listing property
                for (listingSnapshot in snapshot.children){
                    val listingsProperty:AllListings? = listingSnapshot.getValue(AllListings::class.java)
                    listingsProperty?.let {
                        listingsPropertys.add(it)
                    }
                }
                setAdapter()
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("DatabaseError", "Error: ${error.message}")
            }
        })
    }

    private fun setAdapter() {
        val adapter = ListingsPropertyAdapter(this@AllPropertyActivity,listingsPropertys,databaseReference)
        binding.ListingsRecylerView.layoutManager = LinearLayoutManager(this)
        binding.ListingsRecylerView.adapter = adapter
    }
}