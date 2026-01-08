package com.pascal.testproject.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.pascal.testproject.data.entity.MataPelajaranEntity
import com.pascal.testproject.data.entity.PesertaEntity
import com.pascal.testproject.data.entity.SiswaEntity
import com.pascal.testproject.data.entity.UjianEntity
import com.pascal.testproject.domain.model.SiswaGagal
import com.pascal.testproject.domain.model.UjianRekap
import kotlinx.coroutines.flow.Flow

@Dao
interface AkademikDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSiswa(siswa: SiswaEntity)

    @Update
    suspend fun updateSiswa(siswa: SiswaEntity)

    @Delete
    suspend fun deleteSiswa(siswa: SiswaEntity)

    @Query("SELECT * FROM siswa")
    fun getAllSiswa(): Flow<List<SiswaEntity>>

    @Insert
    suspend fun insertMatpel(matpel: MataPelajaranEntity)

    @Update
    suspend fun updateMatpel(matpel: MataPelajaranEntity)

    @Delete
    suspend fun deleteMatpel(matpel: MataPelajaranEntity)

    @Query("SELECT * FROM mata_pelajaran")
    fun getAllMatpel(): Flow<List<MataPelajaranEntity>>

    @Insert
    suspend fun insertUjian(ujian: UjianEntity)

    @Update
    suspend fun updateUjian(ujian: UjianEntity)

    @Delete
    suspend fun deleteUjian(ujian: UjianEntity)

    @Query("SELECT * FROM ujian ORDER BY tanggal")
    fun getAllUjian(): Flow<List<UjianEntity>>

    @Query("""
        SELECT * FROM ujian
        WHERE tanggal BETWEEN :start AND :end
    """)
    fun getUjianByTanggal(start: Long, end: Long): Flow<List<UjianEntity>>

    @Insert
    suspend fun insertPeserta(peserta: PesertaEntity)

    @Update
    suspend fun updatePeserta(peserta: PesertaEntity)

    @Delete
    suspend fun deletePeserta(peserta: PesertaEntity)

    @Query("SELECT * FROM peserta")
    fun getAllPeserta(): Flow<List<PesertaEntity>>

    @Query("""
        SELECT ujian.namaUjian,
               mata_pelajaran.namaMatpel,
               ujian.tanggal,
               COUNT(peserta.id) AS jumlahPeserta
        FROM ujian
        JOIN mata_pelajaran ON ujian.idMatpel = mata_pelajaran.idMatpel
        LEFT JOIN peserta ON ujian.idUjian = peserta.idUjian
        GROUP BY ujian.idUjian
    """)
    fun getRekapUjian(): Flow<List<UjianRekap>>

    @Query("""
        SELECT COUNT(DISTINCT nis)
        FROM peserta
        WHERE nilai >= 75
    """)
    fun getJumlahLulus(): Flow<Int>

    @Query("""
        SELECT siswa.nama AS namaSiswa,
               mata_pelajaran.namaMatpel
        FROM peserta
        JOIN siswa ON peserta.nis = siswa.nis
        JOIN ujian ON peserta.idUjian = ujian.idUjian
        JOIN mata_pelajaran ON ujian.idMatpel = mata_pelajaran.idMatpel
        WHERE peserta.nilai < 75
    """)
    fun getSiswaGagal(): Flow<List<SiswaGagal>>

    @Query("""
    SELECT ujian.namaUjian,
           mata_pelajaran.namaMatpel,
           ujian.tanggal,
           COUNT(peserta.id) AS jumlahPeserta
    FROM ujian
    JOIN mata_pelajaran ON ujian.idMatpel = mata_pelajaran.idMatpel
    LEFT JOIN peserta ON ujian.idUjian = peserta.idUjian
    WHERE ujian.tanggal BETWEEN :start AND :end
    GROUP BY ujian.idUjian
""")
    fun getRekapUjianByTanggal(start: Long, end: Long): Flow<List<UjianRekap>>
}
