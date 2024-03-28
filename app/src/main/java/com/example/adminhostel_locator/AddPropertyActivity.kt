package com.example.adminhostel_locator

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import com.example.adminhostel_locator.databinding.ActivityAddPropertyBinding
import com.example.adminhostel_locator.model.AllListings
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask

class AddPropertyActivity : AppCompatActivity() {
    //listing property details
    private lateinit var listingName:String
    private lateinit var listingPrice:String
    private lateinit var listingRating:String
    private lateinit var listingLocation:String
    private lateinit var listingHseType:String
    private lateinit var listingBedsize:String




//    private lateinit var listingDescription:String
//    private lateinit var listingFacilities:String
    private  var listingImageUri: Uri? = null
    //firebase
    private lateinit var auth:FirebaseAuth
    private lateinit var database: FirebaseDatabase


    private val binding: ActivityAddPropertyBinding by lazy {
        ActivityAddPropertyBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        //initialize firebase
        auth = FirebaseAuth.getInstance()
        //initialize firebasedatabase instance
        database = FirebaseDatabase.getInstance()
        binding.addPropertyButton.setOnClickListener {
            //get data from the fields
            listingName =  binding.listingName.text.toString().trim()
            listingPrice =  binding.listingPrice.text.toString().trim()
            listingRating = binding.listingRating.text.toString().trim()
            listingLocation = binding.listingLocation.text.toString().trim()
            listingHseType = binding.listingHseType.text.toString().trim()
            listingBedsize = binding.listingBedsize.text.toString().trim()
//            listingDescription = binding.description.text.toString().trim()
//            listingFacilities = binding.facilities.text.toString().trim()

            if (!(listingName.isBlank()||listingPrice.isBlank()||listingRating.isBlank()||listingLocation.isBlank()||listingHseType.isBlank()||listingBedsize.isBlank())){
                uploadData()
                Toast.makeText(this,"Property Added Successfully",Toast.LENGTH_SHORT).show()
                finish()
            } else{
                Toast.makeText(this,"Fill all the details",Toast.LENGTH_SHORT).show()
            }
        }

        binding.selectImage.setOnClickListener {
            pickImage.launch("image/*")
        }

        binding.backButton.setOnClickListener {
            finish()
        }

    }

    private fun uploadData() {
        //get the reference to the listing node in the database
        val listingRef:DatabaseReference = database.getReference("listing")
        //generate unique key for the new listing property
        val newPropertyKey:String? = listingRef.push().key

        if (listingImageUri != null){
            val storageRef = FirebaseStorage.getInstance().reference
            val imageRef = storageRef.child("listing_images/${newPropertyKey}.jpg")
            val uploadTask:UploadTask = imageRef.putFile(listingImageUri!!)

            uploadTask.addOnSuccessListener {
                imageRef.downloadUrl.addOnSuccessListener {
                    downloadUrl->
                    //create a new listing property
                    val newListing = AllListings(
                        listingName = listingName,
                        listingPrice = listingPrice,
                        listingRating = listingRating,
                        listingLocation = listingLocation,
                        listingHseType = listingHseType,
                        listingBedsize = listingBedsize,
//                        listingDescription = listingDescription,
//                        listingFacilities = listingFacilities,
                        listingImage = downloadUrl.toString(),
                    )
                    newPropertyKey?.let {
                        key ->
                        listingRef.child(key).setValue(newListing).addOnSuccessListener {
                            Toast.makeText(this,"Data Uploaded successfully",Toast.LENGTH_SHORT).show()
                        }
                            .addOnFailureListener {
                                Toast.makeText(this,"Data Upload failed",Toast.LENGTH_SHORT).show()
                            }

                    }
                }
            }
                .addOnFailureListener {
                    Toast.makeText(this,"Image Upload failed",Toast.LENGTH_SHORT).show()
                }
        }
            else {
                Toast.makeText(this,"Please select an image",Toast.LENGTH_SHORT).show()
            }
    }

    private val pickImage = registerForActivityResult(ActivityResultContracts.GetContent()){uri ->
        if (uri != null)
        {
            binding.selectedImage.setImageURI(uri)
            listingImageUri = uri
        }
    }
}