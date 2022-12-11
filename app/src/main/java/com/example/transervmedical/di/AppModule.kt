package com.example.transervmedical.di

import android.app.Application
import androidx.room.Room
import com.example.transervmedical.data.data_source.CalendarDatabase
import com.example.transervmedical.data.data_source.UserDatabase
import com.example.transervmedical.data.repository.CalendarRepositoryImpl
import com.example.transervmedical.data.repository.UserRepositoryImpl
import com.example.transervmedical.domain.repository.CalendarRepository
import com.example.transervmedical.domain.repository.UserRepository
import com.example.transervmedical.domain.use_case.calendar.AddCalendarEvent
import com.example.transervmedical.domain.use_case.calendar.CalendarUseCases
import com.example.transervmedical.domain.use_case.calendar.GetAllCalendarEvents
import com.example.transervmedical.domain.use_case.form.*
import com.example.transervmedical.domain.use_case.form.register.*
import com.example.transervmedical.domain.use_case.form.validation.ValidateEmail
import com.example.transervmedical.domain.use_case.form.validation.ValidatePassword
import com.example.transervmedical.domain.use_case.form.validation.ValidatePhoneNumber
import com.example.transervmedical.domain.use_case.form.validation.ValidateRepeatedPassword
import com.example.transervmedical.util.Util.CALENDAR_DATABASE
import com.example.transervmedical.util.Util.FIREBASE_DATABASE_URL
import com.example.transervmedical.util.Util.USER_DATABASE
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.util.*
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
            USER_DATABASE
        ).build()
    }

    @Provides
    @Singleton
    fun provideCalendarDatabase(app: Application): CalendarDatabase {
        return Room.databaseBuilder(
            app,
            CalendarDatabase::class.java,
            CALENDAR_DATABASE
        ).build()
    }

    @Provides
    @Singleton
    fun provideUserRepository(database: UserDatabase): UserRepository {
        return UserRepositoryImpl(database.userDao)
    }

    @Provides
    @Singleton
    fun provideCalendarRepository(database: CalendarDatabase): CalendarRepository {
        return CalendarRepositoryImpl(database.calendarDao)
    }

/* provideUserUseCases
    @Provides
    @Singleton
    fun provideUserUseCases(repository: UserRepository): UserUseCases {
        return UserUseCases(
            addUserUseCase = AddUser(repository = repository),
            getUserUseCase = GetUser(repository = repository)
        )
    }
 */

    @Provides
    @Singleton
    fun provideRegistrationUseCases(): RegistrationUseCases {
        return RegistrationUseCases(
            validateEmail = ValidateEmail(),
            validatePhoneNumber = ValidatePhoneNumber(),
            validatePassword = ValidatePassword(),
            validateRepeatedPassword = ValidateRepeatedPassword()
        )
    }

    @Provides
    @Singleton
    fun provideCalendarUseCases(calendarRepository: CalendarRepository): CalendarUseCases {
        return CalendarUseCases(
            addCalendarEvent = AddCalendarEvent(calendarRepository),
            getAllCalendarEvents = GetAllCalendarEvents(calendarRepository)
        )
    }

    @Provides
    @Singleton
    fun provideFirebase(): FirebaseAuth {
        return Firebase.auth
    }

    @Provides
    @Singleton
    fun provideRealtimeDatabase(): FirebaseDatabase {
        return Firebase.database(FIREBASE_DATABASE_URL)
    }

}