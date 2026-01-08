package com.pascal.testproject.data.database

import android.content.Context
import androidx.room.Room

object DatabaseProvider {

    private var INSTANCE: AkademikDatabase? = null

    fun getDatabase(context: Context): AkademikDatabase {
        return INSTANCE ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                AkademikDatabase::class.java,
                "akademik_db"
            ).build()
            INSTANCE = instance
            instance
        }
    }
}
