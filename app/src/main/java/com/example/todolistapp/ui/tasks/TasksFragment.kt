package com.example.todolistapp.ui.tasks

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todolistapp.R
import com.example.todolistapp.databinding.FragmentTasksBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TasksFragment : Fragment(R.layout.fragment_tasks) {

    //Fragment should not be responsible for holding data.
    // The reason for this is separation of concerns

    private val viewModel: TasksViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentTasksBinding.bind(view)

        val tasksAdapter = TasksAdapter()

        binding.apply {
            recyclerViewTasks.apply {
                adapter = tasksAdapter
                layoutManager = LinearLayoutManager(requireContext())
                setHasFixedSize(true)
            }
        }

        //Fragment always has 2 lifecycles.
        // Fragment object itself and views lifecycle.
        // If we move to another fragment and this fragment is in backstack its view is destroyed and
        // the Fragment object remains in the memory.
        viewModel.tasks.observe(viewLifecycleOwner) { tasksLists ->
            tasksAdapter.submitList(tasksLists)
        }

    }

}