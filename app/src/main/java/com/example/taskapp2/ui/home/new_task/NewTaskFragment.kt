package com.example.taskapp2.ui.home.new_task

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import coil.load
import com.example.taskapp2.App
import com.example.taskapp2.R
import com.example.taskapp2.databinding.FragmentNewTaskBinding
import com.example.taskapp2.ui.home.TaskModel

class NewTaskFragment : Fragment() {
    private lateinit var binding: FragmentNewTaskBinding
    var imgUri :String=""

    private val getContent : ActivityResultLauncher<String> =
        registerForActivityResult(ActivityResultContracts.GetContent()){ imageUri: Uri?->
            binding.imageView.load(imageUri)
             imgUri=imageUri.toString()}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentNewTaskBinding.inflate(LayoutInflater.from(context),container,false)
        initViews()
        initListeners()
        return binding.root
    }

    private fun initListeners() {
        binding.btnSave.setOnClickListener{

            App.database.dao().insert(
                TaskModel(
                    imageUri = imgUri,
                    description = binding.description.text.toString(),
                    data = binding.etData.text.toString(),
                    title = binding.etTitle.text.toString()
                ))
            findNavController().navigateUp()
        }
    }
    private fun initViews() {
        binding.imageView.setOnClickListener {
            getContent.launch("image/*")
        }
    }
}

