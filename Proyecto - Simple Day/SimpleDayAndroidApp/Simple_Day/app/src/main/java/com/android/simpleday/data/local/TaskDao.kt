package com.android.simpleday.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {

    @Insert
    suspend fun insertar(taskEntry: TaskEntry)

    @Delete
    suspend fun borrar(taskEntry: TaskEntry)

    @Update
    suspend fun actualizar(taskEntry: TaskEntry)

    @Query("DELETE FROM tareas")
    suspend fun borrarTodo()

    @Query("SELECT * FROM tareas ORDER BY timestamp DESC")
    fun obtenerTodasLasTareas(): Flow<List<TaskEntry>>

    @Query("SELECT * FROM tareas ORDER BY prioridad ASC")
    fun getAllPriorityTasks(): Flow<List<TaskEntry>>

    @Query("SELECT * FROM tareas WHERE titulo LIKE :searchQuery ORDER BY timestamp DESC")
    fun searchDatabase(searchQuery: String): LiveData<List<TaskEntry>>
}