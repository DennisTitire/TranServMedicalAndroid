package com.example.transervmedical.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val email: String,
    val phoneNumber: String,
    val password: String,
    val repeatedPassword: String,
)