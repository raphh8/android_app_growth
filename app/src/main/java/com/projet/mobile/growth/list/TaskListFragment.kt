package com.projet.mobile.growth.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.projet.mobile.growth.R
import java.util.UUID

class TaskListFragment : Fragment() {
    
    private var taskList = List(100) { index ->
        Task(id = "id_$index", title = "Task $index")
    }
    private val adapter = TaskListAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_task_list, container, false)
        adapter.submitList(taskList)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler)
        recyclerView.adapter = adapter
        val addTask = view.findViewById<FloatingActionButton>(R.id.add_task)
        addTask.setOnClickListener {
            val newTask = Task(id = UUID.randomUUID().toString(), title = "Task ${taskList.size + 1}")
            taskList = taskList + newTask
            refreshAdapter(adapter, taskList)
        }
    }

    fun refreshAdapter(a: TaskListAdapter, newList: List<Task>) {
        a.submitList(newList)
        a.notifyDataSetChanged()
    }
}