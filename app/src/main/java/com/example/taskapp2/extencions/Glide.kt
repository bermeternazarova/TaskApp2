package com.example.taskapp2.extencions

import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.taskapp2.R

fun View.loadImage(url: String){
    Glide.with(this).load(url).placeholder(R.drawable.account).circleCrop().into(this as ImageView)
}