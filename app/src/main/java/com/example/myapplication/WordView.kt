package com.example.myapplication

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import com.example.myapplication.persistence.WordExamples


/** Inflates and populates a [View] representing a [Word]  */
class WordView(layoutInflater: LayoutInflater, container: ViewGroup?) {
    //
    val view: View = layoutInflater.inflate(R.layout.item_word_layout, container, false)

    //
    private val textWord: TextView
    private val textPart: TextView
    private val textSoundMark: TextView
    private val textTitle: TextView
    private val textLevel: TextView
    private val textDefinition: TextView
    private val textDictionaryTag: TextView
    private val textDictionary: TextView
    private val textLearnerTag: TextView
    private val textLearner: TextView
    private val textPage: TextView

    init {
        textWord = view.findViewById(R.id.text_word)
        textPart = view.findViewById(R.id.text_part)
        textSoundMark = view.findViewById(R.id.text_soundmark)
        textTitle = view.findViewById(R.id.text_title)
        textLevel = view.findViewById(R.id.text_level)
        textDefinition = view.findViewById(R.id.text_definition)
        textDictionaryTag = view.findViewById(R.id.text_dictionary_tag)
        textDictionary = view.findViewById(R.id.text_dictionary)
        textLearnerTag = view.findViewById(R.id.text_learner_tag)
        textLearner = view.findViewById(R.id.text_learner)
        textPage = view.findViewById(R.id.text_page)
    }

    /**
     * Updates the view to represent the passed in card
     */
    @SuppressLint("SetTextI18n")
    fun bind(index: Int, total: Int, word: WordExamples) {

        var idx = index + 1
        textPage.text = "$idx / $total"

        word.voc.let {
            textWord.text = it.word

            if (it.posGram.isNullOrEmpty()) {
                textPart.text = it.part
            } else {
                textPart.text = it.part + " [${it.posGram}]"
            }

            textSoundMark.text = it.soundMark
            textTitle.text = it.title
            textLevel.text = LEVEL_MAP[it.level]
            it.level?.let { getDrawableRes(it)?.let { textLevel.setBackgroundResource(it) } }
            textDefinition.text = "\u3000\u3000" + it.definition
        }

        word.examples.let {
            val dExamples = it.filter { it.type == 1 }
            if (dExamples.isEmpty()) {
                textDictionaryTag.visibility = View.GONE
                textDictionary.visibility = View.GONE
            } else {
                textDictionaryTag.visibility = View.VISIBLE
                textDictionary.visibility = View.VISIBLE

                var examples = ""
                for ((i, example) in dExamples.withIndex()) {
                    if (i != dExamples.size - 1) {
                        examples += "• " + example.content + "\n"
                    } else {
                        examples += "• " + example.content
                    }
                }
                textDictionary.text = examples
            }

            val lExamples = it.filter { it.type == 2 }
            if (lExamples.isEmpty()) {
                textLearnerTag.visibility = View.GONE
                textLearner.visibility = View.GONE
            } else {
                textLearnerTag.visibility = View.VISIBLE
                textLearner.visibility = View.VISIBLE

                var examples = ""
                for ((i, example) in lExamples.withIndex()) {
                    if (i != lExamples.size - 1) {
                        examples += "• " + example.content + "\n"
                    } else {
                        examples += "• " + example.content
                    }
                }
                textLearner.text = examples
            }
        }


    }

    @DrawableRes
    private fun getDrawableRes(level: Int): Int? {
        return LEVEL_BACKGROUND_MAP[level]
    }

    companion object {
        private val LEVEL_MAP = mapOf(
            11 to "A1",
            12 to "A2",
            21 to "B1",
            22 to "B2",
            31 to "C1",
            32 to "C2",
        )
        private val LEVEL_BACKGROUND_MAP = mapOf(
            11 to R.drawable.shape_level_a1,
            12 to R.drawable.shape_level_a2,
            21 to R.drawable.shape_level_b1,
            22 to R.drawable.shape_level_b2,
            31 to R.drawable.shape_level_c1,
            32 to R.drawable.shape_level_c2,
        )
    }
}

