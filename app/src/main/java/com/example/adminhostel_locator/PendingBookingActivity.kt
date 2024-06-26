package com.example.adminhostel_locator

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.adminhostel_locator.adapter.PendingBookingAdapter
import com.example.adminhostel_locator.databinding.ActivityPendingBookingBinding
import com.example.adminhostel_locator.model.BookingDetails
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class PendingBookingActivity : AppCompatActivity(), PendingBookingAdapter.OnItemClickLed {
    private lateinit var binding: ActivityPendingBookingBinding
    private var listOfName: MutableList<String> = mutableListOf()
    private var listOfTotalPrice: MutableList<String> = mutableListOf()
    private var listOfImageFirstListingBook: MutableList<String> = mutableListOf()
    private var listOfBookingListing: ArrayList<BookingDetails> = arrayListOf()
    private lateinit var database: FirebaseDatabase
    private lateinit var databaseBookingDetails: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPendingBookingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //initialization of database
        database = FirebaseDatabase.getInstance()
        //initialization of database reference
        databaseBookingDetails = database.reference.child("BookingDetails")

        getBookingDetails()

        binding.backButton.setOnClickListener {
            finish()
        }
    }

    private fun getBookingDetails() {
        //retrieve booking details from Firebase database
        databaseBookingDetails.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (bookingSnapshot in snapshot.children) {
                    val bookingDetails = bookingSnapshot.getValue(BookingDetails::class.java)
                    bookingDetails?.let {
                        listOfBookingListing.add(it)
                    }
                }
                addDataToListForRecyclerView()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    private fun addDataToListForRecyclerView() {
        for (bookingListing in listOfBookingListing) {
            //add data to respective list for populating the recycler view
            bookingListing.userName?.let { listOfName.add(it) }
            bookingListing.totalPrice?.let { listOfTotalPrice.add(it) }
            bookingListing.listingImages?.filterNot { it.isEmpty() }?.forEach {
                listOfImageFirstListingBook.add(it)
            }
        }
        setAdapter()
    }

    private fun setAdapter() {
        binding.pendingBookingRecyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = PendingBookingAdapter(
            this,
            listOfName,
            listOfTotalPrice,
            listOfImageFirstListingBook,
            this
        )
        binding.pendingBookingRecyclerView.adapter = adapter
    }

    override fun onItemClickListener(position: Int) {
        val intent = Intent(this, BookingDetailsActivity::class.java)
        val userBookingDetails = listOfBookingListing[position]
        intent.putExtra("UserBookingDetails", userBookingDetails)
        startActivity(intent)
    }

    override fun onItemAcceptClickListener(position: Int) {
        //handle items acceptance and update database
        val childListingPushKey = listOfBookingListing[position].listingPushKey
        val clickListingBookingReference = childListingPushKey?.let {
            database.reference.child("BookingDetails").child(it)
        }
        clickListingBookingReference?.child("AcceptedBooking")?.setValue(true)
        updateBookingAcceptStatus(position)
    }


    override fun onItemAllocatedClickListener(position: Int) {
        //handle items acceptance and update database
        val acceptBookingPushKey = listOfBookingListing[position].listingPushKey
        val acceptListingBookingReference = database.reference.child("CompletedBooking").child(acceptBookingPushKey!!)
        acceptListingBookingReference.setValue(listOfBookingListing[position])
            .addOnSuccessListener {
                deleteThisListingFromBookingDetails(acceptBookingPushKey)
            }

    }

    private fun deleteThisListingFromBookingDetails(acceptBookingPushKey: String) {
        val bookingDetailsReference = database.reference.child("BookingDetails").child(acceptBookingPushKey)
        bookingDetailsReference.removeValue()
            .addOnSuccessListener {
                Toast.makeText(this, "Booking Is Allocated", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Booking is not Allocated", Toast.LENGTH_SHORT).show()
            }
    }

    private fun updateBookingAcceptStatus(position: Int) {

        //update booking acceptace in booking details
        val userIdOfClickedListing = listOfBookingListing[position].userUid
        val pushKeyOfClickedListing = listOfBookingListing[position].listingPushKey ?: ""
        databaseBookingDetails.child(pushKeyOfClickedListing).child("AcceptedBooking")
            .setValue(true)
    }
}
