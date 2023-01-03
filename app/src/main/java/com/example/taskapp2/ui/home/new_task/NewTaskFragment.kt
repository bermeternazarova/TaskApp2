package com.example.taskapp2.ui.home.new_task

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import coil.load
import com.example.taskapp2.App
import com.example.taskapp2.Keys
import com.example.taskapp2.databinding.FragmentNewTaskBinding
import com.example.taskapp2.ui.home.TaskModel

class NewTaskFragment : Fragment() {
    private lateinit var binding: FragmentNewTaskBinding
     private var imgUri :String=""
    private lateinit var task :TaskModel

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
        editTask()
        return binding.root
    }

    private fun initListeners() {
        binding.btnSave.setOnClickListener{
           if (arguments!=null){
               updateTAsk(task)
           }else{
               saveData()
           }
            findNavController().navigateUp()
        }
    }
    private fun initViews() {
        binding.imageView.setOnClickListener {
            getContent.launch("image/*")
        }
    }
    @SuppressLint("SetTextI18n")
    private fun editTask(){
        if (arguments!=null){
            task = arguments?.getSerializable(Keys.EDIT_TASK) as TaskModel
            binding.etTitle.setText(task.title)
            binding.etDescription.setText(task.description)
            binding.etData.setText(task.data)
            binding.btnSave.text = "Update"
        }else{
            binding.btnSave.text = "Save"
        }
    }
    private fun saveData(){
        App.database.dao().insert(
            TaskModel(
                imageUri = imgUri,
                description = binding.etDescription.text.toString(),
                data = binding.etData.text.toString(),
                title = binding.etTitle.text.toString()
            ))
    }
    private fun updateTAsk(taskModel: TaskModel){
        taskModel.title = binding.etTitle.text.toString()
        taskModel.description = binding.etDescription.text.toString()
        taskModel.data = binding.etData.text.toString()
        App.database.dao().updateTask(taskModel)
    }
}

