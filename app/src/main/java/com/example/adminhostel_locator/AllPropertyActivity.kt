package com.example.adminhostel_locator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
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

        val adapter = ListingsPropertyAdapter(this@AllPropertyActivity,listingsPropertys,databaseReference){position ->
          deleteListingProperties(position)
        }

        binding.ListingsRecylerView.layoutManager = LinearLayoutManager(this)
        binding.ListingsRecylerView.adapter = adapter
    }

    private fun deleteListingProperties(position: Int) {
        val listingPropertyToDelete = listingsPropertys[position]
        val listingPropertyKey = listingPropertyToDelete.key
        val listingNameReference = database.reference.child("listing").child(listingPropertyKey!!)
        listingNameReference.removeValue().addOnCompleteListener { task ->
            if (task.isSuccessful){
                listingsPropertys.removeAt(position)
                binding.ListingsRecylerView.adapter?.notifyItemRemoved(position)
            }else{
                Toast.makeText(this, "Listing Not deleted", Toast.LENGTH_SHORT).show()
            }
        }
    }
}