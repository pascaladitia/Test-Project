package com.pascal.testproject.di

import androidx.room.Room
import com.pascal.testproject.data.database.AppDatabase
import com.pascal.testproject.data.repository.AkademikRepository
import com.pascal.testproject.data.repository.AkademikRepositoryImpl
import com.pascal.testproject.domain.usecase.AkademikUseCase
import com.pascal.testproject.domain.usecase.AkademikUseCaseImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val appModule = module {

    single {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            "akademik_db"
        ).build()
    }

    single {
        get<AppDatabase>().dao()
    }

    single<AkademikRepository> {
        AkademikRepositoryImpl(get())
    }

    single<AkademikUseCase> {
        AkademikUseCaseImpl(get())
    }
}
