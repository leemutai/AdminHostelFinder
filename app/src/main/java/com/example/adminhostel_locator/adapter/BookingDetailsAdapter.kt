package com.example.adminhostel_locator.adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.adminhostel_locator.databinding.BookDetailsListingsBinding

class BookingDetailsAdapter(
    private var context: Context,
    private var listingNames: ArrayList<String>,
    private var listingPrices: ArrayList<String>,
    private var listingImages: ArrayList<String>,
) : RecyclerView.Adapter<BookingDetailsAdapter.BookingDetailsViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookingDetailsViewHolder {
         val binding = BookDetailsListingsBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return BookingDetailsViewHolder(binding)
    }



    override fun onBindViewHolder(holder: BookingDetailsViewHolder, position: Int) {
         holder.bind(position)
    }
    override fun getItemCount(): Int = listingNames.size
    inner class BookingDetailsViewHolder(private val binding: BookDetailsListingsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            binding.apply {
                listingsName.text = listingNames[position]
                val uriString = listingImages[position]
                val uri = Uri.parse(uriString)
                Glide.with(context).load(uri).into(listingImage)
                listingsPrice.text = listingPrices[position]
            }
        }

    }
}