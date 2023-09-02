package com.android.simpleday.data

import androidx.lifecycle.LiveData
import com.android.simpleday.data.local.TaskDao
import com.android.simpleday.data.local.TaskEntry
import javax.inject.Inject

class TaskRepository @Inject constructor(private val taskDao: TaskDao) {

    suspend fun insertar(taskEntry: TaskEntry) = taskDao.insertar(taskEntry)

    suspend fun actualizarData(taskEntry: TaskEntry) = taskDao.actualizar(taskEntry)

    suspend fun borrarItem(taskEntry: TaskEntry) = taskDao.borrar(taskEntry)

    suspend fun deleteAll() {
        taskDao.borrarTodo()
    }

    fun getAllTasks() = taskDao.obtenerTodasLasTareas()

    fun getAllPriorityTasks() = taskDao.getAllPriorityTasks()

    fun searchDatabase(searchQuery: String): LiveData<List<TaskEntry>> {
        return taskDao.searchDatabase(searchQuery)
    }

}
