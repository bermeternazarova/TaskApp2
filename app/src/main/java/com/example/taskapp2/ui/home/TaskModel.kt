package com.example.taskapp2.ui.home

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TaskModel(
    @PrimaryKey(autoGenerate = true)
    var id: Long? = null,
    var imageUri: String,
    var description: String,
    var title:String,
    var data: String
):java.io.Serializable