package com.nandaadisaputra.simplemodularization.ui.detail

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
import com.nandaadisaputra.simplemodularization.databinding.ActivityDetailBinding
import com.nandaadisaputra.simplemodularization.databinding.ActivityEditBinding
import com.nandaadisaputra.simplemodularization.ui.edit.EditActivity
import com.nandaadisaputra.simplemodularization.ui.edit.EditViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

/*Karena menggunakan DI Hilt maka Kita wajib menambahkan anotasi @AndroidEntryPoint*/

@AndroidEntryPoint
//class DetailActivity : AppCompatActivity() {
class DetailActivity : BaseActivity<ActivityDetailBinding, DetailViewModel>(R.layout.activity_detail) {
//    /*Kita deklarasikan detailViewModel terlebih dahulu*/
//    private val detailViewModel: DetailViewModel by viewModels()
//
//    /*Kita deklarasikan juga binding yang akan Kita gunakan*/
//    private val binding: ActivityDetailBinding by lazy {
//        DataBindingUtil.setContentView(this, R.layout.activity_detail)
//    }

    /*deklarasikan variabel idUsername dengan diberi isi secara default value 0*/
    private var idUsername = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /*inisialisasi dulu activitynya*/
        /*wajib karena Kita memakai DataBinding*/
        binding.activity = this
        binding.lifecycleOwner = this
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
                    /*panggil fungsi getUsername dengan param id Username dari viewmodel yang isinya perintah menampilkan data username*/
//                    detailViewModel.getUsername(idUsername).collect { username ->
                    viewModel.getUsername(idUsername).collect { username ->
                        /*untuk menampilkan data username*/
                        binding.users = username
                    }
                }
                launch {
                    /*panggil variabel responseDelete dari ViewModel*/
//                    detailViewModel.responseDelete.collect { success ->
                    viewModel.responseDelete.collect { success ->
                        //jika delete sukses
                        if (success) {
                            /*Ketika berhasil delete datanya akan muncul pesan Berhasil Menghapus Data. */
                            tos(Const.TOAST.DELETE)
//                            Toast.makeText(
//                                this@DetailActivity,
//                                Const.TOAST.DELETE,
//                                Toast.LENGTH_SHORT
//                            ).show()
                            /*Beri finish() agar anda tidak dapat kembali ke aktivitas sebelumnya dengan tombol "kembali".*/
                            finish()
                        }
                    }
                }
            }
        }
    }

    fun updateUsername() {
        /*Ketika button update di klik maka akan berpindah ke halaman EditActivity dengan membawa data id username. */
        openActivity<EditActivity> {
            /*Untuk membawa data ID Username ketika terjadi perpindahan activity*/
            putExtra(Const.ID.ID_USERNAME, idUsername)
        }
//        val editIntent = Intent(this, EditActivity::class.java).apply {
//            /*Untuk membawa data ID Username ketika terjadi perpindahan activity*/
//            putExtra(Const.ID.ID_USERNAME, idUsername)
//        }
//        startActivity(editIntent)
    }

    fun deleteUsername() {
        /*Ketika button delete di klik maka akan terhapus datanya. */
//        detailViewModel.deleteUsername(idUsername)
        viewModel.deleteUsername(idUsername)
    }
}