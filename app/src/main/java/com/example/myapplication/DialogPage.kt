package com.example.myapplication

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView

class DialogPage(context: Context?) : AlertDialog(context), View.OnClickListener {

    var call: clickCallBack? = null
    var TextView_title: TextView? = null
    var editText: EditText? = null
    var yesButton: Button? = null
    var noButton: Button? = null

    var total: Int = 0
    var current: Int = 0

    var number1: ImageButton? = null
    var number2: ImageButton? = null
    var number3: ImageButton? = null
    var number4: ImageButton? = null
    var number5: ImageButton? = null
    var number6: ImageButton? = null
    var number7: ImageButton? = null
    var number8: ImageButton? = null
    var number9: ImageButton? = null
    var number0: ImageButton? = null
    var numberDel: ImageButton? = null


    constructor(context: Context?, yesCallBack: clickCallBack) : this(context) {
        call = yesCallBack

        editText?.setText("1/12345")

//        TextView_title?.setText(title);
//        val filters = arrayOf<InputFilter>(InputFilter.LengthFilter(10)) // 最大输入长度
//        editText?.filters = filters
//        editText?.isFocusable = false
//        editText?.isFocusableInTouchMode = false
//        editText?.editableText
//        editText?.requestFocus()
//        editText?.keyListener = object : DigitsKeyListener() {
//            override fun getInputType(): Int {
//                return InputType.TYPE_NUMBER_VARIATION_NORMAL
//            }
//
//            override fun getAcceptedChars(): CharArray {
//                return context?.resources!!.toCharArray()
//            }
//        }

    }

    init {
        val inflate = LayoutInflater.from(context).inflate(R.layout.dialog_page, null);
        setView(inflate)
        //设置点击别的区域不关闭页面
        setCancelable(false)

        TextView_title = inflate.findViewById(R.id.text_title)
        editText = inflate.findViewById(R.id.edit_page)
        yesButton = inflate.findViewById(R.id.btn_ok)
        noButton = inflate.findViewById(R.id.btn_cancel)

        number0 = inflate.findViewById(R.id.number_0)
        number1 = inflate.findViewById(R.id.number_1)
        number2 = inflate.findViewById(R.id.number_2)
        number3 = inflate.findViewById(R.id.number_3)
        number4 = inflate.findViewById(R.id.number_4)
        number5 = inflate.findViewById(R.id.number_5)
        number6 = inflate.findViewById(R.id.number_6)
        number7 = inflate.findViewById(R.id.number_7)
        number8 = inflate.findViewById(R.id.number_8)
        number9 = inflate.findViewById(R.id.number_9)
        numberDel = inflate.findViewById(R.id.number_del)

        number0?.setOnClickListener(this)
        number1?.setOnClickListener(this)
        number2?.setOnClickListener(this)
        number3?.setOnClickListener(this)
        number4?.setOnClickListener(this)
        number5?.setOnClickListener(this)
        number6?.setOnClickListener(this)
        number7?.setOnClickListener(this)
        number8?.setOnClickListener(this)
        number9?.setOnClickListener(this)
        numberDel?.setOnClickListener(this)
        yesButton?.setOnClickListener(this)
        noButton?.setOnClickListener { dismiss() }
    }

    fun showDialog(total: Int, current: Int) {
        this.total = total
        this.current = 0
        this.editText?.setText("$current / ${this.total}")
        this.show()
    }


    override fun onClick(p: View) {
        when (p.id) {
            R.id.btn_ok -> call?.yesClick(this)

            R.id.number_0 -> {
                current *= 10
                if (current > total) {
                    current = total
                }
                this.editText?.setText("${current} / ${total}")
            }
            R.id.number_1 -> {
                current *= 10
                current += 1
                if (current > total) {
                    current = total
                }
                this.editText?.setText("${current} / ${total}")
            }
            R.id.number_2 -> {
                current *= 10
                current += 2
                if (current > total) {
                    current = total
                }
                this.editText?.setText("${current} / ${total}")
            }
            R.id.number_3 -> {
                current *= 10
                current += 3
                if (current > total) {
                    current = total
                }
                this.editText?.setText("${current} / ${total}")
            }
            R.id.number_4 -> {
                current *= 10
                current += 4
                if (current > total) {
                    current = total
                }
                this.editText?.setText("${current} / ${total}")
            }
            R.id.number_5 -> {
                current *= 10
                current += 5
                if (current > total) {
                    current = total
                }
                this.editText?.setText("${current} / ${total}")
            }
            R.id.number_6 -> {
                current *= 10
                current += 6
                if (current > total) {
                    current = total
                }
                this.editText?.setText("${current} / ${total}")
            }
            R.id.number_7 -> {
                current *= 10
                current += 7
                if (current > total) {
                    current = total
                }
                this.editText?.setText("${current} / ${total}")
            }
            R.id.number_8 -> {
                current *= 10
                current += 8
                if (current > total) {
                    current = total
                }
                this.editText?.setText("${current} / ${total}")
            }
            R.id.number_9 -> {
                current *= 10
                current += 9
                if (current > total) {
                    current = total
                }
                this.editText?.setText("${current} / ${total}")
            }
            R.id.number_del -> {
                current /= 10
                if (current > total) {
                    current = total
                }
                this.editText?.setText("${current} / ${total}")
            }

        }

    }

    interface clickCallBack {
        fun yesClick(dialog: DialogPage)
    }

}