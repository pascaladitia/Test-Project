package com.pascal.testproject.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.pascal.testproject.data.dao.AkademikDao
import com.pascal.testproject.data.entity.MataPelajaranEntity
import com.pascal.testproject.data.entity.PesertaEntity
import com.pascal.testproject.data.entity.SiswaEntity
import com.pascal.testproject.data.entity.UjianEntity

@Database(
    entities = [
        SiswaEntity::class,
        MataPelajaranEntity::class,
        UjianEntity::class,
        PesertaEntity::class
    ],
    version = 1
)
abstract class AkademikDatabase : RoomDatabase() {
    abstract fun dao(): AkademikDao
}
