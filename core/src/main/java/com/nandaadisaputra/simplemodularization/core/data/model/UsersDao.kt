package com.nandaadisaputra.simplemodularization.core.data.model

import androidx.room.*
import kotlinx.coroutines.flow.Flow


@Dao
interface UsersDao {
    /*untuk menyimpan data*/
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(users: Users)

    /*untuk mengambil seluruh data*/
    @Query("SELECT * FROM users ORDER BY id DESC")
    fun getAllUsername(): Flow<List<Users>>

    /*Tambahkan beberapa source code dibawah ini untuk tutorial part 2*/
    /*untuk mengambil 1 data*/
    @Query("SELECT * FROM users WHERE id = :id")
    fun getUsername(id: Int): Flow<Users>

    /*untuk delete data*/
    @Query("DELETE FROM users WHERE id = :id")
    suspend fun delete(id: Int)

    /*untuk update data*/
    @Update()
    suspend fun update(users: Users)
}