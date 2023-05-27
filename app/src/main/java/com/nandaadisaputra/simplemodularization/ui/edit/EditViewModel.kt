package com.nandaadisaputra.simplemodularization.ui.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nandaadisaputra.simplemodularization.base.viewmodel.BaseViewModel
import com.nandaadisaputra.simplemodularization.core.data.model.Users
import com.nandaadisaputra.simplemodularization.core.data.model.UsersDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
//class EditViewModel  @Inject constructor(private val nameDao: UsersDao): ViewModel() {
class EditViewModel  @Inject constructor(private val nameDao: UsersDao): BaseViewModel() {
    /*Karena nilainya tetap tidak berubah ubah value nya maka kita pakai val bukan var*/
    /*Kita tuliskan type Private karena hanya diakses oleh class ini*/
    private val _responseSave = MutableSharedFlow<Boolean>()
    /*Untuk mengetahui respon save*/
    val responseSave = _responseSave.asSharedFlow()
    /*Untuk mengambil data user dari table*/
    fun getUsers(idUsers: Int) = nameDao.getUsername(idUsers)
    /*Untuk menambahkan user ke table*/
    fun addUsers(users: Users) = viewModelScope.launch {
        nameDao.insert(users)
        _responseSave.emit(true)
    }

}