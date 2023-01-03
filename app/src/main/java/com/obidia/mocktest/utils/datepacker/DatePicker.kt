package com.obidia.mocktest.utils.datepacker

import android.app.DatePickerDialog
import android.content.Context
import java.util.*

class DatePicker(context: Context, isSpinnerType: Boolean = false) {
    private lateinit var dialog: DatePickerDialog
    private var callback:Callback? = null

    private val listener = DatePickerDialog.OnDateSetListener { datepicker, i, i2, i3 ->
        callback?.onDateSelected(i, i2, i3)
    }

    init {
        val cal = Calendar.getInstance()

        dialog = DatePickerDialog(
            context, listener,
            cal.get(Calendar.YEAR),
            cal.get(Calendar.MONTH),
            cal.get(Calendar.DAY_OF_MONTH)
        )
    }

    fun showDialog(
        dayOfMonth: Int,
        month: Int,
        year: Int,
        callback: Callback?
    ) {
        this.callback = callback
        dialog.datePicker.init(year, month, dayOfMonth, null)
        dialog.show()
    }

    interface  Callback {
        fun onDateSelected(dayOfMonth: Int, month: Int, year: Int)
    }
}