package com.example.taskapp2.data.local.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.taskapp2.ui.home.TaskModel

@Dao
interface TaskDao {
    @Insert
    fun insert(task :TaskModel)

    @Query("SELECT * FROM TaskModel ORDER BY title Desc")
    fun getAllTask():List<TaskModel>

    @Query("DELETE FROM TaskModel")
     fun deleteAll()

   @Delete
   fun deleteTAskDAo(task: TaskModel)

   @Query("SELECT * FROM TaskModel ORDER BY title Asc")
   fun sortedByAlphabet():List<TaskModel>

   @Update
   fun updateTask(taskModel: TaskModel)

   @Query("SELECT * FROM TaskModel ORDER BY  id DESC")
   fun getListByData():List<TaskModel>

   @Query("SELECT * FROM TaskModel ORDER BY title ASC")
   fun getLIstByAlphabet():List<TaskModel>

}