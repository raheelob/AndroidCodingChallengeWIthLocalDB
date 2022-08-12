package com.example.maydcodingchallenge.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ShortLinkEntity::class], version = 1, exportSchema = false)
abstract class ShortLinkDatabase : RoomDatabase(){
    abstract fun ShortLinkDao(): ShortLinkDao

    companion object{
        @Volatile
        private var INSTANCE : ShortLinkDatabase? = null
        private const val DB_NAME = "shorten_database.db"
        fun getInstance(context: Context): ShortLinkDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ShortLinkDatabase::class.java,
                    DB_NAME
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}