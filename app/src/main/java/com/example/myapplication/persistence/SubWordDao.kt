package com.example.myapplication.persistence

import androidx.room.*

@Dao
interface SubWordDao {

    @Transaction
    @Query("SELECT * FROM vocabulary WHERE level in (:levels) order by id")
    fun getSubWords(levels: List<Int>): Array<WordExamples>

    @Transaction
    @Query("SELECT * FROM vocabulary WHERE name like :str order by id")
    fun querySubWords(str: String): Array<WordExamples>

}