package com.example.myapplication

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView

class DialogSearch(context: Context?) : AlertDialog(context), View.OnClickListener {

    var call: clickCallBack? = null
    var editText: EditText? = null
    var btnSearch: TextView? = null


    constructor(context: Context?, yesCallBack: clickCallBack) : this(context) {
        call = yesCallBack
    }

    init {
        val inflate = LayoutInflater.from(context).inflate(R.layout.dialog_search, null);
        setView(inflate)
        //设置点击别的区域不关闭页面
//        setCancelable(false)

        editText = inflate.findViewById(R.id.edit_page)
        btnSearch = inflate.findViewById(R.id.btn_search)
        btnSearch?.setOnClickListener(this)
    }


    override fun onClick(p: View) {
        call?.yesClick(this)
    }

    interface clickCallBack {
        fun yesClick(dialog: DialogSearch)
    }

}