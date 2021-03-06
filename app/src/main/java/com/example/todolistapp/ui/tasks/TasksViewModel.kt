package com.example.todolistapp.ui.tasks

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.todolistapp.data.TaskDao
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest


class TasksViewModel @ViewModelInject constructor(
    private val taskDao: TaskDao
) : ViewModel() {

    val searchQuery = MutableStateFlow("")
    val sortOrder = MutableStateFlow(SortOrder.BY_DATE)
    val hideCompleted = MutableStateFlow(false)

    /* private val taskFlow = searchQuery.flatMapLatest {
         taskDao.getTasks(it)
     }*/

    //Whenever a change happens in any of this 3 flows it will be automatically reflected in the taskFlow
    private val taskFlow = combine(
        searchQuery,
        sortOrder,
        hideCompleted
    ) { query, sortOrder, hideCompleted ->
        Triple(query, sortOrder, hideCompleted)
    }.flatMapLatest { (query, sortOrder, hideCompleted) ->
        taskDao.getTasksBYQuery(query, sortOrder, hideCompleted)
    }

    val tasks = taskFlow.asLiveData()

}

enum class SortOrder {
    BY_NAME,
    BY_DATE
}