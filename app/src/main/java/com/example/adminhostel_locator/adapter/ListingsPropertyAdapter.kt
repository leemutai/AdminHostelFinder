package com.example.adminhostel_locator.adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.adminhostel_locator.databinding.PropertyListingBinding
import com.example.adminhostel_locator.model.AllListings
import com.google.firebase.database.DatabaseReference

class ListingsPropertyAdapter(
    private val context: Context,
    private val listingList: ArrayList<AllListings>,
    databaseReference: DatabaseReference
) :
    RecyclerView.Adapter<ListingsPropertyAdapter.AddListingsViewHolder>() {
    private val itemQuantities = IntArray(listingList.size) { 1 }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddListingsViewHolder {
         val binding = PropertyListingBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return AddListingsViewHolder(binding)
    }


    override fun onBindViewHolder(holder: AddListingsViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = listingList.size

    inner class AddListingsViewHolder(private val binding: PropertyListingBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            binding.apply {
                val quantity = itemQuantities[position]

                val listingsProperty = listingList[position]
                val uriString = listingsProperty.listingImage
                val uri :Uri = Uri.parse(uriString)

                listingsApartNameTv.text = listingsProperty.listingName
                listingsPriceTv.text = listingsProperty.listingPrice
                listingsRatingTv.text = listingsProperty.listingRating
                listingsLocationTv.text = listingsProperty.listingLocation
                listingsHseTypeTv.text = listingsProperty.listingHseType
                listingsBedSizeTv.text = listingsProperty.listingBedsize
                Glide.with(context).load(uri).into(listingsImageTv)

//                listingsRatingTv.text = listingList[position]
//                listingsLocationTv.text = listingList[position]
//                listingsHseTypeTv.text = listingList[position]
//                listingsBedSizeTv.text = listingList[position]


                deleteButton.setOnClickListener {
                    deleteQuantity(position)
                }
            }
        }

    }

    private fun deleteQuantity(position: Int) {
        listingList.removeAt(position)
        listingList.removeAt(position)
        listingList.removeAt(position)
        listingList.removeAt(position)
        listingList.removeAt(position)
        listingList.removeAt(position)
        listingList.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position,listingList.size)
    }
}