package com.hemmersbach.android.service

import android.support.design.widget.TextInputEditText
import android.support.design.widget.TextInputLayout
import android.widget.CheckBox

class CommonService {

    fun clearInputs(inputs: List<TextInputEditText>?) {
        inputs!!.forEach { input ->
            input.text?.clear()
        }
    }

    fun disableErrors(listOfInputLayouts: List<TextInputLayout>) {
        listOfInputLayouts.forEach { input ->
            input.isErrorEnabled = false
        }
    }

    fun disableInputs(inputs: List<CheckBox>) {
        inputs.forEach { input ->
            input.isChecked = false
        }
    }
}
