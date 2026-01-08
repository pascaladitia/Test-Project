package com.pascal.testproject.domain.model

data class UjianRekap(
    val namaUjian: String,
    val namaMatpel: String,
    val tanggal: Long,
    val jumlahPeserta: Int
)

data class SiswaGagal(
    val namaSiswa: String,
    val namaMatpel: String
)
