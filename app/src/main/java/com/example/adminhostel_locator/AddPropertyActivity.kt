package com.example.adminhostel_locator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import com.example.adminhostel_locator.databinding.ActivityAddPropertyBinding

class AddPropertyActivity : AppCompatActivity() {
    private val binding: ActivityAddPropertyBinding by lazy {
        ActivityAddPropertyBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.selectImage.setOnClickListener {
            pickImage.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }
        binding.backButton.setOnClickListener {
            finish()
        }

    }
    val pickImage = registerForActivityResult(ActivityResultContracts.PickVisualMedia()){uri ->
        if (uri != null)
        {
            binding.selectedImage.setImageURI(uri)
        }
    }
}