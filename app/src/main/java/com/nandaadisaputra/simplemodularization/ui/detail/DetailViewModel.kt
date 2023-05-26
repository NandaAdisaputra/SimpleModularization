package com.nandaadisaputra.simplemodularization.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nandaadisaputra.simplemodularization.core.data.model.UsersDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/*Karena menggunakan DI Hilt maka Kita wajib menambahkan anotasi @HiltViewModel*/

@HiltViewModel
class DetailViewModel  @Inject constructor(private val nameDao: UsersDao): ViewModel() {
    /*Karena nilainya tetap tidak berubah ubah value nya maka kita pakai val bukan var*/
    /*Kita tuliskan type Private karena hanya diakses oleh class ini*/
    private val _responseDelete = MutableSharedFlow<Boolean>()
    /*Untuk mengetahui respon delete*/
    val responseDelete = _responseDelete.asSharedFlow()
    /*Untuk mengambil data user dari table*/
    fun getUsername(idUsers: Int) = nameDao.getUsername(idUsers)
    /*Untuk delete user dari table*/
    fun deleteUsername(idUsers: Int) = viewModelScope.launch {
        nameDao.delete(idUsers)
        _responseDelete.emit(true)
    }
}