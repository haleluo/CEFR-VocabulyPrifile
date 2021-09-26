package com.example.myapplication


import androidx.lifecycle.ViewModel
import com.example.myapplication.persistence.SubWordDao
import com.example.myapplication.persistence.WordExamples

class WordViewModel(private val dataSource: SubWordDao) : ViewModel() {

    fun subWords(levels: List<Int>): Array<WordExamples> {
        return dataSource.getSubWords(levels)
    }

}
