package com.example.adminhostel_locator.adapter

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.adminhostel_locator.databinding.BookedPropertyBinding

class BookingStatusAdapter(private val customerNames: ArrayList<String>, private val moneyStatus: ArrayList<String>) : RecyclerView.Adapter<BookingStatusAdapter.BookingStatusViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookingStatusViewHolder {
        val binding = BookedPropertyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BookingStatusViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BookingStatusViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = customerNames.size

    inner class BookingStatusViewHolder(private val binding: BookedPropertyBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {

            binding.apply {
                customerName.text = customerNames[position]
                statusMoney.text = moneyStatus[position]
                val colorMap = mapOf(
                    "Approved" to Color.GREEN,
                    "Pending" to Color.GRAY,
                    "Cancelled" to Color.RED
                )
                statusMoney.setTextColor(colorMap[moneyStatus[position]] ?: Color.BLACK)
                statusColor.backgroundTintList = ColorStateList.valueOf(colorMap[moneyStatus[position]] ?: Color.BLACK)
            }
        }
    }
}
