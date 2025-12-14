package com.projet.mobile.growth.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.projet.mobile.growth.databinding.FragmentTaskListBinding
import java.util.UUID

class TaskListFragment : Fragment() {

    private var _binding: FragmentTaskListBinding? = null
    private val binding get() = _binding!!

    private var taskList = List(100) { index ->
        Task(id = "id_$index", title = "Task $index")
    }

    private val adapter = TaskListAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTaskListBinding.inflate(inflater, container, false)

        adapter.submitList(taskList)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.recycler.adapter = adapter

        binding.addTask.setOnClickListener {
            val newTask = Task(
                id = UUID.randomUUID().toString(),
                title = "Task ${taskList.size + 1}"
            )

            taskList = taskList + newTask
            adapter.submitList(taskList)
            binding.recycler.scrollToPosition(taskList.size - 1)
        }

        adapter.onClickDelete = { taskToDelete ->
            taskList = taskList.filter { it.id != taskToDelete.id }
            adapter.submitList(taskList)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}