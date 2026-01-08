package com.pascal.testproject.domain.usecase

import com.pascal.testproject.data.entity.MataPelajaranEntity
import com.pascal.testproject.data.entity.PesertaEntity
import com.pascal.testproject.data.entity.SiswaEntity
import com.pascal.testproject.data.entity.UjianEntity
import com.pascal.testproject.data.repository.AkademikRepository
import com.pascal.testproject.domain.model.SiswaGagal
import com.pascal.testproject.domain.model.SiswaLulus
import com.pascal.testproject.domain.model.UjianRekap
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Single

@Single
class AkademikUseCaseImpl(
    private val repository: AkademikRepository
) : AkademikUseCase {

    override fun getAllSiswa(): Flow<List<SiswaEntity>> = repository.getAllSiswa()
    override fun getAllMatpel(): Flow<List<MataPelajaranEntity>> = repository.getAllMatpel()
    override fun getAllUjian(): Flow<List<UjianEntity>> = repository.getAllUjian()
    override fun getAllPeserta(): Flow<List<PesertaEntity>> = repository.getAllPeserta()

    override fun getRekapUjian(): Flow<List<UjianRekap>> = repository.getRekapUjian()
    override fun getJumlahLulus(): Flow<Int> = repository.getJumlahLulus()
    override fun getSiswaLulus(): Flow<List<SiswaLulus>> = repository.getSiswaLulus()
    override fun getSiswaGagal(): Flow<List<SiswaGagal>> = repository.getSiswaGagal()

    override fun getUjianByTanggal(
        start: Long,
        end: Long
    ): Flow<List<UjianEntity>> =
        repository.getUjianByTanggal(start, end)

    override fun getRekapUjianByTanggal(
        start: Long,
        end: Long
    ): Flow<List<UjianRekap>> =
        repository.getRekapUjianByTanggal(start, end)

    override suspend fun insertSiswa(siswa: SiswaEntity) =
        repository.insertSiswa(siswa)

    override suspend fun updateSiswa(siswa: SiswaEntity) =
        repository.updateSiswa(siswa)

    override suspend fun deleteSiswa(siswa: SiswaEntity) =
        repository.deleteSiswa(siswa)

    override suspend fun insertMatpel(matpel: MataPelajaranEntity) =
        repository.insertMatpel(matpel)

    override suspend fun updateMatpel(matpel: MataPelajaranEntity) =
        repository.updateMatpel(matpel)

    override suspend fun deleteMatpel(matpel: MataPelajaranEntity) =
        repository.deleteMatpel(matpel)

    override suspend fun insertUjian(ujian: UjianEntity) =
        repository.insertUjian(ujian)

    override suspend fun updateUjian(ujian: UjianEntity) =
        repository.updateUjian(ujian)

    override suspend fun deleteUjian(ujian: UjianEntity) =
        repository.deleteUjian(ujian)

    override suspend fun insertPeserta(peserta: PesertaEntity) =
        repository.insertPeserta(peserta)

    override suspend fun updatePeserta(peserta: PesertaEntity) =
        repository.updatePeserta(peserta)

    override suspend fun deletePeserta(peserta: PesertaEntity) =
        repository.deletePeserta(peserta)
}
