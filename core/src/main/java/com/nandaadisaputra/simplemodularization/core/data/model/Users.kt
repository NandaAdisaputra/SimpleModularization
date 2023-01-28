package com.nandaadisaputra.simplemodularization.core.data.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

/*bungkus dengan interface Parcelable untuk Implementasi Intent mengirim Data dengan Parcelable."*/

@Parcelize
@Entity(tableName = "users")
data class Users(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int = 0,
    @ColumnInfo(name = "username")
    val username: String?
) : Parcelable