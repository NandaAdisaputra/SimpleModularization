package com.nandaadisaputra.simplemodularization.core.injection

import android.content.Context
import androidx.room.Room
import com.nandaadisaputra.simplemodularization.core.data.model.UsersDao
import com.nandaadisaputra.simplemodularization.core.data.room.UsersDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context) = UsersDatabase.getInstance(context)

    @Provides
    fun provideUsersDao(database: UsersDatabase): UsersDao = database.userDao()
}
