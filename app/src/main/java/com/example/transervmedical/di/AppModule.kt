package com.example.transervmedical.di

import android.app.Application
import androidx.room.Room
import com.example.transervmedical.data.data_source.UserDatabase
import com.example.transervmedical.data.repository.UserRepositoryImpl
import com.example.transervmedical.domain.repository.UserRepository
import com.example.transervmedical.domain.use_case.AddUser
import com.example.transervmedical.domain.use_case.GetUser
import com.example.transervmedical.domain.use_case.UserUseCases
import com.example.transervmedical.util.Util.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideUserDatabase(app: Application): UserDatabase {
        return Room.databaseBuilder(
            app,
            UserDatabase::class.java,
            DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideUserRepository(database: UserDatabase): UserRepository {
        return UserRepositoryImpl(database.userDao)
    }

    @Provides
    @Singleton
    fun provideUserUseCases(repository: UserRepository): UserUseCases {
        return UserUseCases(
            addUserUseCase = AddUser(repository = repository),
            getUserUseCase = GetUser(repository = repository)
        )
    }
}