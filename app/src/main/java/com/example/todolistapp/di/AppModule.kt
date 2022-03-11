package com.example.todolistapp.di

import android.app.Application
import androidx.room.Room
import com.example.todolistapp.data.TaskDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesDatabase(
        application: Application,
        callback: TaskDatabase.Callback
    ) = Room.databaseBuilder(
        application,
        TaskDatabase::class.java,
        "task_database"
    ).fallbackToDestructiveMigration()
        .addCallback(callback)
        .build()

    //This Dependency is already considered as SingleTon.
    //Room works under the hood.
    @Provides
    fun provideTaskDao(
        taskDatabase: TaskDatabase
    ) = taskDatabase.taskDao()

    //SupervisorJob tells if any of the child fails keep the other running.
    @ApplicationScope
    @Provides
    @Singleton
    fun providesApplicationScope() = CoroutineScope(SupervisorJob())


}

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class ApplicationScope