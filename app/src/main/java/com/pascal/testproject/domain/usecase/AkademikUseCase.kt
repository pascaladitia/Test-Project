package com.pascal.testproject.domain.usecase

import com.pascal.testproject.data.entity.MataPelajaranEntity
import com.pascal.testproject.data.entity.PesertaEntity
import com.pascal.testproject.data.entity.SiswaEntity
import com.pascal.testproject.data.entity.UjianEntity
import com.pascal.testproject.domain.model.SiswaGagal
import com.pascal.testproject.domain.model.SiswaLulus
import com.pascal.testproject.domain.model.UjianRekap
import kotlinx.coroutines.flow.Flow

interface AkademikUseCase {

    fun getAllSiswa(): Flow<List<SiswaEntity>>
    fun getAllMatpel(): Flow<List<MataPelajaranEntity>>
    fun getAllUjian(): Flow<List<UjianEntity>>
    fun getAllPeserta(): Flow<List<PesertaEntity>>

    fun getRekapUjian(): Flow<List<UjianRekap>>
    fun getJumlahLulus(): Flow<Int>
    fun getSiswaLulus(): Flow<List<SiswaLulus>>
    fun getSiswaGagal(): Flow<List<SiswaGagal>>

    fun getUjianByTanggal(start: Long, end: Long): Flow<List<UjianEntity>>
    fun getRekapUjianByTanggal(start: Long, end: Long): Flow<List<UjianRekap>>

    suspend fun insertSiswa(siswa: SiswaEntity)
    suspend fun updateSiswa(siswa: SiswaEntity)
    suspend fun deleteSiswa(siswa: SiswaEntity)

    suspend fun insertMatpel(matpel: MataPelajaranEntity)
    suspend fun updateMatpel(matpel: MataPelajaranEntity)
    suspend fun deleteMatpel(matpel: MataPelajaranEntity)

    suspend fun insertUjian(ujian: UjianEntity)
    suspend fun updateUjian(ujian: UjianEntity)
    suspend fun deleteUjian(ujian: UjianEntity)

    suspend fun insertPeserta(peserta: PesertaEntity)
    suspend fun updatePeserta(peserta: PesertaEntity)
    suspend fun deletePeserta(peserta: PesertaEntity)
}
