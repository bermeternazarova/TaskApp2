package com.example.taskapp2.ui.home

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.taskapp2.App
import com.example.taskapp2.R
import com.example.taskapp2.databinding.FragmentHomeBinding
class HomeFragment :  Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var taskAdapter: AdapterTask

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        initViews() // отвечает за иницилизацию вьюшек
        initListeners() // за нажатие
       // registerForContextMenu(binding.rvHome)
        return binding.root
    }

//    override fun onContextItemSelected(item: MenuItem): Boolean {
//        val id = item.itemId
//       if (id==R.string.No){
//           Toast.makeText(requireContext(),"GUGUGAGA",Toast.LENGTH_SHORT).show()
//       }else if (id==R.string.yes){
//           Toast.makeText(requireContext(),"URARA",Toast.LENGTH_SHORT).show()
//       }
//
//        return super.onContextItemSelected(item)
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        taskAdapter = AdapterTask()
    }

    @SuppressLint("SuspiciousIndentation")
    private fun initViews() {
        binding.rvHome.apply {
            layoutManager= LinearLayoutManager(context)
            adapter=taskAdapter
        }
        val listOfTask = App.database.dao().getAllTask()
        taskAdapter.addTasks(listOfTask)
    }

    private fun initListeners() {
        binding.fabHome.setOnClickListener {
            findNavController().navigate(R.id.newTaskFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
