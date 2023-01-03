package com.example.taskapp2.ui.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.taskapp2.R
import com.example.taskapp2.databinding.ItemTaskBinding
import com.example.taskapp2.extencions.loadImage

class AdapterTask(
    private var onLongClick:(Int)->Unit,
    private var onClick:(Int)->Unit
): RecyclerView.Adapter<AdapterTask.ViewHolderTask>(){
    private var taskList= arrayListOf<TaskModel>()

    @SuppressLint("NotifyDataSetChanged")
    fun addTasks(list: List<TaskModel>){
        taskList.clear()
        taskList.addAll(list)
        notifyDataSetChanged()
    }
    fun getTAsk(position: Int):TaskModel{
        return taskList[position]
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderTask {
        return ViewHolderTask(ItemTaskBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolderTask, position: Int) {
        holder.bind(taskList[position])
        if(position % 2==0){
            holder.itemView.setBackgroundResource(R.color.black)
        }
        else
        { holder.itemView.setBackgroundResource(R.color.white)}
    }
    override fun getItemCount(): Int {
        return taskList.size
    }

    inner class ViewHolderTask(private var binding: ItemTaskBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(taskModel: TaskModel){
            binding.tvItemTitle.text= taskModel.title
            binding.tvItemDescription.text = taskModel.description
            binding.dataItem.text=taskModel.data
            binding.imageItem.loadImage(taskModel.imageUri)

           itemView.setOnLongClickListener{
                onLongClick(adapterPosition)
               Toast.makeText(itemView.context,adapterPosition.toString(),Toast.LENGTH_SHORT).show()
                return@setOnLongClickListener false
            }
            itemView.setOnClickListener {
                onClick(adapterPosition)
            }
        }
    }
}
