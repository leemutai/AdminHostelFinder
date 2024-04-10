package com.example.adminhostel_locator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.adminhostel_locator.adapter.BookingStatusAdapter
import com.example.adminhostel_locator.databinding.ActivityBookingStatusBinding
import com.example.adminhostel_locator.model.BookingDetails
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class BookingStatusActivity : AppCompatActivity() {
    private val binding: ActivityBookingStatusBinding by lazy {
        ActivityBookingStatusBinding.inflate(layoutInflater)
    }
    private lateinit var database: FirebaseDatabase
    private  var listOfCompletedBookingList: ArrayList<BookingDetails> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.backButton.setOnClickListener {
            finish()
        }
        //retrieve and display completed booking
        retrieveCompletedBookingDetail()
    }

    private fun retrieveCompletedBookingDetail() {
        // initialize firebase database
        database = FirebaseDatabase.getInstance()
        val completedBookingReference = database.reference.child("CompletedBooking")
            .orderByChild("CurrentTime")
        completedBookingReference.addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                //clear the list before populating it with data
                listOfCompletedBookingList.clear()

                for (bookingSnapshot in snapshot.children){
                    val completeBooking = bookingSnapshot.getValue(BookingDetails::class.java)
                    completeBooking?.let {
                        listOfCompletedBookingList.add(it)
                    }
                }
                //reverse the list to display latest booking first
                listOfCompletedBookingList.reverse()

                setDataIntoRecyclerView()
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }

    private fun setDataIntoRecyclerView() {
        //initialize list to hold customers' names and payment status
        val customerName = mutableListOf<String>()
        val moneyStatus = mutableListOf<Boolean>()

        for (booking in listOfCompletedBookingList){
            booking.userName?.let {
                customerName.add(it)
            }
            moneyStatus.add(booking.paymentReceived)
        }
        val adapter = BookingStatusAdapter(customerName,moneyStatus)
        binding.bookingStatusRecyclerView.adapter = adapter
        binding.bookingStatusRecyclerView.layoutManager = LinearLayoutManager(this)
    }
}
