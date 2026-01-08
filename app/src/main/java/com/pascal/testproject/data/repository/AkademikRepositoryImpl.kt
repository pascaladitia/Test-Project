package com.pascal.testproject.data.repository

import com.pascal.testproject.data.dao.AkademikDao
import com.pascal.testproject.data.entity.*
import com.pascal.testproject.domain.model.SiswaGagal
import com.pascal.testproject.domain.model.SiswaLulus
import com.pascal.testproject.domain.model.UjianRekap
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Single

@Single
class AkademikRepositoryImpl(
    private val dao: AkademikDao
) : AkademikRepository {

    override fun getAllSiswa(): Flow<List<SiswaEntity>> = dao.getAllSiswa()
    override fun getAllMatpel(): Flow<List<MataPelajaranEntity>> = dao.getAllMatpel()
    override fun getAllUjian(): Flow<List<UjianEntity>> = dao.getAllUjian()
    override fun getAllPeserta(): Flow<List<PesertaEntity>> = dao.getAllPeserta()

    override fun getRekapUjian(): Flow<List<UjianRekap>> = dao.getRekapUjian()
    override fun getJumlahLulus(): Flow<Int> = dao.getJumlahLulus()
    override fun getSiswaLulus(): Flow<List<SiswaLulus>> = dao.getSiswaLulus()
    override fun getSiswaGagal(): Flow<List<SiswaGagal>> = dao.getSiswaGagal()

    override fun getUjianByTanggal(start: Long, end: Long): Flow<List<UjianEntity>> =
        dao.getUjianByTanggal(start, end)

    override fun getRekapUjianByTanggal(
        start: Long,
        end: Long
    ): Flow<List<UjianRekap>> =
        dao.getRekapUjianByTanggal(start, end)

    override suspend fun insertSiswa(siswa: SiswaEntity) = dao.insertSiswa(siswa)
    override suspend fun updateSiswa(siswa: SiswaEntity) = dao.updateSiswa(siswa)
    override suspend fun deleteSiswa(siswa: SiswaEntity) = dao.deleteSiswa(siswa)

    override suspend fun insertMatpel(matpel: MataPelajaranEntity) = dao.insertMatpel(matpel)
    override suspend fun updateMatpel(matpel: MataPelajaranEntity) = dao.updateMatpel(matpel)
    override suspend fun deleteMatpel(matpel: MataPelajaranEntity) = dao.deleteMatpel(matpel)

    override suspend fun insertUjian(ujian: UjianEntity) = dao.insertUjian(ujian)
    override suspend fun updateUjian(ujian: UjianEntity) = dao.updateUjian(ujian)
    override suspend fun deleteUjian(ujian: UjianEntity) = dao.deleteUjian(ujian)

    override suspend fun insertPeserta(peserta: PesertaEntity) = dao.insertPeserta(peserta)
    override suspend fun updatePeserta(peserta: PesertaEntity) = dao.updatePeserta(peserta)
    override suspend fun deletePeserta(peserta: PesertaEntity) = dao.deletePeserta(peserta)
}
