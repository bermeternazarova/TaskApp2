package com.example.taskapp2.utils

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences

class Preference (context: Context) {

  private  val sharedPreferences:SharedPreferences = context.getSharedPreferences("preference", MODE_PRIVATE)

    fun isBoardingShowed():Boolean{
        return sharedPreferences.getBoolean("board",false)
    }
    fun setBoardingShowed(isShow:Boolean){
        sharedPreferences.edit().putBoolean("board",isShow).apply()
    }
    fun getImageUri():String{
        return sharedPreferences.getString("image","").toString()
    }
    fun saveImageUri(url:String){
        sharedPreferences.edit().putString("image",url).apply()
    }
    fun getEditText():String{
        return sharedPreferences.getString("name","").toString()
    }
    fun saveEditText(name:String){
        sharedPreferences.edit().putString("name",name).apply()
    }
}