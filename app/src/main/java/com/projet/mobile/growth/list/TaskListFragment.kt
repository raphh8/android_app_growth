package com.projet.mobile.growth.list

import com.projet.mobile.growth.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.projet.mobile.growth.databinding.FragmentTaskListBinding

class TaskListFragment : Fragment() {

    private var _binding: FragmentTaskListBinding? = null
    private val binding get() = _binding!!

    private var taskList = emptyList<Task>()

    val adapterListener : TaskListListener = object : TaskListListener {
        override fun onClickDelete(task: Task) {
            taskList = taskList.filter { it.id != task.id }
            adapter.submitList(taskList)
        }
        override fun onClickEdit(task: Task) {
            parentFragmentManager.commit {
                replace(R.id.fragment_container, DetailFragment(task))
                addToBackStack(null)
            }
        }
    }
    val adapter = TaskListAdapter(adapterListener)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTaskListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.recycler.adapter = adapter
        adapter.submitList(taskList)

        parentFragmentManager.setFragmentResultListener(
            DetailFragment.REQUEST_KEY,
            viewLifecycleOwner
        ) { _, bundle ->
            val task = bundle.getSerializable(DetailFragment.RESULT_KEY) as Task?
            val index = taskList.indexOfFirst { it.id == task!!.id }
            if (index == -1) {
                task?.let {
                    taskList = taskList + it
                    adapter.submitList(taskList)
                    binding.recycler.scrollToPosition(taskList.size - 1)
                }
            } else {
                task?.let {
                    taskList = taskList.map { t ->
                        if (t.id == it.id) it else t
                    }
                    adapter.submitList(taskList)
                }
            }

        }

        binding.addTask.setOnClickListener {
            parentFragmentManager.commit {
                replace<DetailFragment>(R.id.fragment_container)
                addToBackStack(null)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}