package com.example.taskapp2.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.taskapp2.ui.home.TaskModel

@Database (entities = [TaskModel::class], version = 1)
 abstract class TaskDataBase :RoomDatabase() {
     abstract  fun dao( ):TaskDao
}