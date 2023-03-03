package com.jason.data.db

import androidx.room.RoomDatabase

abstract class SportDatabase : RoomDatabase() {

    companion object {
        const val DATABASE_NAME = "SportDataBase"
    }
}