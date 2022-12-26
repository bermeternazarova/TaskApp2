package com.example.taskapp2.extencions

import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide

fun View.loadImage(url: String){
    Glide.with(this).load(url).circleCrop().into(this as ImageView)
}