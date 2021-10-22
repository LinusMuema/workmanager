package com.moose.work.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey
    val number: Int,
    val name: String,
    val image: String
)