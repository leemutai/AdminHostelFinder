package com.example.adminhostel_locator

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.adminhostel_locator.databinding.ActivityMainBinding
import com.example.adminhostel_locator.model.BookingDetails
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private lateinit var database: FirebaseDatabase
    private lateinit var auth:FirebaseAuth
    private lateinit var completedBookingReference : DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
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
        binding.logoutButton.setOnClickListener {
            auth.signOut()
            startActivity(Intent(this,SignUpActivity::class.java))
            finish()
        }

        pendingBookings()

        completedBookings()

        wholeTimeEarnings()
    }

    private fun wholeTimeEarnings() {
        var listOfTotalPay = mutableListOf<Int>()
        completedBookingReference = FirebaseDatabase.getInstance().reference.child("CompletedBooking")
        completedBookingReference.addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
              for (bookingSnapshot in snapshot.children){
                  var completeBooking = bookingSnapshot.getValue(BookingDetails::class.java)
                  completeBooking?.totalPrice?.replace("Ksh","")?.toIntOrNull()?.let {
                      i ->
                      listOfTotalPay.add(i)
                  }
              }
                binding.wholeTimeEarning.text = listOfTotalPay.sum().toString() + "Ksh"
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }

    private fun completedBookings() {
        val completedBookingReference = database.reference.child("CompletedBooking")
        var completedBookingItemCount = 0
        completedBookingReference.addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                completedBookingItemCount = snapshot.childrenCount.toInt()
                binding.completedBookings.text = completedBookingItemCount.toString()
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }

    private fun pendingBookings() {
        database = FirebaseDatabase.getInstance()
        val pendingBookingReference = database.reference.child("BookingDetails")
        var pendingBookingItemCount = 0
        pendingBookingReference.addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                pendingBookingItemCount = snapshot.childrenCount.toInt()
                binding.pendingBookings.text = pendingBookingItemCount.toString()
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }
}