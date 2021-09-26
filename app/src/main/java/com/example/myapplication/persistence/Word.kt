package com.example.myapplication.persistence

import androidx.room.*

//@Entity(tableName = "t_word")
//data class Word(
//    @PrimaryKey val id: Int,
//    @ColumnInfo(name = "name") val word: String?,
//    val part: String?,
//    @ColumnInfo(name = "pos_gram") val posGram: String?,
//    @ColumnInfo(name = "sound_mark") val soundMark: String?,
//    val audio: String?,
//)
//
//@Entity(tableName = "t_sub")
//data class Sub(
//    @PrimaryKey(autoGenerate = true) val id: Int,
//    @ColumnInfo(name = "word_id") val wordId: Int?,
//    val title: String?,
//    val level: Int?,
//    val definition: String?,
//)

@Entity(tableName = "vocabulary")
data class Vocabulary(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "name") val word: String?,
    val part: String?,
    @ColumnInfo(name = "pos_gram") val posGram: String?,
    val audio: String?,
    @ColumnInfo(name = "sound_mark") val soundMark: String?,
    val title: String?,
    val level: Int?,
    val definition: String?,
)

@Entity(tableName = "examples")
data class Example(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "sub_id") val subId: Int?,
    val type: Int?,
    val content: String?,
)

data class WordExamples(
    @Embedded val voc: Vocabulary,
    @Relation(
        parentColumn = "id",
        entityColumn = "sub_id"
    )
    val examples: List<Example>
)

//data class WordSubWithExamples(
//    @Embedded val word: Word,
//    @Relation(
//        entity = Sub::class,
//        parentColumn = "id",
//        entityColumn = "word_id"
//    )
//    val subWithExamples: SubWithExamples?,
//)


