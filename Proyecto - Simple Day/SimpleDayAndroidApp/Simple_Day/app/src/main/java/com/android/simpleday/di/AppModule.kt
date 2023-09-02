package com.android.simpleday.di

import android.content.Context
import androidx.room.Room
import com.android.simpleday.data.local.TaskDatabase
import com.android.simpleday.util.Constants.TAREAS_DB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideTaskDao(@ApplicationContext context: Context) =
        Room.databaseBuilder(
            context,
            TaskDatabase::class.java,
            TAREAS_DB
        ).build().taskDao()
}