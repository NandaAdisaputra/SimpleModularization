package com.nandaadisaputra.simplemodularization.data.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


@Dao
interface UsersDao {
    /*untuk menyimpan data*/
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(users: Users)

    /*untuk mengambil data*/
    @Query("SELECT * FROM users ORDER BY id DESC")
    fun getAllUsername(): Flow<List<Users>>
}