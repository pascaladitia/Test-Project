package com.pascal.testproject.di

import androidx.room.Room
import com.pascal.testproject.data.database.AkademikDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {

    single {
        Room.databaseBuilder(
            androidContext(),
            AkademikDatabase::class.java,
            "akademik_db"
        ).build()
    }

    single {
        get<AkademikDatabase>().dao()
    }
}
