package com.pascal.testproject.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "ujian",
    foreignKeys = [
        ForeignKey(
            entity = MataPelajaranEntity::class,
            parentColumns = ["idMatpel"],
            childColumns = ["idMatpel"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class UjianEntity(
    @PrimaryKey(autoGenerate = true)
    val idUjian: Int = 0,
    val namaUjian: String,
    val idMatpel: Int,
    val tanggal: Long
)
