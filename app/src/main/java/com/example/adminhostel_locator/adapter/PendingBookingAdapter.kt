// PendingBookingAdapter.kt
package com.example.adminhostel_locator.adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.adminhostel_locator.databinding.PendingBookingPropertyBinding

class PendingBookingAdapter(
    private val context: Context,
    private val customerNames: MutableList<String>,
    private val listingPrice: MutableList<String>,
    private val listingImages: MutableList<String>,
    private val itemClicked: OnItemClickLed,
) : RecyclerView.Adapter<PendingBookingAdapter.PendingViewHolder>() {
    interface OnItemClickLed {
        fun onItemClickListener(position: Int)
        fun onItemAcceptClickListener(position: Int)
        fun onItemAllocatedClickListener(position: Int)
    }

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
                if (position < customerNames.size && position < listingPrice.size && position < listingImages.size) {
                    customerName.text = customerNames[position]
                    listingsPrice.text = listingPrice[position]
                    val uriString = listingImages[position]
                    val uri = Uri.parse(uriString)
                    Glide.with(context).load(uri).into(listingImage)
                } else {
                    // Handle the case when lists are empty or out of bounds
                    // You can set default values or display a placeholder
                }

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
                            if (adapterPosition != RecyclerView.NO_POSITION) {
                                itemClicked.onItemAcceptClickListener(adapterPosition)
                            }
                        } else {
                            if (adapterPosition != RecyclerView.NO_POSITION) {
                                if (customerNames.size > adapterPosition && listingPrice.size > adapterPosition && listingImages.size > adapterPosition) {
                                    customerNames.removeAt(adapterPosition)
                                    listingPrice.removeAt(adapterPosition)
                                    listingImages.removeAt(adapterPosition)
                                    notifyItemRemoved(adapterPosition)
                                    showToast("House Allocated")
                                    itemClicked.onItemAllocatedClickListener(adapterPosition)
                                }
                            }
                        }
                    }
                }
                itemView.setOnClickListener {
                    itemClicked.onItemClickListener(position)
                }
            }
        }

        private fun showToast(message: String) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }
}
