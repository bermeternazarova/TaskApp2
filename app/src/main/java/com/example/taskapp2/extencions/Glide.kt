package com.example.taskapp2.extencions

import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.taskapp2.R

fun View.loadImage(url: String){
    Glide.with(this).load(url).placeholder(R.drawable.account).circleCrop().into(this as ImageView)
}
fun Fragment.showToast(message:String){
    Toast.makeText(requireContext(),message , Toast.LENGTH_SHORT).show()
}