package com.nandaadisaputra.simplemodularization.ui.edit

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.crocodic.core.extension.openActivity
import com.crocodic.core.extension.tos
import com.nandaadisaputra.simplemodularization.R
import com.nandaadisaputra.simplemodularization.base.activity.BaseActivity
import com.nandaadisaputra.simplemodularization.core.data.constant.Const
import com.nandaadisaputra.simplemodularization.core.data.model.Users
import com.nandaadisaputra.simplemodularization.databinding.ActivityEditBinding
import com.nandaadisaputra.simplemodularization.databinding.ActivityMainBinding
import com.nandaadisaputra.simplemodularization.ui.home.MainActivity
import com.nandaadisaputra.simplemodularization.ui.home.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


/*Karena menggunakan DI Hilt maka Kita wajib menambahkan anotasi @AndroidEntryPoint*/

@AndroidEntryPoint
//class EditActivity : AppCompatActivity() {
    class EditActivity : BaseActivity<ActivityEditBinding, EditViewModel>(R.layout.activity_edit) {
//    /*Kita deklarasikan editViewModel terlebih dahulu*/
//    private val editViewModel: EditViewModel by viewModels()
//
//    /*Kita deklarasikan juga binding yang akan Kita gunakan*/
//    private val binding: ActivityEditBinding by lazy {
//        DataBindingUtil.setContentView(this, R.layout.activity_edit)
//    }

    /*deklarasikan variabel idUsername dengan diberi isi secara default value 0*/
    private var idUsername = 0

    /*Jangan lupa deklarasikan usernameStudent*/
    /* username diambil dari xml di activity_main dari android:text='@={activity.usernameStudent}'*/
    /*Karena Kita menerapkan DataBinding*/
    var usernameStudent = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = getString(R.string.app_name)
        /*inisialisasi dulu activitynya*/
        /*wajib karena Kita memakai DataBinding*/
        binding.activity = this
        binding.lifecycleOwner = this
        /*Kita inisialisasi variabel idUsername yang isinya adalah menerima data id username yang dibawa dari MainActivity.kt*/
        idUsername = intent.getIntExtra(Const.ID.ID_USERNAME, 0)
        /*Kita akan memakai fungsi observe()
         * maka perlu Kita deklarasikan dulu */
        observe()
    }

    private fun observe() {
        /*Karena menggunakan Coroutines kita dapat memanggil lifecycleScope.launch */
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    /*panggil variabel responseDelete dari ViewModel*/
//                    editViewModel.responseSave.collect { success ->
                    viewModel.responseSave.collect { success ->
                        if (success) {
                            /*Ketika berhasil tersimpan datanya akan muncul pesan Berhasil Mengubah Data. */
//                            Toast.makeText(
//                                this@EditActivity,
//                                Const.TOAST.UPDATE,
//                                Toast.LENGTH_SHORT
//                            ).show()
                            tos( Const.TOAST.UPDATE)
                            /*Beri finish() agar anda tidak dapat kembali ke aktivitas sebelumnya dengan tombol "kembali".*/
                            finish()
                        }
                    }
                }
                /*Pengecekan jika id username tidak sama dengan 0*/
                if (idUsername != 0) {
                    launch {
                        /*panggil fungsu getUsers dengan param id Username dari ViewModel*/
//                        editViewModel.getUsers(idUsername).collect { users ->
                       viewModel.getUsers(idUsername).collect { users ->
                            /*menampilkan teks username sebelum proses edit, sehingga tidak perlu ketik ulang
                            * semuanya*/
                            usernameStudent = users.username ?: ""
                            binding.activity = this@EditActivity
                        }
                    }
                }
            }
        }
    }

    fun updateUsername() {
        /*Apabila inputan kosong / tidak ada inputan */
        if (usernameStudent.isEmpty()) {
            /*Ketika berhasil formnya kosong akan menampilkan pesan toast Masih ada form yang kosong. . */
//            Toast.makeText(this@EditActivity, Const.VALIDATION.EMPTY, Toast.LENGTH_SHORT).show()
            tos(Const.VALIDATION.EMPTY)
            return
        }
        /*Ketika berhasil update data akan berpindah secara otomatis ke MainActivity.kt */
//        val editIntent = Intent(this, MainActivity::class.java)
//        startActivity(editIntent)
        openActivity<MainActivity>()
        /*Kita inisialisasi variabel newUsername isinya adalah class Users*/
        val newUsername = Users(id = idUsername, username = usernameStudent)
        /*Kita panggil addUsers untuk menyimpan data ke tabel Users*/
//        editViewModel.addUsers(newUsername)
        viewModel.addUsers(newUsername)
    }
}