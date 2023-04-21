package com.example.ui

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import androidx.appcompat.app.AppCompatViewInflater
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat

class CustomViewInflater : AppCompatViewInflater() {

    override fun createView(context: Context?, name: String?, attrs: AttributeSet?): View? {
        return super.createView(context, name, attrs)
    }

    override fun createTextView(context: Context?, attrs: AttributeSet?): AppCompatTextView {
        return CustomTextView(context!!, attrs)
    }
}

class CustomTextView : AppCompatTextView {

    init {
        setTextSize(TypedValue.COMPLEX_UNIT_DIP, 28f)
        setTextColor(ContextCompat.getColor(this.context, R.color.purple_700))
    }

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)
}