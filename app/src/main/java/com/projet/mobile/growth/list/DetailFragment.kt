package com.projet.mobile.growth.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.projet.mobile.growth.databinding.FragmentDetailBinding
import java.util.UUID

class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.detailSubmit.setOnClickListener {

            val title = binding.editTitle.text.toString()
            val description = binding.editDesc.text.toString()

            if (title.isBlank()) return@setOnClickListener

            val newTask = Task(
                id = UUID.randomUUID().toString(),
                title = title,
                description = description
            )

            parentFragmentManager.setFragmentResult(
                REQUEST_KEY,
                Bundle().apply {
                    putSerializable(RESULT_KEY, newTask)
                }
            )

            parentFragmentManager.popBackStack()
        }
    }

    companion object {
        const val REQUEST_KEY = "request_key"
        const val RESULT_KEY = "result_key"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
