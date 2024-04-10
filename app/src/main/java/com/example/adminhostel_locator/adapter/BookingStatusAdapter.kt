package com.example.adminhostel_locator.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.adminhostel_locator.databinding.BookedPropertyBinding

class BookingStatusAdapter(
    private val customerNames: MutableList<String>,
    private val moneyStatus: MutableList<Boolean>,
) : RecyclerView.Adapter<BookingStatusAdapter.BookingStatusViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookingStatusViewHolder {
        val binding =
            BookedPropertyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BookingStatusViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BookingStatusViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = customerNames.size

    inner class BookingStatusViewHolder(private val binding: BookedPropertyBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {

            binding.apply {
                customerName.text = customerNames[position]
                if (moneyStatus[position]) {
                    statusMoney.text = "Approved"
                    statusColor.setCardBackgroundColor(Color.GREEN)
                } else {
                    statusMoney.text = "Not Received"
                    statusColor.setCardBackgroundColor(Color.RED)
                }
            }
        }
    }
}
