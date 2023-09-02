package com.android.simpleday.data.local

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.android.simpleday.util.Constants.TABLA_TAREAS
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = TABLA_TAREAS)
data class TaskEntry(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var titulo: String,
    var prioridad: Int,
    var timestamp: Long
):Parcelable