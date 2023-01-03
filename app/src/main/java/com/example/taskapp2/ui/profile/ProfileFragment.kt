package com.example.taskapp2.ui.profile

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.example.taskapp2.databinding.FragmentProfileBinding
import com.example.taskapp2.extencions.loadImage
import com.example.taskapp2.utils.Preference

class ProfileFragment : Fragment() {



    private lateinit var binding: FragmentProfileBinding
    private val getContent : ActivityResultLauncher<String> =
        registerForActivityResult(ActivityResultContracts.GetContent()){imageUri:Uri?->
       //binding.circleImage.load(imageUri)
            binding.circleImage.loadImage(imageUri.toString())

           Preference(requireContext()).saveImageUri(imageUri.toString())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater,container,false)
        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnProfile.alpha = 0f
        binding.btnProfile.animate().apply {
            duration = 2000
            alpha(1f)
        }

        binding.circleImage.setOnClickListener {
            getContent.launch("image/*")
        }
        binding.circleImage.loadImage(Preference(requireContext()).getImageUri())

        binding.btnProfile.setOnClickListener {
            Preference(requireContext()).saveEditText(binding.etProfile.text.toString())
           // Preference(requireContext()).getEditText()
        }
        Preference(requireContext()).getEditText()

    }
}