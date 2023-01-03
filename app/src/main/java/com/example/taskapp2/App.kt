package com.example.taskapp2

import android.app.Application
import androidx.room.Room
import com.example.taskapp2.data.local.room.TaskDataBase

class App:Application() {
    companion object{
       lateinit var database:TaskDataBase
    }
    override fun onCreate() {
        super.onCreate()
        database=Room.databaseBuilder(
            this,
            TaskDataBase::class.java,
            "database")
            .allowMainThreadQueries()
            .build()
    }
}