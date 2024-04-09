package com.example.adminhostel_locator.adapter

import android.content.res.ColorStateList
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
                } else {
                    statusMoney.text = "notReceived"
                }
                val colorMap = mapOf(
                    true to Color.GREEN,
                    false to Color.RED,
                    "Pending" to Color.GRAY,
                )
                statusMoney.setTextColor(colorMap[moneyStatus[position]] ?: Color.BLACK)
                statusColor.backgroundTintList =
                    ColorStateList.valueOf(colorMap[moneyStatus[position]] ?: Color.BLACK)
            }
        }
    }
}
