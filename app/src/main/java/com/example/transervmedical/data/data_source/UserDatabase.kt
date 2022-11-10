package com.example.transervmedical.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.transervmedical.domain.model.User

@Database(
    entities = [User::class],
    version = 1
)
abstract class UserDatabase : RoomDatabase() {

    abstract val userDao: UserDao

}