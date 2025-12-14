package com.projet.mobile.growth.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.projet.mobile.growth.databinding.ItemTaskBinding

object TasksDiffCallback : DiffUtil.ItemCallback<Task>() {
    override fun areItemsTheSame(oldTask: Task, newTask: Task): Boolean {
        return oldTask.id == newTask.id
    }

    override fun areContentsTheSame(oldTask: Task, newTask: Task): Boolean {
        return oldTask == newTask
    }
}

class TaskListAdapter :
    ListAdapter<Task, TaskListAdapter.TaskViewHolder>(TasksDiffCallback) {

    var onClickDelete: (Task) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val binding = ItemTaskBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return TaskViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class TaskViewHolder(private val binding: ItemTaskBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(task: Task) {
            binding.taskTitle.text = task.title
            binding.taskDesc.text = task.description

            binding.delTask.setOnClickListener {
                onClickDelete(task)
            }
        }

    }
}