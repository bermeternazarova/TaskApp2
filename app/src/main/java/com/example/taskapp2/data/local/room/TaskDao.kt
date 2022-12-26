package com.example.taskapp2.data.local.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.taskapp2.ui.home.TaskModel

@Dao
interface TaskDao {
    @Insert
    fun insert(task :TaskModel)  //добавление

    @Query("SELECT * FROM TaskModel ORDER BY title Desc")
    fun getAllTask():List<TaskModel>  //запрос на получение

    @Query("DELETE FROM TaskModel")
     fun deleteAll()

   @Delete
   fun deleteTAskDAo(task: TaskModel)

   @Query("SELECT * FROM TaskModel ORDER BY title Asc")
   fun sortedByAlphabet():List<TaskModel>
}