package com.example.taskapp2.ui.onBoard

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.example.taskapp2.R
import com.example.taskapp2.databinding.FragmentOnBoardPageBinding

class OnBoardPageFragment(var listenerSkip:() -> Unit,
                          var  listenerNext:() -> Unit) : Fragment() {
    private lateinit var binding: FragmentOnBoardPageBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentOnBoardPageBinding.inflate(LayoutInflater.from(context),container,false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initListeners()
    }

    private fun initListeners() {
        binding.startBtn.setOnClickListener{
            findNavController().navigate(R.id.navigation_home)
        }
        binding.nextBtn.setOnClickListener {
            listenerNext.invoke()
        }
        binding.skipBtn.setOnClickListener {
            listenerSkip.invoke()
        }
    }
    private fun initViews() {
        arguments.let {
            val data = it?.getSerializable("onBoard") as BoardModel
            binding.titleBoard.text=data.title
            binding.descriptionBoard.text=data.description
            data.image?.let { it1 -> binding.imageBoard.setImageResource(it1) }
            binding.skipBtn.isVisible = data.isLast == false
            binding.nextBtn.isVisible= data.isLast ==false
            binding.startBtn.isVisible =data.isLast ==true
        }
    }
}