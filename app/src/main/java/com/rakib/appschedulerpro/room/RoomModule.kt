package com.rakib.appschedulerpro.room

import android.content.Context
import androidx.room.Room
import com.rakib.appschedulerpro.scheduler.SchedulerRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RoomModule {
    @Provides
    @Singleton
    fun providesScheduleDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, "ScheduleDatabase").build()

    @Provides
    fun providesScheduleDao(appDatabase: AppDatabase): ScheduleDAO = appDatabase.scheduleDao()

    @Provides
    fun providesScheduleRepository(scheduleDao: ScheduleDAO): SchedulerRepository =
        SchedulerRepository(scheduleDao)
}