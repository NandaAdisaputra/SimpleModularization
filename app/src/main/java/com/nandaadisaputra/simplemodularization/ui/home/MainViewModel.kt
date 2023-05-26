package com.nandaadisaputra.simplemodularization.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nandaadisaputra.simplemodularization.core.data.model.Users
import com.nandaadisaputra.simplemodularization.core.data.model.UsersDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


/*Karena menggunakan DI Hilt maka Kita wajib menambahkan anotasi @HiltViewModel*/

@HiltViewModel
class MainViewModel @Inject constructor(private val nameDao: UsersDao): ViewModel() {
    /*Kita berikan isi variabel users dengan perintah mengambil data*/
    /*Karena nilainya tetap tidak berubah ubah value nya maka kita pakai val bukan var*/
    val users = nameDao.getAllUsername()
    /*Kita tuliskan type Private karena hanya diakses oleh class ini*/
    private val _responseSave = Channel<Boolean>()
    /*Untuk mengetahui respon save*/
    val responseSave = _responseSave.receiveAsFlow()
    /*Untuk menambahkan user ke table*/
    fun addUsers(users: Users) = viewModelScope.launch {
        nameDao.insert(users)
        _responseSave.send(true)
    }
}