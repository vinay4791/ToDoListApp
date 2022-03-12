package com.example.todolistapp.ui.tasks

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.todolistapp.data.TaskDao


class TasksViewModel @ViewModelInject constructor(
    private val taskDao: TaskDao
): ViewModel() {

    val tasks = taskDao.getTasks().asLiveData()

}