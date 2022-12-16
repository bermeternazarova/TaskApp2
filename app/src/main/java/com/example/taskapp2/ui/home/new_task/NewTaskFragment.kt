package com.example.taskapp2.ui.home.new_task

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import com.example.taskapp2.R
import com.example.taskapp2.databinding.FragmentNewTaskBinding

class NewTaskFragment : Fragment() {
    private lateinit var binding: FragmentNewTaskBinding

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
            setFragmentResult("new_task", bundleOf(
                "title" to binding.etTitle.text.toString(),
                "description" to binding.description.text.toString())
            )
            findNavController().navigateUp()
        }
    }

    private fun initViews() {

    }}

