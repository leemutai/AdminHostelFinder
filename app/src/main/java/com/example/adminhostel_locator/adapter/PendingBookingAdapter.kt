package com.example.adminhostel_locator.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.textclassifier.ConversationActions.Message
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.adminhostel_locator.databinding.PendingBookingPropertyBinding

class PendingBookingAdapter(
    private val customerNames: ArrayList<String>,
    private val listingPrice: ArrayList<String>,
    private val listingImage: ArrayList<Int>,
    private val context: Context
) : RecyclerView.Adapter<PendingBookingAdapter.PendingViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PendingViewHolder {
        val binding = PendingBookingPropertyBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return PendingViewHolder(binding)
    }


    override fun onBindViewHolder(holder: PendingViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = customerNames.size
    inner class PendingViewHolder(private val binding: PendingBookingPropertyBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private var isAccepted = false
        fun bind(position: Int) {
            binding.apply {
                customerName.text = customerNames[position]
                pendingPropertyPrice.text = listingPrice[position]
                bookedPropertyImage.setImageResource(listingImage[position])

                bookingAcceptButton.apply {
                    if (!isAccepted) {
                        text = "Accept"
                    } else {
                        text = "Dispatch"
                    }
                    setOnClickListener {
                        if (!isAccepted) {
                            text = "Allocated"
                            isAccepted = true
                            showToast("Booking Is accepted")
                        } else {
                            customerNames.removeAt(adapterPosition)
                            notifyItemRemoved(adapterPosition)
                            showToast("House Allocated")
                        }
                    }
                }

            }

        }
        private fun showToast(message: String) {
            Toast.makeText(context,message, Toast.LENGTH_SHORT).show()
        }

    }
}