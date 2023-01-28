package com.nandaadisaputra.simplemodularization.core.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.nandaadisaputra.simplemodularization.core.data.model.Users
import com.nandaadisaputra.simplemodularization.core.data.model.UsersDao

@Database(
    entities = [Users::class],
    version = 1
)
abstract class UsersDatabase : RoomDatabase() {
    abstract fun userDao(): UsersDao

    companion object {
        @Volatile
        private var INSTANCE: UsersDatabase? = null

        @Synchronized
        fun getInstance(context: Context): UsersDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }

            val instance = Room.databaseBuilder(context.applicationContext, UsersDatabase::class.java, "Users_Database.db")
                .fallbackToDestructiveMigration()
                .build()
            INSTANCE = instance
            return instance
        }
    }
}
