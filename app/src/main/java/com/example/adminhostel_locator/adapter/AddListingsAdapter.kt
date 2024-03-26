package com.example.adminhostel_locator.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.adminhostel_locator.databinding.PropertyListingBinding

class AddListingsAdapter(
    private val ListingItemsName: ArrayList<String>,
    private val ListingItemPrice: ArrayList<String>,
    private val ListingRating: ArrayList<String>,
    private val ListingLocation: ArrayList<String>,
    private val ListingHseType: ArrayList<String>,
    private val ListingBed: ArrayList<String>,
    private val ListingImage: ArrayList<Int>,

    ) :
    RecyclerView.Adapter<AddListingsAdapter.AddListingsViewHolder>() {
    private val itemQuantities = IntArray(ListingItemsName.size) { 1 }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddListingsViewHolder {
         val binding = PropertyListingBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return AddListingsViewHolder(binding)
    }


    override fun onBindViewHolder(holder: AddListingsViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = ListingItemsName.size

    inner class AddListingsViewHolder(private val binding: PropertyListingBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            binding.apply {
                val quantity = itemQuantities[position]
                listingsApartNameTv.text = ListingItemsName[position]
                listingsPriceTv.text = ListingItemPrice[position]
                listingsRatingTv.text = ListingRating[position]
                listingsLocationTv.text = ListingLocation[position]
                listingsHseTypeTv.text = ListingHseType[position]
                listingsBedSizeTv.text = ListingBed[position]
                listingsImageTv.setImageResource(ListingImage[position])

                deleteButton.setOnClickListener {
                    deleteQuantity(position)
                }
            }
        }

    }

    private fun deleteQuantity(position: Int) {
         ListingItemsName.removeAt(position)
         ListingItemPrice.removeAt(position)
         ListingRating.removeAt(position)
         ListingLocation.removeAt(position)
         ListingHseType.removeAt(position)
         ListingBed.removeAt(position)
         ListingImage.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position,ListingItemsName.size)
    }
}