package com.example.taskapp2.ui.home

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.*
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.taskapp2.App
import com.example.taskapp2.Keys
import com.example.taskapp2.R
import com.example.taskapp2.databinding.FragmentHomeBinding

@Suppress("DEPRECATION")
class HomeFragment :  Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var taskAdapter: AdapterTask

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        taskAdapter = AdapterTask(this::onLongClick,this::onClick)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        initViews()
        setData()
        initListeners()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.fabHome.alpha = 0f
        binding.fabHome.animate().apply {
            duration = 1200
            alpha(1f)
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.home_menu ){
            val items = arrayOf("By data","By alphabet")
            val alert = AlertDialog.Builder(requireContext())
            alert.setTitle("Sort by :").setItems(items){ _, i ->
               when(i){
                   0 ->{
                       taskAdapter.addTasks(App.database.dao().getListByData())
                   }
                   1 ->{
                       taskAdapter.addTasks(App.database.dao().getLIstByAlphabet())
                   }
               }

            }.show()
        }
        return super.onOptionsItemSelected(item)
    }

    @Deprecated("Deprecated in Java")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_home,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun onClick(pos :Int){
        val  task = taskAdapter.getTAsk(pos)
    findNavController().navigate(R.id.newTaskFragment, bundleOf(Keys.EDIT_TASK to task))
    }
    private fun onLongClick(pos:Int){
        val option = arrayOf("Нет", "Удалить")
        val alert = AlertDialog.Builder(requireContext())
        alert.setTitle("Вы точно хотите удалить запись?").setItems(option,
            DialogInterface.OnClickListener { dialogInterface, i ->
            if (i==0){
                dialogInterface.dismiss()
            }else if(i==1){
                App.database.dao().deleteTAskDAo(taskAdapter.getTAsk(pos))
            }
        }).show()
    }
    @SuppressLint("SuspiciousIndentation")
    private fun initViews() {
        binding.rvHome.apply {
            layoutManager= LinearLayoutManager(context)
            adapter=taskAdapter
        }
    }

    private fun initListeners() {
        binding.fabHome.setOnClickListener {
            findNavController().navigate(R.id.newTaskFragment)
        }
    }
    private fun setData(){
        val listOfTask = App.database.dao().getAllTask()
        taskAdapter.addTasks(listOfTask)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
