package com.nandaadisaputra.simplemodularization.ui.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.crocodic.core.extension.openActivity
import com.crocodic.core.extension.tos
import com.nandaadisaputra.simplemodularization.R
import com.nandaadisaputra.simplemodularization.base.activity.BaseActivity
import com.nandaadisaputra.simplemodularization.core.adapter.UsersAdapter
import com.nandaadisaputra.simplemodularization.core.data.constant.Const
import com.nandaadisaputra.simplemodularization.core.data.model.Users
import com.nandaadisaputra.simplemodularization.databinding.ActivityMainBinding
import com.nandaadisaputra.simplemodularization.ui.detail.DetailActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

/*Karena menggunakan DI Hilt maka Kita wajib menambahkan anotasi @AndroidEntryPoint*/

@AndroidEntryPoint
//class MainActivity : AppCompatActivity() {
class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>(R.layout.activity_main) {
//    /*Kita deklarasikan mainViewModel terlebih dahulu*/
//    private val mainViewModel: MainViewModel by viewModels()
//    /*Kita deklarasikan juga binding yang akan Kita gunakan*/
//    private val binding: ActivityMainBinding by lazy {
//        DataBindingUtil.setContentView(this, R.layout.activity_main)
//    }
    /*Berikutnya deklarasikan juga adapternya*/
    private val adapter = UsersAdapter()
    /*Jangan lupa deklarasikan usernameStudent*/
    /* username diambil dari xml di activity_main dari android:text='@={activity.usernameStudent}'*/
    /*Karena Kita menerapkan DataBinding*/
    var usernameStudent = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /*inisialisasi dulu activitynya*/
        /*wajib karena Kita memakai DataBinding*/
        binding.activity = this
        binding.lifecycleOwner = this
        /*Kita akan memakai fungsi  initData() dan observe()
        * maka perlu Kita deklarasikan dulu */
        initData()
        observe()

    }
    private fun initData() {
        /*Ketika salah satu item di List diklik akan memunculkan aksi*/
        adapter.setOnClickItem { name ->
            /*Disini Saya kasih aksi memunculkan Toast*/
//            Toast.makeText(this, "Ini namanya ${name.username}", Toast.LENGTH_SHORT).show()
            tos("Ini namanya ${name.username}")
            /*Agar dapat berpindah ke DetailActivity.kt ketika salah satu item diklik
             maka Kita perlu tambahkan Intent seperti pada dibawah ini*/
//            val detailIntent = Intent(this, DetailActivity::class.java).apply {
//                putExtra(Const.ID.ID_USERNAME, name.id)
//            }
//            startActivity(detailIntent)
            openActivity<DetailActivity> {
                putExtra(Const.ID.ID_USERNAME, name.id)
            }
        }
        /*Jangan lupa setelah deklarasi di inisialisasi adapter yak*/
        binding.adapter = adapter
    }

    private fun observe() {
        /*Karena menggunakan Coroutines kita dapat memanggil lifecycleScope.launch */
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    /*panggil variabel user dari viewmodel yang isinya perintah menampilkan data*/
//                    mainViewModel.users.collect { username ->
                    viewModel.users.collect { username ->
                        /*Pada bagian ini digunakan untuk menampilkan data */
                        adapter.submitList(username)
                    }
//                    mainViewModel.responseSave.collect { success ->
                    viewModel.responseSave.collect { success ->
                        if (success) {
                            /*Katika berhasil tersimpan datanya akan muncul pesan Berhasil Menyimpan Data. */
//                           Toast.makeText(this@MainActivity, Const.TOAST.SAVE, Toast.LENGTH_SHORT).show()
                            tos(Const.TOAST.SAVE)
                        }
                    }
                }
            }
        }
    }
    fun saveUsername() {
        /*Apabila inputan kosong / tidak ada inputan */
        if (usernameStudent.isEmpty()) {
            /*Akan menampilan pesan Form Masih Kosong Lur. */
//            Toast.makeText(this, Const.VALIDATION.EMPTY, Toast.LENGTH_SHORT).show()
            tos(Const.VALIDATION.EMPTY)
            return
        }
        /*Kita inisialisasi variabel newUsername isinya adalah class Users*/
        val newUsername = Users(username = usernameStudent)
        /*Kita panggil addUsers untuk menyimpan data ke tabel Users*/
//        mainViewModel.addUsers(newUsername)
        viewModel.addUsers(newUsername)
    }
}
