package com.example.myapplication

import android.content.Context
import android.content.res.AssetManager
import android.media.MediaPlayer
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.persistence.WordExamples


class WordViewAdapter(
    private val list: Array<WordExamples>
) : RecyclerView.Adapter<CardViewHolder>() {

    var call: callBack? = null

    constructor(list: Array<WordExamples>, cb: callBack) : this(list) {
        call = cb
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        return CardViewHolder(WordView(LayoutInflater.from(parent.context), parent))
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        call?.updateAudio("")
        holder.bind(position, list.size, list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface callBack {
        fun updateAudio(fileName: String)
    }
}

class CardViewHolder internal constructor(private val cardView: WordView) :
    RecyclerView.ViewHolder(cardView.view) {
    internal fun bind(index: Int, total: Int, word: WordExamples) {
        cardView.bind(index, total, word)
    }
}