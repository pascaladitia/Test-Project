package com.pascal.testproject.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "peserta",
    foreignKeys = [
        ForeignKey(entity = SiswaEntity::class, parentColumns = ["nis"], childColumns = ["nis"]),
        ForeignKey(entity = UjianEntity::class, parentColumns = ["idUjian"], childColumns = ["idUjian"])
    ]
)
data class PesertaEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nis: String,
    val idUjian: Int,
    val nilai: Int
)
