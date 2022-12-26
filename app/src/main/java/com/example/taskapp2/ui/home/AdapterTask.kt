package com.example.taskapp2.ui.home

import android.app.AlertDialog
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.recyclerview.widget.RecyclerView
import com.example.taskapp2.App
import com.example.taskapp2.data.local.room.TaskDao
import com.example.taskapp2.databinding.ItemTaskBinding
import com.example.taskapp2.extencions.loadImage
import okhttp3.internal.concurrent.Task

class AdapterTask: RecyclerView.Adapter<AdapterTask.ViewHolderTask>(){
    private var tasklist= arrayListOf<TaskModel>()

    //    override fun onCreateContextMenu(
//        p0: ContextMenu?,
//        p1: View?,
//        p2: ContextMenu.ContextMenuInfo?
//    ) {
//        p0?.add(Menu.NONE,R.string.No,Menu.NONE,R.string.No)
//        p0?.add(Menu.NONE,R.string.yes,Menu.NONE,R.string.yes)
//    }
    fun addTasks(list: List<TaskModel>){
        tasklist.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderTask {
        return ViewHolderTask(ItemTaskBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolderTask, position: Int) {
        holder.bind(tasklist[position])
        holder.itemView.setOnLongClickListener{
//            holder.itemView.setOnCreateContextMenuListener(this)
//            holder.adapterPosition
            val option = arrayOf("Нет", "Удалить")
        val alert = AlertDialog.Builder(holder.itemView.context)
        alert.setTitle("Вы точно хотите удалить запись?").setItems(option,DialogInterface.OnClickListener { dialogInterface, i ->
            if (i==0){
                Toast.makeText(holder.itemView.context,"Молодец!Правильный выбор",Toast.LENGTH_SHORT).show()
            }else if(i==1){
                deleteTask(holder.adapterPosition)
                apply {
                    //App.database.dao().deleteTask(holder.adapterPosition)
                    val item =tasklist[position]
                    (tasklist as MutableList<TaskModel>).remove(item)
                    notifyItemChanged(position)
                    App.database.dao().deleteTAskDAo(item)
                }
            }
        }).show()
            return@setOnLongClickListener false
        }
//        val v = holder.itemView
//        v.setOnClickListener {
//            Toast.makeText(v.context,"Position is" + holder.adapterPosition,
//                Toast.LENGTH_SHORT).show()
//        }
//        v.setOnLongClickListener {
////           alert()
//            return@setOnLongClickListener false
//        }
    }

    private fun deleteTask(index:Int) {
        tasklist.removeAt(index)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return tasklist.size
    }

    inner class ViewHolderTask(private var binding: ItemTaskBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(taskModel: TaskModel){
            binding.tvItemTitle.text= taskModel.title
            binding.tvItemDescription.text = taskModel.description
            binding.dataItem.text=taskModel.data
           // binding.imageItem.setImageURI(taskModel.imageUri.toUri())
            binding.imageItem.loadImage(taskModel.imageUri)
        }
    }
}
