package com.pascal.testproject.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.pascal.testproject.data.dao.AkademikDao
import com.pascal.testproject.data.entity.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import android.database.sqlite.SQLiteConstraintException
import com.pascal.testproject.model.UjianRekap
import kotlinx.coroutines.flow.MutableStateFlow

class AkademikViewModel(
    application: Application,
    private val dao: AkademikDao
) : AndroidViewModel(application) {

    private val context = application.applicationContext

    val siswa = dao.getAllSiswa()
    val matpel = dao.getAllMatpel()
    val ujian = dao.getAllUjian()
    val peserta = dao.getAllPeserta()

    val rekap = dao.getRekapUjian()
    val jumlahLulus = dao.getJumlahLulus()
    val gagal = dao.getSiswaGagal()

    val rekapByTanggal = MutableStateFlow<List<UjianRekap>>(emptyList())

    // ===== TAMBAH =====
    fun tambahSiswa(nis: String, nama: String, alamat: String) =
        viewModelScope.launch {
            try {
                dao.insertSiswa(SiswaEntity(nis, nama, alamat))
            } catch (e: Exception) {
                toast("Gagal menambah siswa")
            }
        }

    fun tambahMatpel(nama: String) =
        viewModelScope.launch {
            try {
                dao.insertMatpel(MataPelajaranEntity(namaMatpel = nama))
            } catch (e: Exception) {
                toast("Gagal menambah mata pelajaran")
            }
        }

    fun tambahUjian(nama: String, idMatpel: Int, tanggal: Long) =
        viewModelScope.launch {
            try {
                dao.insertUjian(
                    UjianEntity(
                        namaUjian = nama,
                        idMatpel = idMatpel,
                        tanggal = tanggal
                    )
                )
            } catch (e: SQLiteConstraintException) {
                toast("Mata pelajaran tidak ditemukan")
            }
        }

    fun tambahPeserta(nis: String, idUjian: Int, nilai: Int) =
        viewModelScope.launch {
            try {
                dao.insertPeserta(
                    PesertaEntity(
                        nis = nis,
                        idUjian = idUjian,
                        nilai = nilai
                    )
                )
            } catch (e: SQLiteConstraintException) {
                toast("NIS atau ID Ujian tidak valid")
            }
        }

    // ===== UPDATE =====
    fun updateSiswa(siswa: SiswaEntity) =
        viewModelScope.launch {
            try {
                dao.updateSiswa(siswa)
            } catch (e: Exception) {
                toast("Gagal update siswa")
            }
        }

    fun updateMatpel(matpel: MataPelajaranEntity) =
        viewModelScope.launch {
            try {
                dao.updateMatpel(matpel)
            } catch (e: Exception) {
                toast("Gagal update matpel")
            }
        }

    fun updateUjian(ujian: UjianEntity) =
        viewModelScope.launch {
            try {
                dao.updateUjian(ujian)
            } catch (e: Exception) {
                toast("Gagal update ujian")
            }
        }

    fun updatePeserta(peserta: PesertaEntity) =
        viewModelScope.launch {
            try {
                dao.updatePeserta(peserta)
            } catch (e: Exception) {
                toast("Gagal update peserta")
            }
        }

    // ===== DELETE =====
    fun deleteSiswa(siswa: SiswaEntity) =
        viewModelScope.launch {
            try {
                dao.deleteSiswa(siswa)
            } catch (e: Exception) {
                toast("Gagal hapus siswa")
            }
        }

    fun deleteMatpel(matpel: MataPelajaranEntity) =
        viewModelScope.launch {
            try {
                dao.deleteMatpel(matpel)
            } catch (e: Exception) {
                toast("Gagal hapus matpel")
            }
        }

    fun deleteUjian(ujian: UjianEntity) =
        viewModelScope.launch {
            try {
                dao.deleteUjian(ujian)
            } catch (e: Exception) {
                toast("Gagal hapus ujian")
            }
        }

    fun deletePeserta(peserta: PesertaEntity) =
        viewModelScope.launch {
            try {
                dao.deletePeserta(peserta)
            } catch (e: Exception) {
                toast("Gagal hapus peserta")
            }
        }

    fun ujianByTanggal(start: Long, end: Long): Flow<List<UjianEntity>> =
        dao.getUjianByTanggal(start, end)

    fun loadRekapByTanggal(start: Long, end: Long) {
        viewModelScope.launch {
            dao.getRekapUjianByTanggal(start, end).collect {
                rekapByTanggal.value = it
            }
        }
    }

    private fun toast(msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }
}