package com.example.myapplication.persistence

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context

/**
 * The Room database that contains the Word table
 */
@Database(entities = [Vocabulary::class, Example::class], version = 1)
abstract class WordsDatabase : RoomDatabase() {

    abstract fun wordDao(): SubWordDao

    companion object {

        @Volatile
        private var INSTANCE: WordsDatabase? = null

        fun getInstance(context: Context): WordsDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                WordsDatabase::class.java, "vocabulary.db"
            ).allowMainThreadQueries().build()
    }
}
