package com.pascal.testproject.viewmodel

import android.content.Context
import android.database.sqlite.SQLiteConstraintException
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pascal.testproject.data.entity.MataPelajaranEntity
import com.pascal.testproject.data.entity.PesertaEntity
import com.pascal.testproject.data.entity.SiswaEntity
import com.pascal.testproject.data.entity.UjianEntity
import com.pascal.testproject.domain.model.UjianRekap
import com.pascal.testproject.domain.usecase.AkademikUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class AkademikViewModel(
    private val context: Context,
    private val useCase: AkademikUseCase
) : ViewModel() {

    val siswa = useCase.getAllSiswa()
    val matpel = useCase.getAllMatpel()
    val ujian = useCase.getAllUjian()
    val peserta = useCase.getAllPeserta()

    val rekap = useCase.getRekapUjian()
    val jumlahLulus = useCase.getJumlahLulus()
    val lulus = useCase.getSiswaLulus()
    val gagal = useCase.getSiswaGagal()

    val rekapByTanggal = MutableStateFlow<List<UjianRekap>>(emptyList())

    fun tambahSiswa(nis: String, nama: String, alamat: String) =
        viewModelScope.launch {
            try {
                useCase.insertSiswa(SiswaEntity(nis, nama, alamat))
            } catch (e: Exception) {
                toast("Gagal menambah siswa")
            }
        }

    fun tambahMatpel(nama: String) =
        viewModelScope.launch {
            try {
                useCase.insertMatpel(MataPelajaranEntity(namaMatpel = nama))
            } catch (e: Exception) {
                toast("Gagal menambah mata pelajaran")
            }
        }

    fun tambahUjian(nama: String, idMatpel: Int, tanggal: Long) =
        viewModelScope.launch {
            try {
                useCase.insertUjian(
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
                useCase.insertPeserta(
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

    fun updateSiswa(siswa: SiswaEntity) =
        viewModelScope.launch {
            try {
                useCase.updateSiswa(siswa)
            } catch (e: Exception) {
                toast("Gagal update siswa")
            }
        }

    fun updateMatpel(matpel: MataPelajaranEntity) =
        viewModelScope.launch {
            try {
                useCase.updateMatpel(matpel)
            } catch (e: Exception) {
                toast("Gagal update matpel")
            }
        }

    fun updateUjian(ujian: UjianEntity) =
        viewModelScope.launch {
            try {
                useCase.updateUjian(ujian)
            } catch (e: Exception) {
                toast("Gagal update ujian")
            }
        }

    fun updatePeserta(peserta: PesertaEntity) =
        viewModelScope.launch {
            try {
                useCase.updatePeserta(peserta)
            } catch (e: Exception) {
                toast("Gagal update peserta")
            }
        }

    fun deleteSiswa(siswa: SiswaEntity) =
        viewModelScope.launch {
            try {
                useCase.deleteSiswa(siswa)
            } catch (e: Exception) {
                toast("Gagal hapus siswa")
            }
        }

    fun deleteMatpel(matpel: MataPelajaranEntity) =
        viewModelScope.launch {
            try {
                useCase.deleteMatpel(matpel)
            } catch (e: Exception) {
                toast("Gagal hapus matpel")
            }
        }

    fun deleteUjian(ujian: UjianEntity) =
        viewModelScope.launch {
            try {
                useCase.deleteUjian(ujian)
            } catch (e: Exception) {
                toast("Gagal hapus ujian")
            }
        }

    fun deletePeserta(peserta: PesertaEntity) =
        viewModelScope.launch {
            try {
                useCase.deletePeserta(peserta)
            } catch (e: Exception) {
                toast("Gagal hapus peserta")
            }
        }

    fun ujianByTanggal(start: Long, end: Long): Flow<List<UjianEntity>> =
        useCase.getUjianByTanggal(start, end)

    fun loadRekapByTanggal(start: Long, end: Long) {
        viewModelScope.launch {
            useCase.getRekapUjianByTanggal(start, end).collect {
                rekapByTanggal.value = it
            }
        }
    }

    private fun toast(msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }
}